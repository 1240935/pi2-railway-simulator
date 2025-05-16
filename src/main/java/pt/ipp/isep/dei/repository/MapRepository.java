package pt.ipp.isep.dei.repository;
import pt.ipp.isep.dei.domain.Map;
import java.util.ArrayList;

public class MapRepository {
    // Cria o repositório como arrayList de mapas
    private ArrayList<Map> maps = new ArrayList<>();

    // Para adicionar mapas ao repositório
    public void add(Map map) {
        if (map != null && !maps.contains(map)) {
            maps.add(map);
        }
    }

    // Getter do repositório
    public ArrayList<Map> getMaps() {
        return maps;
    }
}


