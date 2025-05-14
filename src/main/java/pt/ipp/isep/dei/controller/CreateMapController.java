package pt.ipp.isep.dei.controller;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.Repositories;

public class CreateMapController {
     // construtor default, podemos usá-lo para testes, é instanceado na UI
    public CreateMapController() {
    }

    //  procura por um mapa pelo nome, passando por todos os mapas
    public Map getMap(String name) {
        for (Map m : Repositories.getInstance().getMapRepository().getMaps()) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    //Cria um mapa e guarda no repositório
    public void createMap(int size, String name) {
        Map map = new Map(name, size);
        Repositories.getInstance().getMapRepository().addMap(map);
    }
}