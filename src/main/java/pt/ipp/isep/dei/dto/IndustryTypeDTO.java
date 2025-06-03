package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.ResourceType;

import java.util.List;

public class IndustryTypeDTO {
    private String name;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;

    public String getName() {
        return name;
    }

    public List<ResourceType> getImportation(){
        return importation;
    }

    public List<ResourceType>  getExportation(){
        return exportation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExportation(List<ResourceType>  exportation) {
        this.exportation = exportation;
    }

    public void setImportation(List<ResourceType>  importation) {
        this.importation = importation;
    }
}
