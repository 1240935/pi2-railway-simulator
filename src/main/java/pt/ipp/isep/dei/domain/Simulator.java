package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.Repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the simulator that manages the monthly simulation of routes, revenues, costs,
 * and events related to trains, stations, and scenarios.
 */
public class Simulator implements Serializable {

    private List<Route> routes;
    private Player player;
    private Scenario scenario;
    private int currentMonth;
    private List<String> alerts;
    private String lastMonthSummary;

    private double yearlyRevenue;
    private double yearlyTrackMaintenance;
    private double yearlyTrainMaintenance;
    private double yearlyFuelCost;
    private java.util.Map<Locomotive, Double> locomotiveDistanceMap = new java.util.HashMap<>();
    private List<LocomotiveModel> alertedModels = new ArrayList<>();

    public Simulator(List<Route> routes, Player player, Scenario scenario) {
        this.routes = routes;
        this.player = player;
        this.scenario = scenario;
        this.currentMonth = 1;
        this.alerts = new ArrayList<>();
        resetYearlyFinancials();
    }

    /**
     * Executes the simulation of one month, updating departures, arrivals, cargo,
     * maintenance, revenues, and alert messages.
     */
    public void simulateMonth() {
        System.out.println("=== Month " + currentMonth + " ===");
        if (currentMonth % 12 == 1) resetYearlyFinancials();

        simulateDepartures();

        for (Route route : routes) {
            simulateArrivals(route);
        }

        checkNewLocomotivesAvailable();
        generateCargoes();

        for (Route route : routes) {
            Locomotive loco = route.getLocomotive();
            if (loco != null) simulateRoute(route, loco);
        }

        if (currentMonth % 12 == 0) {
            updateDemand();
            System.out.println(generateFinancialReport());
        }

        lastMonthSummary = generateMonthlySummary();
        alerts.clear();
        currentMonth++;
    }

    /** Resets the yearly financial values. */
    private void resetYearlyFinancials() {
        yearlyRevenue = 0;
        yearlyTrackMaintenance = 0;
        yearlyTrainMaintenance = 0;
        yearlyFuelCost = 0;
        locomotiveDistanceMap.clear();
    }

    /** Simulates the estimated time between route points based on speed and cargo. */
    private void simulateDepartures() {
        for (Route route : routes) {
            Locomotive loco = route.getLocomotive();
            if (loco == null) continue;

            int maxSpeed = loco.getLocomotiveModel().getTopSpeed();
            int numCarriages = 0;
            for (PointOfRoute por : route.getPointsOfRoute()) {
                numCarriages += por.getCargoList().size();
            }
            double speedPenalty = Math.min(0.05 * numCarriages, 0.30);

            for (int i = 0; i < route.getPointsOfRoute().size() - 1; i++) {
                PointOfRoute departure = route.getPointsOfRoute().get(i);
                PointOfRoute arrival = route.getPointsOfRoute().get(i + 1);
                Railway railway = Railway.getRailwayBetween(departure.getStation(), arrival.getStation());

                double adjustedSpeed = railway.isDoubleTrack() ? maxSpeed : 0.8 * maxSpeed;
                adjustedSpeed *= (1 - speedPenalty);

                double scaledDistance = railway.getDistance() * scenario.getMap().getScale();
                double timeHours = scaledDistance / adjustedSpeed;

                departure.setEstimatedTimeToNextArrival(timeHours);
            }
        }
    }

    /** Simulates the delivery of cargo and profit based on deliveries made. */
    private void simulateArrivals(Route route) {
        List<PointOfRoute> points = route.getPointsOfRoute();
        int totalDelivered = 0;
        StringBuilder deliveryReport = new StringBuilder();

        double baseRevenue = 100.0;
        double priceMultiplier = 1.0;
        double yearlyRevenue = 0.0;

        for (int i = 1; i < points.size(); i++) {
            PointOfRoute point = points.get(i);
            Station station = point.getStation();

            List<Cargo> carriedCargo = point.getCargoList();
            List<Cargo> demands = station.getDemands();
            int deliveredHere = 0;

            List<Cargo> carriedCargoCopy = new ArrayList<>(carriedCargo);

            for (Cargo carried : carriedCargoCopy) {
                for (Cargo demand : demands) {
                    if (carried.getType() == demand.getType()) {
                        int deliverAmount = Math.min(carried.getQuantity(), demand.getQuantity());
                        if (deliverAmount > 0) {
                            carried.setQuantity(carried.getQuantity() - deliverAmount);
                            demand.setQuantity(demand.getQuantity() - deliverAmount);
                            totalDelivered += deliverAmount;
                            deliveredHere += deliverAmount;

                            deliveryReport.append(station.getName())
                                    .append(" received ")
                                    .append(deliverAmount)
                                    .append(" of ")
                                    .append(carried.getType().name())
                                    .append("\n");
                        }
                    }
                }
                if (carried.getQuantity() <= 0) {
                    carriedCargo.remove(carried);
                }
            }

            double bonus = station.getTotalRevenueBonus();
            priceMultiplier += bonus;
            yearlyRevenue += baseRevenue * priceMultiplier;

            if (deliveredHere > 0) {
                System.out.println(station.getName() + " delivered " + deliveredHere + " units.");
            }

            carriedCargo.clear();
            loadCargoFromStation(point);
        }

        if (totalDelivered > 0) {
            int reward = totalDelivered * 10;
            player.addBudget(reward);
            System.out.println(deliveryReport);
            System.out.println("Rewarded " + reward + "€ for delivering " + totalDelivered + " units.");
        } else {
            System.out.println("No cargo matched station demands. No budget rewarded.");
        }
    }

