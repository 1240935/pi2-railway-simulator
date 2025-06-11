package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.ResourceType;

import java.util.List;

public class IndustryTypeDTO {
    private String name;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;

    public IndustryTypeDTO() {
    }

    public IndustryTypeDTO(String name, List<ResourceType> importation, List<ResourceType> exportation) {
        this.name = name;
        this.importation = importation;
        this.exportation = exportation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResourceType> getImportation() {
        return importation;
    }

    public void setImportation(List<ResourceType> importation) {
        this.importation = importation;
    }

    public List<ResourceType> getExportation() {
        return exportation;
    }

    public void setExportation(List<ResourceType> exportation) {
        this.exportation = exportation;
    }

    @Override
    public String toString() {
        String importStr = (importation == null || importation.isEmpty())
                ? "None"
                : importation.toString().replaceAll("[\\[\\]]", "");

        String exportStr = (exportation == null || exportation.isEmpty())
                ? "None"
                : exportation.toString().replaceAll("[\\[\\]]", "");

        return String.format("%s\n  - Imports: %s\n  - Exports: %s", name, importStr, exportStr);
    }

}
