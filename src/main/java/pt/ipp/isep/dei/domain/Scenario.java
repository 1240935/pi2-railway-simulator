package pt.ipp.isep.dei.domain;

import java.util.List;

public class Scenario {
    private Map map;
    private int year;
    private List<LocomotiveType> locomotiveType;
    private List<IndustryType> industryType;
    private List<IndustryChanger> industryChanger;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;

   public Scenario(Map map, int year, List<LocomotiveType> locomotiveType, List<IndustryType> industryType,
            List<IndustryChanger> industryChanger, List<ResourceType> importation, List<ResourceType> exportation){
       this.map = map;
       this.year = year;
       this.locomotiveType = locomotiveType;
       this.industryType = industryType;
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
