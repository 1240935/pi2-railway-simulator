package pt.ipp.isep.dei.domain;

public enum BuildingType {
    // Tipos
    TELEGRAPH(100,1),
    TELEPHONE(150,2),
    SMALL_CAFE(200,1),
    LARGE_CAFE(300,2),
    CUSTOMS(500,3),
    POST_OFFICE(250,1),
    SMALL_HOTEL(400,2),
    LARGE_HOTEL(600,3),
    SILO(350,2),
    LIQUID_STORAGE(450,2);

    private final int cost;
    private final int upgrade;

    BuildingType(int cost, int upgrade) {
        this.cost = cost;
        this.upgrade = upgrade;
    }
}
