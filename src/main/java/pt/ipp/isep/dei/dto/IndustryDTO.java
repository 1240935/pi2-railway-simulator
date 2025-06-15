package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.IndustryType;

/**
 * Data Transfer Object (DTO) representing an industry.
 * Contains the industry type, coordinates, and size information.
 */
public class IndustryDTO {

    /**
     * The type of the industry.
     */
    private IndustryType industryType;

    /**
     * The x-coordinate of the industry location.
     */
    private int xCoordinate;

    /**
     * The y-coordinate of the industry location.
     */
    private int yCoordinate;

    /**
     * The size of the industry.
     */
    private int size;

    /**
     * Gets the y-coordinate of the industry.
     *
     * @return the y-coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Gets the x-coordinate of the industry.
     *
     * @return the x-coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Gets the size of the industry.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the industry type.
     *
     * @return the industry type
     */
    public IndustryType getIndustryType() {
        return industryType;
    }

    /**
     * Sets the y-coordinate of the industry.
     *
     * @param yCoordinate the y-coordinate to set
     */
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Sets the x-coordinate of the industry.
     *
     * @param xCoordinate the x-coordinate to set
     */
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Sets the industry type.
     *
     * @param industryType the industry type to set
     */
    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }

    /**
     * Sets the size of the industry.
     *
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
}
