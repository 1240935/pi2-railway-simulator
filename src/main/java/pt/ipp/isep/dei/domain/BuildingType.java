package pt.ipp.isep.dei.domain;

public enum BuildingType {
    // Tipos
    TELEGRAPH(1,1),
    TELEPHONE(1,1),
    SMALLCAFE(1,1),
    LARGECAFE(1,1),
    CUSTOMS(1,1),
    POSTOFFICE(1,1),
    SMALLHOTEL(1,1),
    LARGEHOTEL(1,1),
    SILO(1,1),
    LIQUIDSTORAGE(1,1);

    private final int cost;
    private final int upgrade;

    BuildingType(int cost, int upgrade) {
        this.cost = cost;
        this.upgrade = upgrade;
    }
}
