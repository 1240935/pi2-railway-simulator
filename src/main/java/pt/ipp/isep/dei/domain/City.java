package pt.ipp.isep.dei.domain;

import java.util.List;

public class City {

    private Position position;
    private String name;
    private List<CityBlock> blocks;

    public City(String name, Position position, List<CityBlock> blocks){
        this.name = name;
        this.position = position;
        this.blocks = blocks;
    }
    public String getName() {
        return name;
    }
    public Position getPosition() {
        return position;
    }
    public List<CityBlock> getBlocks() {
        return blocks;
    }
}
