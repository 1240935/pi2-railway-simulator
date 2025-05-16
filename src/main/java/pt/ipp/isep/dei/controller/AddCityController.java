package pt.ipp.isep.dei.controller;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    public class AddCityController {
        private Map selectedMap;
        private List<Position> selectedPositions;
        private Position center;
        private String selectedName;
        private List<CityBlock> selectedBlocks;

        public AddCityController() {
        }

        public List<Position> getSelectedPositions() {
            return selectedPositions;
        }

        public void setSelectedBlocks(List<CityBlock> blocks){
            selectedBlocks = blocks;
        }

        public void setSelectedName(String name){
            selectedName = name;
        }

        public void setCenter(Position position){
            center = position;
        }
        public void setMap(Map map) {
            this.selectedMap = map;
        }

        public List<Map> getAvailableMaps() {
            return Repositories.getInstance().getMapRepository().getMaps();
        }

        public Map chooseMap(List<Map> maps, int choice){
            Map map = maps.get(choice-1);
            return map;
        }
        public boolean checkName(String name) {
            return name.matches("^[a-zA-Z\\s]+$");
        }

        public List<String> getAvailableMapNames() {
            List<String> names = new ArrayList<>();
            for (Map map : getAvailableMaps()) {
                names.add(map.getName());
            }
            return names;
        }

        public Position createPosition(int x, int y) {
            return new Position(x, y);
        }

        public boolean checkCoordinates(Position position) {
            return selectedMap != null && selectedMap.checkCoordinates(position);
        }
        public List<CityBlock> createBlocks(List<Position> positions, int cityblocks){
            List<CityBlock> blocks = null;
            for(int i = 0; i < cityblocks; i++){
                CityBlock block = new CityBlock(positions.get(i));
                blocks.add(block);
            }
            return blocks;
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

        public void addSelectedPositions(int x, int y){
            selectedPositions.add(createPosition(x,y));
        }

        public void createCity() {
            if (!checkName(selectedName)) {
                throw new IllegalArgumentException("Invalid city name");
            }
            if (!checkCoordinates(center)) {
                throw new IllegalArgumentException("Invalid position");
            }

            City city = new City(selectedName, center, selectedBlocks);
            selectedMap.addCity(city);
        }
        public void showInfo(){
            selectedPositions.toString();
            center.toString();
        }
    }
