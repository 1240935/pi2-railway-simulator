package pt.ipp.isep.dei.domain;

public class Railway {

    private Position startPosition;
    private Position endPosition;
    private boolean eletrification;
    private RailwayType type;

    Railway(Position startPosition , Position endPosition, boolean eletrification,
            RailwayType type){
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.eletrification = eletrification;
        this.type = type;
    }
}
