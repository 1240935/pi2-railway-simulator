package pt.ipp.isep.dei.domain;

import java.util.List;

public class Simulator {
    private List<Route> routes;
    private Player player;
    private Scenario scenario;
    private int totalGenerated = 0;
    private int totalResourcesDelivered = 0;


    public Simulator(List<Route> routes, Player player, Scenario scenario) {
        this.routes = routes;
        this.player = player;
        this.scenario = scenario;
    }

    public void simulateDay(int day) {
        System.out.println("Simulating day " + day);

        for (Route route : routes) {
            int numStations = route.getRouteStations().size();

            if (day % 2 == 0) {
                for (Station station : route.getRouteStations()) {
                    addCargoToStation(station, route);
                }
            }

            if (day % numStations == 0) {
                System.out.println("Delivering and picking up cargo for route " + route);

                for (Station station : route.getRouteStations()) {
                    deliverCargoToStation(station);
                    int totalValuePickedUp = pickupCargoFromStation(route, station);

                    player.addBudget(totalValuePickedUp);
                    System.out.println("Added " + totalValuePickedUp + " to player budget from cargo pickup at station " + station.getName());
                }
            }
        }
    }

    private void addCargoToStation(Station station, Route route) {
        List<Cargo> stationSupplies = station.getSupplies();
        List<Cargo> routeCargo = route.getCargo();

        for (Cargo routeCargoItem : routeCargo) {
            Cargo existingCargo = null;

            for (Cargo stationCargo : stationSupplies) {
                if (stationCargo.getType().equals(routeCargoItem.getType())) {
                    existingCargo = stationCargo;
                    break;
                }
            }

            if (existingCargo != null) {
                existingCargo.setQuantity(existingCargo.getQuantity() + 1);
            } else {
                Cargo newCargo = new Cargo(routeCargoItem.getType(), 1);
                stationSupplies.add(newCargo);
            }

            totalGenerated++;
        }
    }

    private void deliverCargoToStation(Station station) {
        List<Cargo> demands = station.getDemands();
        List<Cargo> supplies = station.getSupplies();

        for (Cargo demand : demands) {
            for (Cargo supply : supplies) {
                if (supply.getType().equals(demand.getType())) {
                    int quantityToDeliver = Math.min(demand.getQuantity(), supply.getQuantity());

                    supply.setQuantity(supply.getQuantity() - quantityToDeliver);
                    totalResourcesDelivered += quantityToDeliver;

                    System.out.println("Delivered " + quantityToDeliver + " of " +
                            supply.getType() + " at station " + station.getName());
                }
            }
        }
    }


    private int pickupCargoFromStation(Route route, Station station) {
        int totalValue = 0;

        for (Cargo cargoToPick : route.getCargo()) {
            for (Cargo stationCargo : station.getSupplies()) {
                if (stationCargo.getType().equals(cargoToPick.getType()) && stationCargo.getQuantity() > 0) {
                    int quantity = stationCargo.getQuantity();

                    totalValue += cargoToPick.getType().getValue() * quantity;
                    System.out.println("Picked up " + quantity + " of " + cargoToPick.getType() +
                            " worth " + cargoToPick.getType().getValue() + " each at station " + station.getName());

                    stationCargo.setQuantity(0);
                }
            }
        }

        return totalValue;
    }

    public String getSimulationSummary(Player player) {
        return "\nSimulation Summary: \n" +
                "Total Resources Generated: " + totalGenerated + "\n" +
                "Total Resources Delivered: " + totalResourcesDelivered + "\n" +
                "Final Player Budget: " + player.getBudget() + "\n";
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
