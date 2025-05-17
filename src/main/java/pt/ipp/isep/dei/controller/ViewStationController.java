package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;
import java.util.List;

public class ViewStationController {
    private Scenario selectedScenario;

    public ViewStationController() {}

    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    public void setScenario(Scenario scenario) {
        this.selectedScenario = scenario;
    }

    public List<Station> getAvailableStations() {
        return Repositories.getInstance().getStationRepository().getStations();
    }

    public BuildingType getBuildingType(Station station) {
        return station.getBuildingType();
    }

    public List<Cargo> getCargoes(Station station) {
        return station.getCargoes();
    }
}
