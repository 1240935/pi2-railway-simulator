package pt.ipp.isep.dei.domain;

import java.util.List;

public class City {

    private Position position;
    private String name;
    private List<CityBlock> blocks;

    City(Position position, String name, List<CityBlock> blocks){
        this.position = position;
        this.name = name;
        this.blocks = blocks;
    }
}
