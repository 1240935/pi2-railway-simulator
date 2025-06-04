package pt.ipp.isep.dei.repository;
import pt.ipp.isep.dei.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CityBlockRepository {
    private List<CityBlock> cityBlocks = new ArrayList<>();

    public static List<CityBlock> createBlocks(List<Position> positions, int cityBlocks){
        List<CityBlock> blocks = new ArrayList<>();
        for(int i = 0; i < cityBlocks; i++){
            CityBlock block = new CityBlock(positions.get(i));
            blocks.add(block);
        }
        return blocks;
    }

    public static List<CityBlock> generateBlocks(int x, int y, int number) {
        List<CityBlock> blocks = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < number; i++) {
            Position position = new Position(x,y);
            Position pos;
                int dx = (int) Math.round(rand.nextGaussian() * 2);
                int dy = (int) Math.round(rand.nextGaussian() * 2);
                pos = new Position(position.getxCoordinate() + dx, position.getyCoordinate() + dy);
                CityBlock cityBlock = new CityBlock(pos);
                blocks.add(cityBlock);
        }

        return blocks;
    }

    public void addCityBlock(CityBlock block) {
        cityBlocks.add(block);
    }

    public List<CityBlock> getCityBlocks() {
        return cityBlocks;
    }
}