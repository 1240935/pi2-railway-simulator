package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Represents various types of industries in the system.
 * <p>
 * Each industry type may have associated import resource types (inputs)
 * and export resource types (outputs).
 * <p>
 * Some industries import multiple resources and export one,
 * others import one and export one,
 * and some have no imports but export resources.
 * The PORT industry type is special and defers to scenario-specific imports/exports.
 */
public enum IndustryType implements Serializable {

    ALUMINUM_MILL(ResourceType.BAUXITE, ResourceType.ALUMINUM),
    AUTO_PLANT(List.of(ResourceType.STEEL, ResourceType.TIRES), ResourceType.AUTOS),
    BAKERY(List.of(ResourceType.GRAIN, ResourceType.SUGAR), ResourceType.FOOD),
    BAUXIT_MINE(ResourceType.BAUXITE),
    CANNERY(List.of(ResourceType.ALUMINUM, ResourceType.COFFEE, ResourceType.PRODUCE, ResourceType.STEEL), ResourceType.FOOD),
    CATTLE_YARD(ResourceType.CATTLE),
    CHEMICAL_PLANT(ResourceType.CHEMICALS),
    CEMENT_PLANT(ResourceType.GRAVEL, ResourceType.CEMENT),
    COAL_MINE(ResourceType.COAL),
    COFFEE_FARM(ResourceType.FERTILIZER, ResourceType.COFFEE),
    COTTON_FARM(ResourceType.FERTILIZER, ResourceType.COTTON),
    DAIRY_FARM(ResourceType.GRAIN, ResourceType.DAIRY),
    DAIRY_PROCESSOR(ResourceType.DAIRY, ResourceType.FOOD),
    ELECTRIC_PLANT(List.of(ResourceType.COAL, ResourceType.DIESEL), ResourceType.WASTE),
    FERTILIZER_PLANT(ResourceType.CHEMICALS, ResourceType.FERTILIZER),
    GRAIN_SILO(ResourceType.FERTILIZER, ResourceType.GRAIN),
    GRAVEL_PIT(ResourceType.GRAVEL),
    IRON_MINE(ResourceType.IRON),
    LANDFILL(ResourceType.WASTE),
    LOGGING_CAMP(List.of(ResourceType.LOGS,ResourceType.PULPWOOD)),
    LUMBER_MILL(ResourceType.LOGS, ResourceType.LUMBER),
    MEAT_PACKING_PLANT(ResourceType.CATTLE, ResourceType.FOOD),
    NUCLEAR_PLANT(ResourceType.URANIUM, ResourceType.WASTE),
    OIL_REFINERY(ResourceType.OIL, ResourceType.DIESEL),
    OIL_WELL(ResourceType.OIL),
    PAPER_MILL(ResourceType.PULPWOOD, ResourceType.PAPER),
    PRODUCE_ORCHARD(ResourceType.FERTILIZER, ResourceType.PRODUCE),
    RUBBER_FARM(ResourceType.FERTILIZER, ResourceType.RUBBER),
    STEEL_MILL(List.of(ResourceType.COAL, ResourceType. IRON),ResourceType.STEEL) ,
    TEXTILE_MILL(List.of(ResourceType.COTTON, ResourceType.WOOL ), ResourceType.GOODS),
    TIRE_FACTORY(ResourceType.RUBBER, ResourceType.TIRES),
    TOOL_AND_DIE_FACTORY(List.of(ResourceType.IRON, ResourceType.STEEL, ResourceType.ALUMINUM), ResourceType.GOODS),
    URANIUM_MINE(ResourceType.URANIUM),
    GEOCORE_PLANT(ResourceType.CHEMICALS, ResourceType.WASTE),
    DISTILLERY(List.of(ResourceType.GRAIN, ResourceType.PRODUCE, ResourceType.SUGAR), ResourceType.ALCOHOL),
    MUNITIONS_FACTORY(List.of(ResourceType.CHEMICALS, ResourceType.STEEL, ResourceType.ALUMINUM), ResourceType.AMMUNITION),
    WEAPONS_FACTORY(List.of(ResourceType.RUBBER, ResourceType.STEEL, ResourceType.ALUMINUM), ResourceType.WEAPONS),
    PORT();

    /**
     * List of resource types this industry imports (inputs).
     * Can be {@code null} if the industry has no imports.
     */
    private List<ResourceType> importation;

    /**
     * List of resource types this industry exports (outputs).
     */
    private List<ResourceType> exportation;

    /**
     * Constructor for industries with multiple imports and one export.
     *
     * @param importation List of resource types imported.
     * @param exportation Single resource type exported.
     */
    IndustryType(List<ResourceType> importation, ResourceType exportation) {
        this.importation = importation;
        this.exportation = List.of(exportation);
    }

    /**
     * Constructor for industries with a single import and single export.
     *
     * @param importation Single resource type imported.
     * @param exportation Single resource type exported.
     */
    IndustryType(ResourceType importation, ResourceType exportation) {
        this(List.of(importation), exportation);
    }

    /**
     * Constructor for industries with no imports and a single export.
     *
     * @param exportation Single resource type exported.
     */
    IndustryType(ResourceType exportation) {
        this.importation = null;
        this.exportation = List.of(exportation);
    }

    /**
     * Constructor for industries with no imports and multiple exports.
     *
     * @param exportation List of resource types exported.
     */
    IndustryType(List<ResourceType> exportation) {
        this.importation = null;
        this.exportation = exportation;
    }

    /**
     * Default constructor for industries with no imports or exports defined (e.g., PORT).
     */
    IndustryType() {
        this.importation = List.of();
        this.exportation = List.of();
    }

    /**
     * Returns the list of resource types this industry imports.
     *
     * @return List of import resource types, or {@code null} if none.
     */
    public List<ResourceType> getImportation() {
        return importation;
    }

    /**
     * Returns the list of resource types this industry exports.
     *
     * @return List of export resource types.
     */
    public List<ResourceType> getExportation() {
        return exportation;
    }

    /**
     * Returns the list of resource types this industry imports, considering the scenario context.
     * For the PORT industry, the scenario import list is used.
     *
     * @param scenario The scenario context.
     * @return List of resource types imported.
     */
    public List<ResourceType> getImportation(Scenario scenario) {
        if (this != PORT) {
            return this.importation;
        }
        return scenario.getImportation();
    }

    /**
     * Returns the list of resource types this industry exports, considering the scenario context.
     * For the PORT industry, the scenario export list is used.
     *
     * @param scenario The scenario context.
     * @return List of resource types exported.
     */
    public List<ResourceType> getExportation(Scenario scenario) {
        if (this != PORT) {
            return this.exportation;
        }
        return scenario.getExportation();
    }

    /**
     * Sets the list of resource types this industry imports.
     *
     * @param importation List of import resource types.
     */
    public void setImportation(List<ResourceType> importation) {
        this.importation = importation;
    }

    /**
     * Sets the list of resource types this industry exports.
     *
     * @param exportation List of export resource types.
     */
    public void setExportation(List<ResourceType> exportation) {
        this.exportation = exportation;
    }

    /**
     * Returns a string representation of the industry type,
     * including its imports and exports if available.
     *
     * @return String describing the industry type and its resources.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name());

        if (importation != null && !importation.isEmpty()) {
            sb.append(" | Imports: ");
            sb.append(importation);
        }

        if (exportation != null && !exportation.isEmpty()) {
            sb.append(" | Exports: ");
            sb.append(exportation);
        }

        return sb.toString();
    }

}
