package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.*;
import java.util.List;

public class StationDTO {
    private StationType stationType;
    private Position position;
    private String name;
    private StationCenter center;
    private List<Cargo> supplies;
    private List<Cargo> demands;
    private Scenario scenario;
    private Map map;

    public StationDTO(StationType stationType, Position position, String name, StationCenter center,
    List<Cargo> supplies, List<Cargo> demands, Scenario scenario, Map map ){
        this.stationType = stationType;
        this.position = position;
        this.name = name;
        this.center = center;
        this.supplies = supplies;
        this.demands = demands;
        this.scenario = scenario;
        this.map = map;
    }

    public StationDTO(){}

    public String getName() {
        return name;
    }

    public List<Cargo> getDemands() {
        return demands;
    }

    public List<Cargo> getSupplies() {
        return supplies;
    }

    public Position getPosition() {
        return position;
    }

    public StationCenter getCenter() {
        return center;
    }

    public StationType getStationType() {
        return stationType;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public Map getMap() {
        return map;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCenter(StationCenter center) {
        this.center = center;
    }

    public void setDemands(List<Cargo> demands) {
        this.demands = demands;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStationType(StationType stationType) {
        this.stationType = stationType;
    }

    public void setSupplies(List<Cargo> supplies) {
        this.supplies = supplies;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
