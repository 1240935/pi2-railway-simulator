package pt.ipp.isep.dei.controller;
import pt.ipp.isep.dei.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    public class AddCityController {
        private Map selectedMap;

        public AddCityController() {
        }

        public void setMap(Map map) {
            this.selectedMap = map;
        }

        public boolean checkName(String name) {
            return name.matches("^[a-zA-Z\\s]+$");
        }

        public Position createPosition(int x, int y) {
            return new Position(x, y);
        }

        public boolean checkCoordinates(Position position) {
            return selectedMap != null && selectedMap.checkCoordinates(position);
        }

        public List<CityBlock> generateBlocks(Position center, int number, boolean randomize) {
            List<CityBlock> blocks = new ArrayList<>();
            Random rand = new Random();

            for (int i = 0; i < number; i++) {
                Position pos;
                if (randomize) {
                    int dx = (int) Math.round(rand.nextGaussian() * 2);
                    int dy = (int) Math.round(rand.nextGaussian() * 2);
                    pos = new Position(center.getxCoordinate() + dx, center.getyCoordinate() + dy);
                } else {
                    pos = center;
                }
                blocks.add(new CityBlock(pos));
            }

            return blocks;
        }

        public void createCity(String name, Position position, List<CityBlock> blocks) {
            if (!checkName(name)) {
                throw new IllegalArgumentException("Invalid city name");
            }
            if (!checkCoordinates(position)) {
                throw new IllegalArgumentException("Invalid position");
            }

            City city = new City(name, position, blocks);
            selectedMap.addCity(city);
        }
    }
