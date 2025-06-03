package pt.ipp.isep.dei.dto;

public class IndustryDTO {
    private String industryTypeName;
    private int xCoordinate;
    private int yCoordinate;
    private int mapIndex;

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getMapIndex() {
        return mapIndex;
    }

    public String getIndustryTypeName() {
        return industryTypeName;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setIndustryTypeName(String industryTypeName) {
        this.industryTypeName = industryTypeName;
    }

    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }
}
