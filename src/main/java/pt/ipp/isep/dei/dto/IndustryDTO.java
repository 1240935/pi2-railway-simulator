package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.IndustryType;

public class IndustryDTO {
    private IndustryType industryType;
    private int xCoordinate;
    private int yCoordinate;
    private int size;

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getSize() {
        return size;
    }

    public IndustryType getIndustryType() {
        return industryType;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
