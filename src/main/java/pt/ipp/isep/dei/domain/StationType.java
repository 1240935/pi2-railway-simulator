package pt.ipp.isep.dei.domain;

public enum StationType {
    // Tipos
    DEPOT(100, 10),
    STATION(200, 20),
    TERMINAL(500, 30);

    private final int cost;
    private final int radius;

    StationType(int cost, int radius) {
        this.cost = cost;
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public int getCost() {
        return cost;
    }
}

