package pt.ipp.isep.dei.domain;

public enum LocomotiveModel {
    //Tipos
    POPOTAODONODAO("Poor","Poor",1,1,1,1,
            1,LocomotiveType.STEAM);

    private final String power;
    private final String acceleration;
    private final int topSpeed;
    private final int operationStartYear;
    private final int fuelCost;
    private final int maintenancePYear;
    private final int cost;
    private final LocomotiveType locomotiveType;

    LocomotiveModel(String power, String acceleration, int topSpeed, int operationStartYear,int fuelCost,
                    int maintenancePYear, int cost, LocomotiveType locomotiveType){
        this.power = power;
        this.acceleration = acceleration;
        this.topSpeed = topSpeed;
        this.operationStartYear = operationStartYear;
        this.fuelCost = fuelCost;
        this.maintenancePYear = maintenancePYear;
        this.cost = cost;
        this.locomotiveType = locomotiveType;
    }
}
