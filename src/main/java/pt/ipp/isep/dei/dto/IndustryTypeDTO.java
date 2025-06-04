package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.IndustryType;
import pt.ipp.isep.dei.domain.ResourceType;

import java.util.List;

public class IndustryTypeDTO {
    private List<ResourceType> importation;
    private List<ResourceType> exportation;


    public List<ResourceType> getImportation(){
        return importation;
    }

    public List<ResourceType>  getExportation(){
        return exportation;
    }

    public void setExportation(List<ResourceType>  exportation) {
        this.exportation = exportation;
    }

    public void setImportation(List<ResourceType>  importation) {
        this.importation = importation;
    }
}
