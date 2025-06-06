package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.LocomotiveType;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.ResourceType;

import java.util.List;

public class ScenarioDTO {
    private Map map;
    private int year;
    private List<LocomotiveType> locomotiveTypes;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;
    private int changer;


    public Map getMap() { return map; }
    public int getYear() { return year; }
    public List<LocomotiveType> getLocomotiveTypes() { return locomotiveTypes; }
    public List<ResourceType> getImportation() { return importation; }
    public List<ResourceType> getExportation() { return exportation; }
    public int getChanger() { return changer; }

    public void setImportation(List<ResourceType> importation) {
        this.importation = importation;
    }

    public void setExportation(List<ResourceType> exportation) {
        this.exportation = exportation;
    }

    public void setLocomotiveTypes(List<LocomotiveType> locomotiveTypes) {
        this.locomotiveTypes = locomotiveTypes;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setChanger(int changer) {
        this.changer = changer;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
