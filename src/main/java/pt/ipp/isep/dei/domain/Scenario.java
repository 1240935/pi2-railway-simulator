package pt.ipp.isep.dei.domain;

public class Scenario {
    private Map map;
    private int year;
    private LocomotiveType locomotiveType;
    private IndustryType industryType;
    private ResourceType resourceType;
    private IndustryChanger industryChanger;
    private ResourceType importation;
    private ResourceType exportation;

   Scenario(Map map, int year, LocomotiveType locomotiveType, IndustryType industryType, ResourceType
           resourceType, IndustryChanger industryChanger, ResourceType importation, ResourceType exportation){
       this.map = map;
       this.locomotiveType = locomotiveType;
       this.industryType = industryType;
       this.resourceType = resourceType;
       this.industryChanger = industryChanger;
       this.importation = importation;
       this.exportation = exportation;
   }

    public ResourceType getImportation() {
        return importation;
    }

    public ResourceType getExportation() {
       return exportation;
    }
}
