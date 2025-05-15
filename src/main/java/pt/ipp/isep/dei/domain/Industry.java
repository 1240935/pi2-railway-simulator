package pt.ipp.isep.dei.domain;

public class Industry {
    private IndustryType industryType;
    private Position position;

    public Industry(IndustryType industryType, Position position){
        this.industryType = industryType;
        this.position = position;
    }

    public IndustryType getIndustryType() {
        return industryType;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }

    public Position getIndustryPosition() {
        return position;
    }

    public void setIndustryPosition(Position position) {
        this.position = position;
    }
}
