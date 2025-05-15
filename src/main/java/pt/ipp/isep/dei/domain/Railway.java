package pt.ipp.isep.dei.domain;

public class Railway {

    private final Station start;
    private final Station end;
    private final RailwayType type;

    Railway(Station start , Station end, RailwayType type){
        this.start = start;
        this.end = end;
        this.type = type;
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
}
