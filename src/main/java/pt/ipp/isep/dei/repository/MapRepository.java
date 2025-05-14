package pt.ipp.isep.dei.repository;
import pt.ipp.isep.dei.domain.Map;
import java.util.ArrayList;
import java.util.List;

public class MapRepository {
    // Cria o repositório como arrayList de mapas
    private List<Map> maps = new ArrayList<>();

    // Para adicionar mapas ao repositório
    public void addMap(Map map) {
        maps.add(map);
    }

    // Getter do repositório
    public List<Map> getMaps() {
        return maps;
    }
}


