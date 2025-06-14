package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.LocomotiveType;

public class LocomotiveModelDTO {
    private String power;
    private String acceleration;
    private int topSpeed;
    private int operationStartYear;
    private int fuelCost;
    private int maintenancePYear;
    private int cost;
    private LocomotiveType locomotiveType;
    private String name;

    public LocomotiveModelDTO(){}

    public LocomotiveModelDTO( String power,String acceleration, int topSpeed,
                               int operationStartYear, int fuelCost, int maintenancePYear,
                               int cost, LocomotiveType locomotiveType, String name){
      this.acceleration =  acceleration;
      this.cost = cost;
      this.locomotiveType = locomotiveType;
      this.fuelCost = fuelCost;
      this.power = power;
      this.topSpeed = topSpeed;
      this.operationStartYear = operationStartYear;
      this.maintenancePYear = maintenancePYear;
      this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getFuelCost() {
        return fuelCost;
    }

    public int getMaintenancePYear() {
        return maintenancePYear;
    }

    public int getOperationStartYear() {
        return operationStartYear;
    }

    public LocomotiveType getLocomotiveType() {
        return locomotiveType;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public String getAcceleration() {
        return acceleration;
    }

    public String getPower() {
        return power;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }

    public void setFuelCost(int fuelCost) {
        this.fuelCost = fuelCost;
    }

    public void setLocomotiveType(LocomotiveType locomotiveType) {
        this.locomotiveType = locomotiveType;
    }

    public void setMaintenancePYear(int maintenancePYear) {
        this.maintenancePYear = maintenancePYear;
    }

    public void setOperationStartYear(int operationStartYear) {
        this.operationStartYear = operationStartYear;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public void setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
    }
}
