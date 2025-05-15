package pt.ipp.isep.dei.domain;

public enum RailwayType {
    SINGLE(6, false),
    DOUBLE(9, false),
    ELECSINGLE(11,true),
    ELECDOUBLE(14, true);

    private final int cost ;
    private final boolean electrification;

    RailwayType(int cost, boolean electrification){
        this.cost = cost;
        this.electrification = electrification;
    }

    public int getCost() {
        return cost;
    }

    public boolean isElectrification() {
        return electrification;
    }
}