    /** Checks if there are new locomotive models available for the current year. */
    private void checkNewLocomotivesAvailable() {
        int currentYear = scenario.getYear() + (currentMonth / 12);
        List<LocomotiveModel> models = Repositories.getInstance().getLocomotiveModelRepository().getLocomotiveModels();

        for (LocomotiveModel model : models) {
            if (model.getStartYear() == currentYear && !alertedModels.contains(model)) {
                String alertMessage = "New locomotive available: " + model.name();
                alerts.add(alertMessage);
                alertedModels.add(model);
                System.out.println(alertMessage);
            }
        }
    }

    /** Generates new cargo units according to the month and industry modifiers. */
    private void generateCargoes() {
        int industryChanger = scenario.getIndustryChanger();
        int baseInterval = 2;

        for (Route route : routes) {
            for (PointOfRoute point : route.getPointsOfRoute()) {
                Station station = point.getStation();
                for (Cargo suppliedCargo : station.getSupplies()) {
                    int adjustedInterval = (int) Math.round(baseInterval * (industryChanger / 100.0));
                    if (adjustedInterval == 0) adjustedInterval = 1;
                    if (currentMonth % adjustedInterval == 0) {
                        suppliedCargo.addQuantity(1);
                    }
                }
            }
        }
    }

    /** Updates demand at stations based on deliveries and adjusts resource values. */
    private void updateDemand() {
        for (Route route : routes) {
            for (PointOfRoute point : route.getPointsOfRoute()) {
                Station station = point.getStation();
                int deliveries = station.getDeliveriesCount();
                int oldDemand = station.getDemandLevel();
                int newDemand = oldDemand;

                if (deliveries > 30 && oldDemand > 1) newDemand--;
                if (deliveries < 10 && oldDemand < 9) newDemand++;

                if (newDemand != oldDemand) {
                    for (Cargo cargo : station.getDemands()) {
                        ResourceType rt = cargo.getType();
                        int currentValue = rt.getValue();
                        rt.setValue((int) (currentValue * (newDemand > oldDemand ? 1.15 : 0.85)));
                    }
                    station.setDemandLevel(newDemand);
                }

                station.resetDeliveries();
            }
        }
    }

    /** Simulates the path of a locomotive through its route. */
    public void simulateRoute(Route route, Locomotive loco) {
        List<PointOfRoute> points = route.getPointsOfRoute();
        int totalCapacity = 24;
        int loadedCargo = 0;

        for (int i = 0; i < points.size(); i++) {
            PointOfRoute currentPoint = points.get(i);
            loadedCargo += loadCargoFromStation(currentPoint);

            while (!canDepart(currentPoint, loadedCargo, totalCapacity)) {
                System.out.println("Waiting to depart at " + currentPoint.getStation().getName() +
                        " with cargo mode " + currentPoint.getCargoMode());
                break;
            }

            if (i < points.size() - 1) {
                PointOfRoute nextPoint = points.get(i + 1);
                travelToNextPoint(loco, currentPoint, nextPoint);
                System.out.println("Traveling from " + currentPoint.getStation().getName() +
                        " to " + nextPoint.getStation().getName());
            }
        }
    }

    /** Checks if there are conditions to depart from the current point based on cargo mode. */
    private boolean canDepart(PointOfRoute point, int loadedCargo, int totalCapacity) {
        CargoMode mode = point.getCargoMode();
        switch (mode) {
            case FULL: return loadedCargo >= totalCapacity;
            case HALF: return loadedCargo >= totalCapacity / 2;
            case AVAILABLE: return true;
            default: return true;
        }
    }

    /** Loads available cargo at the point. */
    private int loadCargoFromStation(PointOfRoute point) {
        List<Cargo> selectedToLoad = point.getCargoList();
        point.getCargoList().clear();

        int totalLoaded = 0;
        for (Cargo cargo : selectedToLoad) {
            if (cargo.getQuantity() > 0) {
                point.getCargoList().add(new Cargo(cargo.getType(), cargo.getQuantity()));
                totalLoaded += cargo.getQuantity();
            }
        }

        return totalLoaded;
    }

