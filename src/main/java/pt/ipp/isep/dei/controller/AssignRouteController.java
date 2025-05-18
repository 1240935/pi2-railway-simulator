package pt.ipp.isep.dei.controller;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

public class AssignRouteController {

    private Scenario selectedScenario;
    private Station firstStation;
    private Station lastStation;
    private List<Station> stops;
    private Route selectedRoute;
    private Locomotive selectedLocomotive;
    private List<Cargo> selectedCargo;

    public AssignRouteController(){

    }

    public List<Cargo> addAllCargo(List<Cargo> supplies, List<Cargo> demands) {
        List<Cargo> allCargo = new ArrayList<>();
        allCargo.addAll(supplies);
        allCargo.addAll(demands);
        return allCargo;
    }

    public List<Locomotive> getAvailableLocomotives() {
        // return available scenarios
        return Repositories.getInstance().getLocomotiveRepository().getAll();
    }

    public List<String> getAvailableLocomotivesNames() {
        List<String> names = new ArrayList<>();
        for (Locomotive locomotive : getAvailableLocomotives()) {
            names.add(locomotive.toString());
        }
        return names;
    }

        public List<Scenario> getAvailableScenarios() {
            // return available scenarios
            return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    public List<String> getAvailableScenarioNames() {
        List<String> names = new ArrayList<>();
        for (Scenario scenario : getAvailableScenarios()) {
            names.add(scenario.toString());
        }
        return names;
    }

    public void addCargo(Cargo cargo){
        selectedCargo.add(cargo);
    }

    public List<Station> getAvailableStations() {
        // return available scenarios
        return Repositories.getInstance().getStationRepository().getStations();
    }

    public List<String> getAvailableStationNames() {
        List<String> names = new ArrayList<>();
        for (Station station : getAvailableStations()) {
            names.add(station.toString());
        }
        return names;
    }

    public Scenario chooseScenario(List<Scenario> scenarios, int choice){
        Scenario scenario = scenarios.get(choice-1);
        return scenario;
    }

    public Locomotive chooseLocomotive(List<Locomotive> locomotives, int choice){
        Locomotive locomotive = locomotives.get(choice-1);
        return locomotive;
    }

    public Station chooseStation(List<Station> stations, int choice){
        Station station = stations.get(choice-1);
        return station;
    }

    public void addStation(Station station){
        stops.add(station);
    }

    public void setFirstStation(Station selectedStation) {
        this.firstStation = selectedStation;
    }

    public void setLastStation(Station lastStation) {
        this.lastStation = lastStation;
    }

    public  List<Cargo> getSupply(){
            List<Cargo> supplyCargoes = new ArrayList<>();

            for (Station stop : stops) {
                supplyCargoes.addAll(stop.getSupplies());
            }
            supplyCargoes.addAll(firstStation.getSupplies());
            supplyCargoes.addAll(lastStation.getSupplies());
            return supplyCargoes;
    }

    public  List<Cargo> getDemand(){
        List<Cargo> demandCargoes = new ArrayList<>();

        for (Station stop : stops) {
            demandCargoes.addAll(stop.getDemands());
        }
        demandCargoes.addAll(firstStation.getDemands());
        demandCargoes.addAll(lastStation.getDemands());
        return demandCargoes;
    }

    public void printCargoes(List<Cargo> suppliesList, List<Cargo> demandsList) {
        System.out.println("=== Supply Cargoes ===");
        int counter= 0;
        for (Cargo cargo : suppliesList) {
            System.out.printf("%d. [Supply]| Type: %s | Quantity: %d%n",
                    counter,
                    cargo.getType().getName(),
                    cargo.getQuantity());
            counter++;
        }

        System.out.println("\n=== Demand Cargoes ===");
        for (Cargo cargo : demandsList) {
                System.out.printf("Type: %s | Quantity: %d%n",
                        cargo.getType().getName(),
                        cargo.getQuantity());
            }
        }


    public void setSelectedLocomotive(Locomotive selectedLocomotive) {
        this.selectedLocomotive = selectedLocomotive;
    }

    public void setSelectedScenario(Scenario selectedScenario) {
        this.selectedScenario = selectedScenario;
    }

    public void createRoute(){
       selectedRoute = new Route(firstStation,lastStation,selectedLocomotive,selectedCargo);
    }
    public void createRoute2(){
        selectedRoute = new Route(firstStation,lastStation,stops, selectedLocomotive,selectedCargo);
    }
}
