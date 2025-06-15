package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.LocomotiveType;

/**
 * Data Transfer Object (DTO) representing a locomotive model.
 * Contains various properties related to the locomotive's specifications and costs.
 */
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

    /**
     * Default constructor.
     */
    public LocomotiveModelDTO() {}

    /**
     * Constructs a {@code LocomotiveModelDTO} with all fields initialized.
     *
     * @param power the power specification of the locomotive
     * @param acceleration the acceleration characteristic
     * @param topSpeed the top speed in appropriate units
     * @param operationStartYear the year when the model started operation
     * @param fuelCost the fuel cost associated with the locomotive
     * @param maintenancePYear the yearly maintenance cost
     * @param cost the purchase cost of the locomotive
     * @param locomotiveType the type of the locomotive
     * @param name the name of the locomotive model
     */
    public LocomotiveModelDTO(String power, String acceleration, int topSpeed,
                              int operationStartYear, int fuelCost, int maintenancePYear,
                              int cost, LocomotiveType locomotiveType, String name) {
        this.acceleration = acceleration;
        this.cost = cost;
        this.locomotiveType = locomotiveType;
        this.fuelCost = fuelCost;
        this.power = power;
        this.topSpeed = topSpeed;
        this.operationStartYear = operationStartYear;
        this.maintenancePYear = maintenancePYear;
        this.name = name;
    }

    /** Returns the name of the locomotive model. */
    public String getName() {
        return name;
    }

    /** Returns the purchase cost of the locomotive model. */
    public int getCost() {
        return cost;
    }

    /** Returns the fuel cost of the locomotive model. */
    public int getFuelCost() {
        return fuelCost;
    }

    /** Returns the yearly maintenance cost of the locomotive model. */
    public int getMaintenancePYear() {
        return maintenancePYear;
    }

    /** Returns the year the locomotive model started operation. */
    public int getOperationStartYear() {
        return operationStartYear;
    }

    /** Returns the type of the locomotive. */
    public LocomotiveType getLocomotiveType() {
        return locomotiveType;
    }

    /** Returns the top speed of the locomotive model. */
    public int getTopSpeed() {
        return topSpeed;
    }

    /** Returns the acceleration of the locomotive model. */
    public String getAcceleration() {
        return acceleration;
    }

    /** Returns the power specification of the locomotive model. */
    public String getPower() {
        return power;
    }

    /** Sets the name of the locomotive model. */
    public void setName(String name) {
        this.name = name;
    }

    /** Sets the purchase cost of the locomotive model. */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /** Sets the acceleration characteristic of the locomotive model. */
    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }

    /** Sets the fuel cost of the locomotive model. */
    public void setFuelCost(int fuelCost) {
        this.fuelCost = fuelCost;
    }

    /** Sets the type of the locomotive. */
    public void setLocomotiveType(LocomotiveType locomotiveType) {
        this.locomotiveType = locomotiveType;
    }

    /** Sets the yearly maintenance cost of the locomotive model. */
    public void setMaintenancePYear(int maintenancePYear) {
        this.maintenancePYear = maintenancePYear;
    }

    /** Sets the year the locomotive model started operation. */
    public void setOperationStartYear(int operationStartYear) {
        this.operationStartYear = operationStartYear;
    }

    /** Sets the power specification of the locomotive model. */
    public void setPower(String power) {
        this.power = power;
    }

    /** Sets the top speed of the locomotive model. */
    public void setTopSpeed(int topSpeed) {
        this.topSpeed = topSpeed;
    }
}
