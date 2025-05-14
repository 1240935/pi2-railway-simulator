package pt.ipp.isep.dei.domain;

import java.util.List;

public class Route {

    private Station beginningStation;
    private Station endingStation;
    private List<Station> stops;

    Route(Station beginningStation, Station endingStation, List<Station> stops){
        this.beginningStation = beginningStation;
        this.endingStation = endingStation;
        this.stops = stops;
    }
}
