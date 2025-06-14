package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Simulator implements Serializable {

    private List<Route> routes;
    private Player player;
    private Scenario scenario;
    private int currentMonth;
    private List<String> alerts;
    private String lastMonthSummary;

    private List<LocomotiveModel> alertedModels = new ArrayList<>();

    public Simulator(List<Route> routes, Player player, Scenario scenario) {
        this.routes = routes;
        this.player = player;
        this.scenario = scenario;
        this.currentMonth = 1;
        this.alerts = new ArrayList<>();
    }

    public void simulateMonth() {
        System.out.println("=== Month " + currentMonth + " ===");
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
        }

        // Atualiza o resumo do mês anterior
        lastMonthSummary = generateMonthlySummary();

        currentMonth++;
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
        System.out.println("Locomotive " + loco.getId() + " traveling from " +
                from.getStation().getName() + " to " + to.getStation().getName());
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
}
