package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.Repositories;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulator handles the month-by-month simulation of a railway logistics game.
 * It tracks time progression, cargo delivery, locomotive movements, and financial metrics.
 */
public class Simulator implements Serializable {
    // Active routes in the simulation
    private List<Route> routes;

    // The player participating in the simulation
    private Player player;

    // Scenario under which the simulation is running (year, map, etc.)
    private Scenario scenario;

    // Simulation clock (1 = January of scenario start year)
    private int currentMonth;

    // Alerts to display during the month
    private List<String> alerts;

    // Summary of events in the last simulated month
    private String lastMonthSummary;

    // Yearly financial metrics
    private double yearlyRevenue;
    private double yearlyTrackMaintenance;
    private double yearlyTrainMaintenance;
    private double yearlyFuelCost;

    // Tracks each locomotive used during the year and its travelled distance
    private List<Locomotive> locomotives;
    private List<Double> distances;

    // Models that triggered an alert to avoid re-alerting
    private List<LocomotiveModel> alertedModels;

    /**
     * Constructs the simulator with given routes, player, and scenario.
     */
    public Simulator(List<Route> routes, Player player, Scenario scenario) {
        this.routes = routes;
        this.player = player;
        this.scenario = scenario;
        this.currentMonth = 1;
        this.alerts = new ArrayList<>();
        this.locomotives = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.alertedModels = new ArrayList<>();
        resetYearlyFinancials();
    }

    /**
     * Resets all yearly financial statistics.
     */
    private void resetYearlyFinancials() {
        yearlyRevenue = 0;
        yearlyTrackMaintenance = 0;
        yearlyTrainMaintenance = 0;
        yearlyFuelCost = 0;
        locomotives.clear();
        distances.clear();
    }

    /**
     * Adds travel distance for a locomotive; tracks unique locomotives yearly.
     */
    private void addLocomotiveDistance(Locomotive loco, double distance) {
        int index = locomotives.indexOf(loco);
        if (index >= 0) {
            distances.set(index, distances.get(index) + distance);
        } else {
            locomotives.add(loco);
            distances.add(distance);
        }
    }

    /**
     * Simulates a full month: cargo generation, departures, arrivals, revenue, and alerts.
     */
    public void simulateMonth() {
        System.out.println("=== Month " + currentMonth + " ===");

        if (currentMonth % 12 == 1) {
            resetYearlyFinancials(); // reset on January
        }

        generateCargoes();
        checkNewLocomotivesAvailable();
        simulateDepartures();

        // Simulate cargo arrival
        for (Route route : routes) {
            simulateArrivals(route);
        }

        // Simulate route logic and travel
        for (Route route : routes) {
            Locomotive loco = route.getLocomotive();
            if (loco != null) {
                simulateRoute(route, loco);
            }
        }

        // Annual end-of-year updates
        if (currentMonth % 12 == 0) {
            updateDemand();
            System.out.println(generateFinancialReport());
        }

        // Store monthly summary and reset alerts
        lastMonthSummary = generateMonthlySummary();
        alerts.clear();
        currentMonth++;
    }

