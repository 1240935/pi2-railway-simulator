package pt.ipp.isep.dei.domain;

public enum BuildingType {
    // Tipos
    TELEGRAPH(100,1),
    TELEPHONE(150,2),
    SMALLCAFE(200,1),
    LARGECAFE(300,2),
    CUSTOMS(500,3),
    POSTOFFICE(250,1),
    SMALLHOTEL(400,2),
    LARGEHOTEL(600,3),
    SILO(350,2),
    LIQUIDSTORAGE(450,2);

    private final int cost;
    private final int upgrade;

    BuildingType(int cost, int upgrade) {
        this.cost = cost;
        this.upgrade = upgrade;
    }
}
