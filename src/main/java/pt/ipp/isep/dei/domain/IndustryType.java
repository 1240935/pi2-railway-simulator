package pt.ipp.isep.dei.domain;


public enum IndustryType {;

    /*
    ALUMINUM_MILL(ResourceType.ALUMINUM, ResourceType.BAUXITE),
    AUTO_PLANT(),
    BAKERY(),
    BAUXITE_MINE(),
    CANNERY(),
    CATTLE_YARD(),
    CHEMICAL_PLANT(),
    CEMENT_PLANT(),
    COAL_MINE(),
    COFFEE_FARM(),
    COTTON_FARM(),
    DAIRY_FARM(),
    DAIRY_PROCESSOR(),
    ELECTRIC_PLANT(),
    FERTILIZER_PLANT(),
    GRAIN_SILO(),
    GRAVEL_PIT(),
    HOUSING(),
    IRON_MINE(),
    LANDFILL(),
    LOGGING_CAMP(),
    LUMBER_MILL(),
    MEAT_PACKING_PLANT(),
    NUCLEAR_PLANT(),
    OIL_REFINERY(),
    OIL_WELL(),
    PAPER_MILL(),
    PRODUCE_ORCHARD(),
    RUBBER_FARM(),
    STEEL_MILL(),
    SHEEP_FARM(),
    TEXTILE_MILL(),
    TIRE_FACTORY(),
    TOOL_AND_DIE_FACTORY(),
    URANIUM_MINE(),
    GEOCORE_PLANT(),
    TOWNHOUSE(),
    COMMERCIAL(),
    RETAIL(),
    DISTILLERY(),
    BARRACKS(),
    MILITARY_DEPOT(),
    MUNITIONS_FACTORY(),
    WEAPONS_FACTORY(),
    AIRPORT_TERMINAL(),
    AIRPORT_TARMAC(),
    PORT();

     */

    private ResourceType importation;
    private ResourceType exportation;

    IndustryType(ResourceType importation,ResourceType exportation) {
        this.importation = importation;
        this.exportation = exportation;
    }

    public ResourceType getImportation() {
        return importation;
    }

    public ResourceType getExportation() {
        return exportation;
    }

   /* public ResourceType getImportation(Scenario scenario) {
        if (this != PORT) {
            return this.importation;
        }
        return scenario.getImportation();
    }

    public ResourceType getExportation(Scenario scenario) {
        if (this != PORT) {
            return this.exportation;
        }
        return scenario.getExportation();
    }
    *
    */
}

