package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CityBlockRepository {
    private List<CityBlock> cityBlocks = new ArrayList<>();

    /**
     * Creates a list of CityBlocks from a list of positions,
     * using the specified number of blocks.
     * @param positions base list of positions
     * @param cityBlocks number of blocks to create
     * @return list of created CityBlocks
     */
    public static List<CityBlock> createBlocks(List<Position> positions, int cityBlocks){
        List<CityBlock> blocks = new ArrayList<>();
        for(int i = 0; i < cityBlocks; i++){
            CityBlock block = new CityBlock(positions.get(i));
            blocks.add(block);
        }
        return blocks;
    }

    /**
     * Generates a list of CityBlocks with random positions around
     * coordinates (x, y), based on a normal distribution.
     * @param x central x coordinate
     * @param y central y coordinate
     * @param number number of blocks to generate
     * @return list of generated CityBlocks
     */
    public static List<CityBlock> generateBlocks(int x, int y, int number) {
        List<CityBlock> blocks = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < number; i++) {
            Position position = new Position(x,y);
            int dx = (int) Math.round(rand.nextGaussian() * 2);
            int dy = (int) Math.round(rand.nextGaussian() * 2);
            Position pos = new Position(position.getxCoordinate() + dx, position.getyCoordinate() + dy);
            CityBlock cityBlock = new CityBlock(pos);
            blocks.add(cityBlock);
        }

        return blocks;
    }

    /**
     * Adds a CityBlock to the list managed by this instance.
     * @param block block to add
     */
    public void addCityBlock(CityBlock block) {
        cityBlocks.add(block);
    }

    /**
     * Returns the current list of CityBlocks stored in this instance.
     * @return list of CityBlocks
     */
    public List<CityBlock> getCityBlocks() {
        return cityBlocks;
    }
}
