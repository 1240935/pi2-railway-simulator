package pt.ipp.isep.dei.domain;

public class Scenario {
    private Map map;
    private int year;
    private LocomotiveType locomotiveType;
    private IndustryType industryType;
    private ResourceType resourceType;
    private IndustryChanger industryChanger;
    private boolean importation;
    private boolean exportation;

   Scenario(Map map, int year, LocomotiveType locomotiveType, IndustryType industryType, ResourceType
           resourceType, IndustryChanger industryChanger, boolean importation, boolean exportation){
       this.map = map;
       this.locomotiveType = locomotiveType;
       this.industryType = industryType;
       this.resourceType = resourceType;
       this.industryChanger = industryChanger;
       this.importation = importation;
       this.exportation = exportation;
   }

    public boolean getImportation() {
        return importation;
    }

    public boolean getExportation() {
       return exportation;
    }
}
