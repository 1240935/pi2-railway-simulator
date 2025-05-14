package pt.ipp.isep.dei.domain;

public class Railway {

    private Station start;
    private Station end;
    private boolean eletrification;
    private RailwayType type;

    Railway(Station start , Station end, boolean eletrification,
            RailwayType type){
        this.start = start;
        this.end = end;
        this.eletrification = eletrification;
        this.type = type;
    }
}
