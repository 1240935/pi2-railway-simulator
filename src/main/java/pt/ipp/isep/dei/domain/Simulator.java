// package pt.ipp.isep.dei.domain;
//
//import java.util.List;
//
///**
// * Simulator class responsible for simulating the daily operations of routes within a scenario.
// * <p>
// * It manages cargo generation, delivery, and pickup at stations along each route.
// * The player's budget is updated based on cargo picked up during simulation.
// */
//public class Simulator {
//    private List<Route> routes;
//    private Player player;
//    private Scenario scenario;
//    private int totalGenerated = 0;
//    private int totalResourcesDelivered = 0;
//
//    /**
//     * Constructs a Simulator instance with specified routes, player, and scenario.
//     *
//     * @param routes   List of routes to be simulated.
//     * @param player   Player who owns or interacts with the simulation.
//     * @param scenario Scenario context for the simulation.
//     */
//    public Simulator(List<Route> routes, Player player, Scenario scenario) {
//        this.routes = routes;
//        this.player = player;
//        this.scenario = scenario;
//    }
//
//    /**
//     * Simulates the activities for a given day.
//     * <p>
//     * On even days, cargo is added to stations along each route.
//     * On days divisible by the number of stations in the route,
//     * cargo is delivered and picked up, and the player's budget is increased
//     * based on the value of picked-up cargo.
//     *
//     * @param day The day number to simulate.
//     */
//    public void simulateDay(int day) {
//        System.out.println("Simulating day " + day);
//
//        for (Route route : routes) {
//            int numStations = route.getRouteStations().size();
//
//            if (day % 2 == 0) {
//                for (Station station : route.getRouteStations()) {
//                    addCargoToStation(station, route);
//                }
//            }
//
//            if (day % numStations == 0) {
//                System.out.println("Delivering and picking up cargo for route " + route);
//
//                for (Station station : route.getRouteStations()) {
//                    deliverCargoToStation(station);
//                    int totalValuePickedUp = pickupCargoFromStation(route, station);
//
//                    player.addBudget(totalValuePickedUp);
//                    System.out.println("Added " + totalValuePickedUp + " to player budget from cargo pickup at station " + station.getName());
//                }
//            }
//        }
//    }
//
//    /**
//     * Adds cargo items from the route's cargo list to the station's supplies.
//     * <p>
//     * If the cargo type already exists in the station's supplies, increases quantity by 1.
//     * Otherwise, adds a new cargo item with quantity 1.
//     * Also increments the total generated cargo count.
//     *
//     * @param station Station to which cargo will be added.
//     * @param route   Route providing the cargo types.
//     */
//    private void addCargoToStation(Station station, Route route) {
//        List<Cargo> stationSupplies = station.getSupplies();
//        List<Cargo> routeCargo = route.getCargo();
//
//        for (Cargo routeCargoItem : routeCargo) {
//            Cargo existingCargo = null;
//
//            for (Cargo stationCargo : stationSupplies) {
//                if (stationCargo.getType().equals(routeCargoItem.getType())) {
//                    existingCargo = stationCargo;
//                    break;
//                }
//            }
//
//            if (existingCargo != null) {
//                existingCargo.setQuantity(existingCargo.getQuantity() + 1);
//            } else {
//                Cargo newCargo = new Cargo(routeCargoItem.getType(), 1);
//                stationSupplies.add(newCargo);
//            }
//
//            totalGenerated++;
//        }
//    }
//
//    /**
//     * Delivers cargo to the station by reducing the supply quantities based on demands.
//     * <p>
//     * For each demand at the station, the method delivers as much as possible from supplies,
//     * adjusting quantities accordingly and tracking total resources delivered.
//     *
//     * @param station Station at which cargo is delivered.
//     */
//    private void deliverCargoToStation(Station station) {
//        List<Cargo> demands = station.getDemands();
//        List<Cargo> supplies = station.getSupplies();
//
//        for (Cargo demand : demands) {
//            for (Cargo supply : supplies) {
//                if (supply.getType().equals(demand.getType())) {
//                    int quantityToDeliver = Math.min(demand.getQuantity(), supply.getQuantity());
//
//                    supply.setQuantity(supply.getQuantity() - quantityToDeliver);
//                    totalResourcesDelivered += quantityToDeliver;
//
//                    System.out.println("Delivered " + quantityToDeliver + " of " +
//                            supply.getType() + " at station " + station.getName());
//                }
//            }
//        }
//    }
//
//    /**
//     * Picks up cargo from the station according to the route's cargo needs.
//     * <p>
//     * Calculates the total value of picked-up cargo based on type value and quantity,
//     * sets the station's cargo quantities to zero after pickup.
//     *
//     * @param route   The route for which cargo is picked up.
//     * @param station The station from which cargo is picked up.
//     * @return The total value of cargo picked up.
//     */
//    private int pickupCargoFromStation(Route route, Station station) {
//        int totalValue = 0;
//
//        for (Cargo cargoToPick : route.getCargo()) {
//            for (Cargo stationCargo : station.getSupplies()) {
//                if (stationCargo.getType().equals(cargoToPick.getType()) && stationCargo.getQuantity() > 0) {
//                    int quantity = stationCargo.getQuantity();
//
//                    totalValue += cargoToPick.getType().getValue() * quantity;
//                    System.out.println("Picked up " + quantity + " of " + cargoToPick.getType() +
//                            " worth " + cargoToPick.getType().getValue() + " each at station " + station.getName());
//
//                    stationCargo.setQuantity(0);
//                }
//            }
//        }
//
//        return totalValue;
//    }
//
//    /**
//     * Provides a summary of the simulation including total resources generated,
//     * total resources delivered, and the player's final budget.
//     *
//     * @param player The player whose budget and results are summarized.
//     * @return A formatted string summarizing the simulation results.
//     */
//    public String getSimulationSummary(Player player) {
//        return "\nSimulation Summary: \n" +
//                "Total Resources Generated: " + totalGenerated + "\n" +
//                "Total Resources Delivered: " + totalResourcesDelivered + "\n" +
//                "Final Player Budget: " + player.getBudget() + "\n";
//    }
//
//    /**
//     * Returns the current scenario associated with this simulation.
//     *
//     * @return The scenario object.
//     */
//    public Scenario getScenario() {
//        return scenario;
//    }
//
//    /**
//     * Sets a new scenario for the simulation.
//     *
//     * @param scenario The scenario to set.
//     */
//    public void setScenario(Scenario scenario) {
//        this.scenario = scenario;
//    }
//}
