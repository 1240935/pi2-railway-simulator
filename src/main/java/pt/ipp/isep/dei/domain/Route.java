package pt.ipp.isep.dei.domain;

import java.util.List;

public class Route {

    private Station beginningStation;
    private Station endingStation;
    private List<Station> stops;
    private Locomotive locomotive;
    private Cargo cargo;

    Route(Station beginningStation, Station endingStation, List<Station> stops, Locomotive locomotive, Cargo cargo){
        this.beginningStation = beginningStation;
        this.endingStation = endingStation;
        this.stops = stops;
        this.locomotive = locomotive;
        this.cargo = cargo;
    }
}
