package pt.ipp.isep.dei.domain;

public class Industry {
    private IndustryType industryType;
    private Position position;

    public Industry(IndustryType industryType, Position position){
        this.industryType = industryType;
        this.position = position;
    }

    public IndustryType getType() {
        return industryType;
    }

    public void setType(IndustryType industryType) {
        this.industryType = industryType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
