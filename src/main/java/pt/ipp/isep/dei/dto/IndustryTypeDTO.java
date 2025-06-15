package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.ResourceType;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing an industry type.
 * Contains the industry's name, as well as lists of resources it imports and exports.
 */
public class IndustryTypeDTO {

    /**
     * The name of the industry type.
     */
    private String name;

    /**
     * List of resource types that the industry imports.
     */
    private List<ResourceType> importation;

    /**
     * List of resource types that the industry exports.
     */
    private List<ResourceType> exportation;

    /**
     * Default constructor.
     */
    public IndustryTypeDTO() {
    }

    /**
     * Constructs an {@code IndustryTypeDTO} with the specified name,
     * importation list, and exportation list.
     *
     * @param name        the name of the industry type
     * @param importation list of resource types imported by the industry
     * @param exportation list of resource types exported by the industry
     */
    public IndustryTypeDTO(String name, List<ResourceType> importation, List<ResourceType> exportation) {
        this.name = name;
        this.importation = importation;
        this.exportation = exportation;
    }

    /**
     * Returns the name of the industry type.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the industry type.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the list of resource types imported by the industry.
     *
     * @return the importation list
     */
    public List<ResourceType> getImportation() {
        return importation;
    }

    /**
     * Sets the list of resource types imported by the industry.
     *
     * @param importation the importation list to set
     */
    public void setImportation(List<ResourceType> importation) {
        this.importation = importation;
    }

    /**
     * Returns the list of resource types exported by the industry.
     *
     * @return the exportation list
     */
    public List<ResourceType> getExportation() {
        return exportation;
    }

    /**
     * Sets the list of resource types exported by the industry.
     *
     * @param exportation the exportation list to set
     */
    public void setExportation(List<ResourceType> exportation) {
        this.exportation = exportation;
    }

    /**
     * Returns a string representation of the industry type,
     * including its name, imported resources, and exported resources.
     *
     * @return string representation of the industry type
     */
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
