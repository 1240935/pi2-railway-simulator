package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.Repositories;

import java.util.*;
import java.io.Serializable;

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
        simulateArrivals();
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

        // Atualiza o resumo do mês anterior
        lastMonthSummary = generateMonthlySummary();

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

    private void simulateArrivals() {
        for (Route route : routes) {
            Locomotive loco = route.getLocomotive();
            if (loco == null) continue;

            for (int i = 0; i < route.getPointsOfRoute().size() - 1; i++) {
                PointOfRoute departure = route.getPointsOfRoute().get(i);
                PointOfRoute arrival = route.getPointsOfRoute().get(i + 1);
                Railway railway = Railway.getRailwayBetween(departure.getStation(), arrival.getStation());

                double scaledDistance = railway.getDistance() * scenario.getMap().getScale();
                double baseRevenue = 10.0 * scaledDistance;

                int demand = arrival.getStation().getDemandLevel();
                double priceMultiplier = 1.0;
                if (demand > 5) priceMultiplier += 0.15 * (demand - 5);
                else if (demand < 5) priceMultiplier -= 0.15 * (5 - demand);

                double bonus = arrival.getStation().getTotalRevenueBonus();
                priceMultiplier += bonus;

                double finalRevenue = baseRevenue * priceMultiplier;
                yearlyRevenue += finalRevenue;

                int totalCargoDelivered = 0;
                StringBuilder cargoNames = new StringBuilder();

                for (Cargo cargo : arrival.getStation().getSupplies()) {
                    int quantity = cargo.getQuantity();
                    if (quantity > 0) {
                        totalCargoDelivered += quantity;
                        cargoNames.append(cargo.getType().name()).append("(").append(quantity).append("), ");
                    }
                }
                if (cargoNames.length() > 2) {
                    cargoNames.setLength(cargoNames.length() - 2);
                }

                player.addBudget((int) finalRevenue);
                arrival.getStation().incrementDeliveries();

                if (totalCargoDelivered > 0) {
                    System.out.println("Added " + (int) finalRevenue + " to player budget for delivering " +
                            totalCargoDelivered + " units of cargo (" + cargoNames + ") at " + arrival.getStation().getName());
                }
            }
        }
    }

    private void checkNewLocomotivesAvailable() {
        List<LocomotiveModel> alertedModels = new ArrayList<>();
        int currentYear = scenario.getYear() + (currentMonth / 12);

        List<LocomotiveModel> models = Repositories.getInstance().getLocomotiveModelRepository().getLocomotiveModels();

        for (LocomotiveModel model : models) {
            int modelStartYear = model.getStartYear();

            if (modelStartYear == currentYear && !alertedModels.contains(model)) {
                alerts.add("New locomotive available: " + model.name());
                alertedModels.add(model);
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
                int demand = station.getDemandLevel();

                if (deliveries > 30 && demand > 1) demand--;
                if (deliveries < 10 && demand < 9) demand++;

                station.setDemandLevel(demand);
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

            // Load cargo at the current station
            loadedCargo += loadCargoFromStation(currentPoint.getStation());

            // Wait until departure condition is satisfied
            while (!canDepart(currentPoint, loadedCargo, totalCapacity)) {
                // Em uma simulação real, avançaria o tempo ou trataria espera aqui
                System.out.println("Waiting to depart at " + currentPoint.getStation().getName() +
                        " with cargo mode " + currentPoint.getCargoMode());
                // Para evitar loop infinito, sai aqui
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

    private int loadCargoFromStation(Station station) {
        int loaded = 0;

        if (station.getSupplies() != null) {
            for (Cargo cargo : station.getSupplies()) {
                int quantityToLoad = cargo.getQuantity();
                loaded += quantityToLoad;
                cargo.setQuantity(0);
            }
        }

        return loaded;
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
    private double calculateTrackMaintenance() {
        double totalCost = 0;
        Set<Railway> uniqueRailways = new HashSet<>();

        for (Route route : routes) {
            List<PointOfRoute> points = route.getPointsOfRoute();
            for (int i = 0; i < points.size() - 1; i++) {
                Station s1 = points.get(i).getStation();
                Station s2 = points.get(i + 1).getStation();
                Railway railway = Railway.getRailwayBetween(s1, s2);
                uniqueRailways.add(railway);
            }
        }

        for (Railway railway : uniqueRailways) {
            // Custo baseado no tipo de via (SINGLE, DOUBLE, etc.)
            double maintenanceCost = railway.getType().getCost() * railway.getDistance();
            totalCost += maintenanceCost;
        }

        return totalCost;
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
    private double calculateTotalTrackLength() {
        Set<Railway> uniqueRailways = new HashSet<>();

        for (Route route : routes) {
            List<PointOfRoute> points = route.getPointsOfRoute();
            for (int i = 0; i < points.size() - 1; i++) {
                Station s1 = points.get(i).getStation();
                Station s2 = points.get(i + 1).getStation();
                Railway railway = Railway.getRailwayBetween(s1, s2);
                uniqueRailways.add(railway);
            }
        }

        double totalLength = 0;
        for (Railway railway : uniqueRailways) {
            totalLength += railway.getDistance();
        }

        return totalLength;
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
        return sb.toString();
    }

    // Retorna o resumo do último mês simulado
    public String getLastMonthSummary() {
        if (lastMonthSummary == null) {
            return "No month simulated yet.";
        }
        return lastMonthSummary;
    }

    public String getSimulationSummary(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("Simulation Summary:\n");
        sb.append("Player budget: ").append(player.getBudget()).append("\n");
        sb.append("Month: ").append(currentMonth).append("\n");
        for (String alert : alerts) {
            sb.append("Alert: ").append(alert).append("\n");
        }
        return sb.toString();
    }
    public String getName() {
        return scenario.getMap().getName() + "-" + currentMonth;
    }

    public String getCurrentSimulationDate() {
        int year = scenario.getYear() + (currentMonth - 1) / 12;
        int month = ((currentMonth - 1) % 12) + 1;
        return "Year " + year + ", Month " + month;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public Player getPlayer() {
        return player;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public List<String> getAlerts() {
        return alerts;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
