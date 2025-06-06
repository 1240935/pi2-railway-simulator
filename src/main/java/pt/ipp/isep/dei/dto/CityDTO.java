package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.CityBlock;

import java.util.List;

public class CityDTO {
    private String name;
    private int xCoordinate;
    private int yCoordinate;
    private int mapSize;
    private List<CityBlock> cityBlocks;

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public String getName() {
        return name;
    }

    public int getMapSize() {
        return mapSize;
    }

    public List<CityBlock> getCityBlocks() {
        return cityBlocks;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public void setCityBlocks(List<CityBlock> cityBlocks) {
        this.cityBlocks = cityBlocks;
    }
}