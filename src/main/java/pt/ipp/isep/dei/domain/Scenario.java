package pt.ipp.isep.dei.domain;

import java.util.List;

public class Scenario {
    private Map map;
    private int year;
    private LocomotiveType locomotiveType;
    private IndustryType industryType;
    private ResourceType resourceType;
    private IndustryChanger industryChanger;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;

   Scenario(Map map, int year, LocomotiveType locomotiveType, IndustryType industryType, ResourceType
           resourceType, IndustryChanger industryChanger, List<ResourceType> importation, List<ResourceType> exportation){
       this.map = map;
       this.year = year;
       this.locomotiveType = locomotiveType;
       this.industryType = industryType;
       this.resourceType = resourceType;
       this.industryChanger = industryChanger;
       this.importation = importation;
       this.exportation = exportation;
   }

    public List<ResourceType> getImportation() {
        return importation;
    }

    public List<ResourceType> getExportation() {
       return exportation;
    }
}
