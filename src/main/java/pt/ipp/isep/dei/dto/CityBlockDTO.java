package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Position;

import java.util.List;

public class CityBlockDTO {
    private int xCoordinate;
    private int yCoordinate;
    private int blocks;
    private boolean randomize;
    private List<Position> positions;

    public int getxCoordinate() {
        return xCoordinate;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }

    public boolean isRandomize() {
        return randomize;
    }
}
