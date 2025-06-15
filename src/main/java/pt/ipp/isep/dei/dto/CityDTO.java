package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.CityBlock;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a city.
 * Contains information about the city's name, coordinates,
 * map size, and the list of city blocks within the city.
 */
public class CityDTO {

    /**
     * The name of the city.
     */
    private String name;

    /**
     * The x-coordinate of the city location.
     */
    private int xCoordinate;

    /**
     * The y-coordinate of the city location.
     */
    private int yCoordinate;

    /**
     * The size of the city map.
     */
    private int mapSize;

    /**
     * The list of {@link CityBlock} objects that compose the city.
     */
    private List<CityBlock> cityBlocks;

    /**
     * Gets the x-coordinate of the city.
     *
     * @return the x-coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Sets the x-coordinate of the city.
     *
     * @param xCoordinate the x-coordinate to set
     */
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Gets the y-coordinate of the city.
     *
     * @return the y-coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Sets the y-coordinate of the city.
     *
     * @param yCoordinate the y-coordinate to set
     */
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets the name of the city.
     *
     * @return the city name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the city.
     *
     * @param name the city name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the size of the city map.
     *
     * @return the map size
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Sets the size of the city map.
     *
     * @param mapSize the map size to set
     */
    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    /**
     * Gets the list of city blocks.
     *
     * @return the list of {@link CityBlock} objects
     */
    public List<CityBlock> getCityBlocks() {
        return cityBlocks;
    }

    /**
     * Sets the list of city blocks.
     *
     * @param cityBlocks the list of {@link CityBlock} objects to set
     */
    public void setCityBlocks(List<CityBlock> cityBlocks) {
        this.cityBlocks = cityBlocks;
    }
}
