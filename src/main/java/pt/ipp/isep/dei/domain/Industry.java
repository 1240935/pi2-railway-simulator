package pt.ipp.isep.dei.domain;

import java.util.Objects;

public class Industry {
    private IndustryType industryType;
    private Position position;
    private ResourceType resourceType;

    public Industry(IndustryType industryType, Position position, ResourceType resourceType){
        this.industryType = industryType;
        this.position = position;
        this.resourceType = resourceType;
    }

    public IndustryType getIndustryType() {
        return industryType;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }
}
