package pt.ipp.isep.dei.domain;

import java.io.Serializable;

public class Railway implements Serializable {

    private final Station start;
    private final Station end;
    private final RailwayType type;
    private final double distance;  // add distance attribute

    public Railway(Station start, Station end, RailwayType type, double distance) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.distance = distance;
    }

    public Station getStart() {
        return start;
    }

    public Station getEnd() {
        return end;
    }

    public RailwayType getType() {
        return type;
    }

    public double getDistance() {
        return distance;
    }

    public static Railway getRailwayBetween(Station start, Station end) {
        Position position1 = start.getPosition();
        Position position2 = end.getPosition();
        double distance = position1.euclideanDistance(position2);
        return new Railway(start, end, RailwayType.ELECDOUBLE, distance);
    }

    public boolean isDoubleTrack(){
        return type == RailwayType.ELECDOUBLE || type == RailwayType.DOUBLE;
    }


}