    /** Simulates the movement of the locomotive between two points. */
    private void travelToNextPoint(Locomotive loco, PointOfRoute from, PointOfRoute to) {
        Railway railway = new Railway(from.getStation(), to.getStation(), RailwayType.DOUBLE, from.getStation().getPosition().euclideanDistance(to.getStation().getPosition()));
        double distance = railway.getDistance();

        locomotiveDistanceMap.merge(loco, distance, Double::sum);
        System.out.println("Locomotive " + loco.getId() + " traveling from " +
                from.getStation().getName() + " to " + to.getStation().getName());
    }

    /** Generates the financial report of the year. */
    public String generateFinancialReport() {
        yearlyTrackMaintenance = calculateTrackMaintenance();
        yearlyTrainMaintenance = calculateTrainMaintenance();
        yearlyFuelCost = calculateFuelCost();

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Year ").append(scenario.getYear() + (currentMonth - 1) / 12).append(" Financial Results ===\n");
        sb.append(String.format("Revenues (Cargo Deliveries): $%,.2f%n", yearlyRevenue));
        sb.append("\nExpenses:\n");
        sb.append(String.format("  - Track Maintenance: $%,.2f%n", yearlyTrackMaintenance));
        sb.append(String.format("  - Train Maintenance: $%,.2f%n", yearlyTrainMaintenance));
        sb.append(String.format("  - Fuel: $%,.2f%n", yearlyFuelCost));

        double totalExpenses = yearlyTrackMaintenance + yearlyTrainMaintenance + yearlyFuelCost;
        double netProfit = yearlyRevenue - totalExpenses;

        sb.append("\nSummary:\n");
        sb.append(String.format("Total Revenue: $%,.2f%n", yearlyRevenue));
        sb.append(String.format("Total Expenses: $%,.2f%n", totalExpenses));
        sb.append(String.format("Net Profit: $%,.2f%n", netProfit));

        return sb.toString();
    }

    /** Calculates the maintenance cost of trains. */
    private double calculateTrainMaintenance() {
        double total = 0;
        for (Route route : routes) {
            if (route.getLocomotive() != null) {
                LocomotiveModel model = route.getLocomotive().getLocomotiveModel();
                total += model.getMaintenancePYear();
            }
        }
        return total;
    }

    /** Calculates fuel cost based on distance traveled and locomotive type. */
    private double calculateFuelCost() {
        double total = 0;
        for (java.util.Map.Entry<Locomotive, Double> entry : locomotiveDistanceMap.entrySet()) {
            Locomotive loco = entry.getKey();
            double distance = entry.getValue();
            LocomotiveModel model = loco.getLocomotiveModel();

            double fuelFactor = switch (model.getLocomotiveType()) {
                case STEAM -> 1.5;
                case DIESEL -> 1.2;
                case ELECTRIC -> 0.8;
            };

            total += (model.getFuelCost() / 1000.0) * distance * fuelFactor;
        }
        return total;
    }

    /** Calculates the maintenance cost of the used railways. */
    private double calculateTrackMaintenance() {
        double totalCost = 0;
        List<Railway> uniqueRailways = new ArrayList<>();

        for (Route route : routes) {
            List<PointOfRoute> points = route.getPointsOfRoute();
            for (int i = 0; i < points.size() - 1; i++) {
                Station s1 = points.get(i).getStation();
                Station s2 = points.get(i + 1).getStation();
                Railway railway = Railway.getRailwayBetween(s1, s2);

                if (!uniqueRailways.contains(railway)) {
                    uniqueRailways.add(railway);
                }
            }
        }

        for (Railway railway : uniqueRailways) {
            totalCost += railway.getType().getCost() * railway.getDistance();
        }

        return totalCost;
    }

    /** Generates a textual summary of the simulated month. */
    private String generateMonthlySummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Summary for Month ").append(currentMonth).append("\n");
        sb.append("Player budget: ").append(player.getBudget()).append("\n");
        sb.append("Alerts:\n");
        for (String alert : alerts) {
            sb.append(" - ").append(alert).append("\n");
        }
        return sb.toString();
    }

    // Getters
    public String getLastMonthSummary() { return lastMonthSummary; }
    public List<Route> getRoutes() { return routes; }
    public Player getPlayer() { return player; }
    public Scenario getScenario() { return scenario; }
    public int getCurrentMonth() { return currentMonth - 1; }
    public List<String> getAlerts() { return alerts; }
    public String getName() { return scenario.getMap().getName() + "-" + currentMonth; }

    /** Returns the current date of the simulation (year and month). */
    public String getCurrentSimulationDate() {
        int year = scenario.getYear() + (currentMonth - 1) / 12;
        int month = ((currentMonth - 1) % 12) + 1;
        return "Year " + year + ", Month " + month;
    }
}