    /**
     * Simulates travel time between stations based on route conditions and cargo load.
     */
    private void simulateDepartures() {
        for (Route route : routes) {
            Locomotive loco = route.getLocomotive();
            if (loco == null) continue;

            int maxSpeed = loco.getLocomotiveModel().getTopSpeed();
            int numCarriages = 0;
            for (PointOfRoute por : route.getPointsOfRoute()) {
                numCarriages += por.getCargoList().size();
            }

            // Penalty reduces speed up to 30% if heavily loaded
            double speedPenalty = Math.min(0.05 * numCarriages, 0.30);

            // For each leg of the journey
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

    /**
     * Simulates cargo deliveries and revenue generation at each route destination.
     */
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
                if (carried.getQuantity() <= 0) carriedCargo.remove(carried);
            }

            // Revenue bonuses from station
            double bonus = station.getTotalRevenueBonus();
            priceMultiplier += bonus;
            double finalRevenue = baseRevenue * priceMultiplier;
            yearlyRevenue += finalRevenue;

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
        }
    }

    /**
     * Issues alerts when new locomotive models become available.
     */
    private void checkNewLocomotivesAvailable() {
        int currentYear = scenario.getYear() + (currentMonth / 12);
        List<LocomotiveModel> models = Repositories.getInstance()
                .getLocomotiveModelRepository()
                .getLocomotiveModels();

        for (LocomotiveModel model : models) {
            int modelStartYear = model.getStartYear();
            if (modelStartYear == currentYear && !alertedModels.contains(model)) {
                String alertMessage = "New locomotive available: " + model.name();
                alerts.add(alertMessage);
                alertedModels.add(model);
                System.out.println(alertMessage);
            }
        }
    }

    /**
     * Generates new cargoes at stations at intervals influenced by scenario industry factor.
     */
    private void generateCargoes() {
        int industryChanger = scenario.getIndustryChanger();
        int baseInterval = 2;
        int adjustedInterval = Math.max(1, (int) Math.round(baseInterval * (industryChanger / 100.0)));

        if (currentMonth % adjustedInterval == 0) {
            for (Route route : routes) {
                for (PointOfRoute point : route.getPointsOfRoute()) {
                    Station station = point.getStation();
                    List<Cargo> stationSupplies = station
                            .getCargoes(scenario.getMap().getIndustries(), scenario.getMap().getCities())
                            .getSupplied();

                    for (Cargo cargo : stationSupplies) {
                        cargo.setQuantity(cargo.getQuantity() + 1);
                        player.addBudget(cargo.getType().getValue());
                        System.out.println("Generated 1 unit of " + cargo.getType().name() +
                                " at " + station.getName());
                    }
                }
            }
        }
    }

    /**
     * Updates station demand levels annually based on delivery performance.
     */
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
                        rt.setValue(newDemand > oldDemand ?
                                (int) (currentValue * 1.15) : (int) (currentValue * 0.85));
                    }
                    station.setDemandLevel(newDemand);
                }

                station.resetDeliveries();
            }
        }
    }

    /**
     * Simulates route cargo loading, cargo mode check, and train travel for a month.
     */
    public void simulateRoute(Route route, Locomotive loco) {
        List<PointOfRoute> points = route.getPointsOfRoute();
        int totalCapacity = 8;

        for (int i = 0; i < points.size(); i++) {
            PointOfRoute currentPoint = points.get(i);
            int loadedCargo = loadCargoFromStation(currentPoint);

            if (!canDepart(currentPoint, loadedCargo, totalCapacity)) {
                System.out.println("Waiting to depart at " + currentPoint.getStation().getName() +
                        " with cargo mode " + currentPoint.getCargoMode());
                continue;
            }

            if (i < points.size() - 1) {
                PointOfRoute nextPoint = points.get(i + 1);
                System.out.println("Traveling from " + currentPoint.getStation().getName() +
                        " to " + nextPoint.getStation().getName());
                travelToNextPoint(loco, currentPoint, nextPoint);
            }
        }
    }

    /**
     * Checks if a train can depart based on cargo mode and loaded quantity.
     */
    private boolean canDepart(PointOfRoute point, int loadedCargo, int totalCapacity) {
        CargoMode mode = point.getCargoMode();
        return switch (mode) {
            case FULL -> loadedCargo >= totalCapacity;
            case HALF -> loadedCargo >= totalCapacity / 2;
            case AVAILABLE -> true;
            default -> true;
        };
    }

    /**
     * Loads cargo into the train from the station.
     */
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

    /**
     * Moves the locomotive from one station to the next and logs the event.
     */
    private void travelToNextPoint(Locomotive loco, PointOfRoute from, PointOfRoute to) {
        Railway railway = new Railway(from.getStation(), to.getStation(),
                RailwayType.DOUBLE,
                from.getStation().getPosition().euclideanDistance(to.getStation().getPosition()));

        double distance = railway.getDistance();
        addLocomotiveDistance(loco, distance);

        System.out.println("Locomotive " + loco.getId() + " traveling from " +
                from.getStation().getName() + " to " + to.getStation().getName());
    }

    /**
     * Generates and prints a financial report at the end of each year.
     */
    public String generateFinancialReport() {
        yearlyTrackMaintenance = calculateTrackMaintenance();
        yearlyTrainMaintenance = calculateTrainMaintenance();
        yearlyFuelCost = calculateFuelCost();

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Year ").append(scenario.getYear() + (currentMonth - 1) / 12).append(" Financial Results ===\n");

        sb.append(String.format("Revenues (Cargo Deliveries): %,.2f%n", yearlyRevenue));
        sb.append("\nExpenses:\n");
        sb.append(String.format("  - Track Maintenance: %,.2f%n", yearlyTrackMaintenance));
        sb.append(String.format("  - Train Maintenance: %,.2f%n", yearlyTrainMaintenance));
        sb.append(String.format("  - Fuel: %,.2f%n", yearlyFuelCost));

        double totalExpenses = yearlyTrackMaintenance + yearlyTrainMaintenance + yearlyFuelCost;
        double net = yearlyRevenue - totalExpenses;
        sb.append(String.format("Net Result: %,.2f%n", net));

        return sb.toString();
    }


    /**
     * Calculates the total yearly maintenance cost of all locomotives used in the routes.
     * It sums the maintenance cost per year for each locomotive model assigned to the routes.
     *
     * @return total maintenance cost for all locomotives in the routes
     */
    private double calculateTrainMaintenance() {
        double total = 0;
        for (Route route : routes) {
            if (route.getLocomotive() != null) {  // Check if the route has a locomotive assigned
                LocomotiveModel model = route.getLocomotive().getLocomotiveModel();
                total += model.getMaintenancePYear();  // Add the maintenance cost per year for this locomotive model
            }
        }
        return total;
    }

    /**
     * Calculates the total fuel cost based on locomotives and distances traveled.
     * Adjusts fuel cost by a factor depending on the locomotive type (Steam, Diesel, Electric).
     *
     * @return total fuel cost for all locomotives based on distances
     */
    private double calculateFuelCost() {
        double total = 0;
        for (int i = 0; i < locomotives.size(); i++) {
            Locomotive loco = locomotives.get(i);
            double distance = distances.get(i);
            LocomotiveModel model = loco.getLocomotiveModel();

            // Determine fuel cost multiplier based on locomotive type
            double fuelFactor = 1.0;
            switch (model.getLocomotiveType()) {
                case STEAM:
                    fuelFactor = 1.5;
                    break;    // Steam locomotives consume more fuel
                case DIESEL:
                    fuelFactor = 1.2;
                    break;   // Diesel locomotives have moderate fuel consumption
                case ELECTRIC:
                    fuelFactor = 0.8;
                    break; // Electric locomotives are more efficient
            }

            // Calculate cost = (cost per unit fuel / 1000) * distance * fuel factor
            total += (model.getFuelCost() / 1000.0) * distance * fuelFactor;
        }
        return total;
    }

    /**
     * Calculates total track maintenance cost by summing costs for unique railway segments
     * used across all routes. Each railway segment cost is its maintenance cost per unit distance
     * multiplied by the distance.
     *
     * @return total track maintenance cost
     */
    private double calculateTrackMaintenance() {
        double totalCost = 0;
        List<Railway> uniqueRailways = new ArrayList<>();

        // Collect unique railway segments from all routes
        for (Route route : routes) {
            List<PointOfRoute> points = route.getPointsOfRoute();
            for (int i = 0; i < points.size() - 1; i++) {
                Station s1 = points.get(i).getStation();
                Station s2 = points.get(i + 1).getStation();
                Railway railway = Railway.getRailwayBetween(s1, s2);

                // Check if this railway segment is already counted
                boolean alreadyExists = false;
                for (Railway r : uniqueRailways) {
                    if (r.equals(railway)) {  // Assuming Railway has a proper equals() method
                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists) {
                    uniqueRailways.add(railway);  // Add unique railway segment
                }
            }
        }

        // Sum maintenance cost for each unique railway segment
        for (Railway railway : uniqueRailways) {
            double maintenanceCost = railway.getType().getCost() * railway.getDistance();
            totalCost += maintenanceCost;
        }

        return totalCost;
    }

    /**
     * Generates a summary string of the current month, including player budget and alerts.
     *
     * @return summary string with month, budget, and alerts info
     */
    private String generateMonthlySummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Summary for Month ").append(currentMonth).append("\n");
        sb.append("Player budget: ").append(player.getBudget()).append("\n");
        sb.append("Alerts:\n");
        for (String alert : alerts) {
            sb.append(" - ").append(alert).append("\n");
        }
        // Additional summary details could be added here if desired
        return sb.toString();
    }

    // Returns the summary string of the last simulated month
    public String getLastMonthSummary() {
        return lastMonthSummary;
    }

    // Returns the list of routes currently considered in the simulation/game
    public List<Route> getRoutes() {
        return routes;
    }

    // Returns the player object for accessing player data like budget, name, etc.
    public Player getPlayer() {
        return player;
    }

    // Returns the current scenario object, including map, year, etc.
    public Scenario getScenario() {
        return scenario;
    }

    // Returns the number of the current month in the simulation (adjusted by subtracting 1)
    public int getCurrentMonth() {
        return currentMonth - 1;
    }

    // Returns the list of alerts generated during simulation or gameplay
    public List<String> getAlerts() {
        return alerts;
    }

    // Returns a name string composed of the scenario map name and current month number
    public String getName() {
        return scenario.getMap().getName() + "-" + currentMonth;
    }

    /**
     * Returns the formatted current simulation date as "Year X, Month Y"
     * based on scenario start year and current month number.
     *
     * @return formatted simulation date string
     */
    public String getCurrentSimulationDate() {
        int year = scenario.getYear() + (currentMonth - 1) / 12;
        int month = ((currentMonth - 1) % 12) + 1;
        return "Year " + year + ", Month " + month;
    }
}
