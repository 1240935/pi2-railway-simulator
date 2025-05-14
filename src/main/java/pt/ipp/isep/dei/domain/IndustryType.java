package pt.ipp.isep.dei.domain;

public enum IndustryType {
    // Tipos
    PRIMARY_SECTOR(false,true),
    TRANSFORMING(true, true),
    MIXED;

    private Boolean importation;
    private Boolean exportation;

    IndustryType(boolean importation, boolean exportation) {
        this.importation = importation;
        this.exportation = exportation;
    }


    IndustryType() {
        this.importation = null;
        this.exportation = null;
    }

    public Boolean getImportation() {
        return importation;
    }

    public Boolean getExportation() {
        return exportation;
    }

    public boolean getImportation(Scenario scenario) {
        if (this != MIXED) {
            return this.importation;
        }
        return scenario.getImportation();
    }

    public boolean getExportation(Scenario scenario) {
        if (this != MIXED) {
            return this.exportation;
        }
        return scenario.getExportation();
    }
}

