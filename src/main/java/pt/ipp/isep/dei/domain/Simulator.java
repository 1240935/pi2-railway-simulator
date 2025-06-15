package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.Repositories;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public void simulateMonth() {
        System.out.println("=== Month " + currentMonth + " ===");
        if (currentMonth % 12 == 1) {
            resetYearlyFinancials();
        }
        simulateDepartures();

        for (Route route : routes) {
            simulateArrivals(route);
        }

        checkNewLocomotivesAvailable();
        generateCargoes();

        for (Route route : routes) {
            Locomotive loco = route.getLocomotive();
            if (loco != null) {
                simulateRoute(route, loco);
            }
        }

        if (currentMonth % 12 == 0) {
            updateDemand();
            System.out.println(generateFinancialReport());
        }

        lastMonthSummary = generateMonthlySummary();

        alerts.clear();

        currentMonth++;
    }
    private void resetYearlyFinancials() {
        yearlyRevenue = 0;
        yearlyTrackMaintenance = 0;
        yearlyTrainMaintenance = 0;
        yearlyFuelCost = 0;
        locomotiveDistanceMap.clear();
    }


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

    private void simulateArrivals(Route route) {
        List<PointOfRoute> points = route.getPointsOfRoute();
        int totalDelivered = 0;
        StringBuilder deliveryReport = new StringBuilder();

        // Assuming these are accessible fields or you can adjust accordingly
        double baseRevenue = 100.0;   // example base revenue
        double priceMultiplier = 1.0;
        double yearlyRevenue = 0.0;

        for (int i = 1; i < points.size(); i++) {  // start from 1 assuming point 0 is start point
            PointOfRoute point = points.get(i);
            Station station = point.getStation();

            List<Cargo> carriedCargo = point.getCargoList();
            List<Cargo> demands = station.getDemands();

            int deliveredHere = 0;

            // Create a copy of cargo list to safely iterate and modify original list
            List<Cargo> carriedCargoCopy = new ArrayList<>(carriedCargo);

            for (Cargo carried : carriedCargoCopy) {
                for (Cargo demand : demands) {
                    if (carried.getType() == demand.getType()) {
                        int deliverAmount = Math.min(carried.getQuantity(), demand.getQuantity());
                        if (deliverAmount > 0) {
                            // Update quantities
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

                // Remove cargo with zero quantity
                if (carried.getQuantity() <= 0) {
                    carriedCargo.remove(carried);
                }
            }

            // Calculate revenue bonus for this station
            double bonus = station.getTotalRevenueBonus();
            priceMultiplier += bonus;

            double finalRevenue = baseRevenue * priceMultiplier;
            yearlyRevenue += finalRevenue;

            if (deliveredHere > 0) {
                System.out.println(station.getName() + " delivered " + deliveredHere + " units.");
            }

            // Clear cargo at this point (all delivered cargo is removed already)
            carriedCargo.clear();

            // Load new cargo from station to carry forward to next point
            loadCargoFromStation(point);
        }

        if (totalDelivered > 0) {
            int reward = totalDelivered * 10;  // reward calculation example
            player.addBudget(reward);
            System.out.println(deliveryReport.toString());
            System.out.println("Rewarded " + reward + "€ for delivering " + totalDelivered + " units.");
        } else {
            System.out.println("No cargo matched station demands. No budget rewarded.");
        }
    }





    private void checkNewLocomotivesAvailable() {
        int currentYear = scenario.getYear() + (currentMonth / 12);
        List<LocomotiveModel> models = Repositories.getInstance().getLocomotiveModelRepository().getLocomotiveModels();

        for (LocomotiveModel model : models) {
            int modelStartYear = model.getStartYear();

            if (modelStartYear == currentYear && !alertedModels.contains(model)) {
                String alertMessage = "New locomotive available: " + model.name();
                alerts.add(alertMessage);
                alertedModels.add(model);

                // Show alert immediately
                System.out.println(alertMessage);
            }
        }
    }

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

    private void updateDemand() {
        for (Route route : routes) {
            for (int i = 0; i < route.getPointsOfRoute().size(); i++) {
                Station station = route.getPointsOfRoute().get(i).getStation();
                int deliveries = station.getDeliveriesCount();
                int oldDemand = station.getDemandLevel();
                int newDemand = oldDemand;

                if (deliveries > 30 && oldDemand > 1) newDemand--;
                if (deliveries < 10 && oldDemand < 9) newDemand++;

                if (newDemand != oldDemand) {
                    for (Cargo cargo : station.getDemands()) {
                        ResourceType rt = cargo.getType();
                        int currentValue = rt.getValue();
                        if (newDemand > oldDemand) {
                            rt.setValue ((int) (currentValue * 1.15));  // Increase by 15%
                        } else {
                            rt.setValue((int) (currentValue * 0.85));  // Decrease by 15%
                        }
                    }
                    station.setDemandLevel(newDemand);
                }

                station.resetDeliveries();
            }
        }
    }

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

            // Partir para o próximo ponto (se houver)
            if (i < points.size() - 1) {
                PointOfRoute nextPoint = points.get(i + 1);
                travelToNextPoint(loco, currentPoint, nextPoint);
                System.out.println("Traveling from " + currentPoint.getStation().getName() +
                        " to " + nextPoint.getStation().getName());
            }
        }
    }

    private boolean canDepart(PointOfRoute point, int loadedCargo, int totalCapacity) {
        CargoMode mode = point.getCargoMode();
        switch (mode) {
            case FULL:
                return loadedCargo >= totalCapacity;
            case HALF:
                return loadedCargo >= totalCapacity / 2;
            case AVAILABLE:
                return true;
            default:
                return true;
        }
    }

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


    private void travelToNextPoint(Locomotive loco, PointOfRoute from, PointOfRoute to) {
        Railway railway = new Railway(from.getStation(), to.getStation(), RailwayType.DOUBLE, from.getStation().getPosition().euclideanDistance(to.getStation().getPosition()));
        double distance = railway.getDistance();

        locomotiveDistanceMap.merge(loco, distance, Double::sum);
        System.out.println("Locomotive " + loco.getId() + " traveling from " +
                from.getStation().getName() + " to " + to.getStation().getName());
    }
    public String generateFinancialReport() {
        // Calcular despesas
        yearlyTrackMaintenance = calculateTrackMaintenance();
        yearlyTrainMaintenance = calculateTrainMaintenance();
        yearlyFuelCost = calculateFuelCost();

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Year ").append(scenario.getYear() + (currentMonth - 1) / 12).append(" Financial Results ===\n");

        // Receitas
        sb.append(String.format("Revenues (Cargo Deliveries): $%,.2f%n", yearlyRevenue));

        // Despesas
        sb.append("\nExpenses:\n");
        sb.append(String.format("  - Track Maintenance: $%,.2f%n", yearlyTrackMaintenance));
        sb.append(String.format("  - Train Maintenance: $%,.2f%n", yearlyTrainMaintenance));
        sb.append(String.format("  - Fuel: $%,.2f%n", yearlyFuelCost));

        // Resumo
        double totalExpenses = yearlyTrackMaintenance + yearlyTrainMaintenance + yearlyFuelCost;
        double netProfit = yearlyRevenue - totalExpenses;

        sb.append("\nSummary:\n");
        sb.append(String.format("Total Revenue: $%,.2f%n", yearlyRevenue));
        sb.append(String.format("Total Expenses: $%,.2f%n", totalExpenses));
        sb.append(String.format("Net Profit: $%,.2f%n", netProfit));

        return sb.toString();
    }

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

    private double calculateFuelCost() {
        double total = 0;
        for (java.util.Map.Entry<Locomotive, Double> entry : locomotiveDistanceMap.entrySet()) {
            Locomotive loco = entry.getKey();
            double distance = entry.getValue();
            LocomotiveModel model = loco.getLocomotiveModel();

            // Fatores de custo baseados no tipo de locomotiva
            double fuelFactor = 1.0;
            switch(model.getLocomotiveType()) {
                case STEAM: fuelFactor = 1.5; break;
                case DIESEL: fuelFactor = 1.2; break;
                case ELECTRIC: fuelFactor = 0.8; break;
            }

            total += (model.getFuelCost() / 1000.0) * distance * fuelFactor;
        }
        return total;
    }

    private double calculateTrackMaintenance() {
        double totalCost = 0;
        List<Railway> uniqueRailways = new ArrayList<>();

        for (Route route : routes) {
            List<PointOfRoute> points = route.getPointsOfRoute();
            for (int i = 0; i < points.size() - 1; i++) {
                Station s1 = points.get(i).getStation();
                Station s2 = points.get(i + 1).getStation();
                Railway railway = Railway.getRailwayBetween(s1, s2);

                // Check if railway is already in the list
                boolean alreadyExists = false;
                for (Railway r : uniqueRailways) {
                    if (r.equals(railway)) {  // Assuming Railway has a proper equals method
                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists) {
                    uniqueRailways.add(railway);
                }
            }
        }

        for (Railway railway : uniqueRailways) {
            double maintenanceCost = railway.getType().getCost() * railway.getDistance();
            totalCost += maintenanceCost;
        }

        return totalCost;
    }


    // Gera um resumo do mês atual
    private String generateMonthlySummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Summary for Month ").append(currentMonth).append("\n");
        sb.append("Player budget: ").append(player.getBudget()).append("\n");
        sb.append("Alerts:\n");
        for (String alert : alerts) {
            sb.append(" - ").append(alert).append("\n");
        }
        // Aqui pode colocar mais detalhes, se desejar
        return sb.toString();
    }

    // Retorna o resumo do último mês simulado
    public String getLastMonthSummary() {
        return lastMonthSummary;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Player getPlayer() {
        return player;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public int getCurrentMonth() {
        return currentMonth - 1;
    }

    public List<String> getAlerts() {
        return alerts;
    }

    public String getName() {
        return scenario.getMap().getName() + "-" + currentMonth;
    }

    public String getCurrentSimulationDate() {
        int year = scenario.getYear() + (currentMonth - 1) / 12;
        int month = ((currentMonth - 1) % 12) + 1;
        return "Year " + year + ", Month " + month;
    }
}
