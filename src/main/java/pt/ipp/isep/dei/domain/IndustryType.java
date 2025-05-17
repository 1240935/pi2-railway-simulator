package pt.ipp.isep.dei.domain;


import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public enum IndustryType {

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

    private List<ResourceType> importation;
    private List<ResourceType> exportation;

    // Multiple imports, one export
    IndustryType(List<ResourceType> importation, ResourceType exportation) {
        this.importation = importation;
        this.exportation = List.of(exportation);
    }

    // Single import, single export
    IndustryType(ResourceType importation, ResourceType exportation) {
        this(List.of(importation), exportation);
    }

    // No import, single export
    IndustryType(ResourceType exportation) {
        this.importation = null;
        this.exportation = List.of(exportation);
    }

    //No import, multiple exports
    IndustryType(List<ResourceType> exportation) {
        this.importation = null;
        this.exportation = exportation;
    }

    IndustryType(){
    }


    public List<ResourceType> getImportation() {
        return importation;
    }

    public List<ResourceType> getExportation() {
        return exportation;
    }

   public List<ResourceType> getImportation(Scenario scenario) {
        if (this != PORT) {
            return this.importation;
        }
        return scenario.getImportation();
    }

    public List<ResourceType> getExportation(Scenario scenario) {
        if (this != PORT) {
            return this.exportation;
        }
        return scenario.getExportation();
    }
}

