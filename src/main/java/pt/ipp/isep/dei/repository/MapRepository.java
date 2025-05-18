package pt.ipp.isep.dei.repository;
import pt.ipp.isep.dei.domain.Map;
import java.util.ArrayList;

public class MapRepository {
    private ArrayList<Map> maps = new ArrayList<>();

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public void addMap(Map map) {
        maps.add(map);
    }
}


