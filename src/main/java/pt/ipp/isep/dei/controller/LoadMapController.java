package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.Repositories;

public class LoadMapController {

    public boolean loadSerializedMap(String filePath) {
        Map map = Repositories.getInstance().getMapRepository().loadMapFromSerializedFile(filePath);
        if (map == null) {
            System.out.println("Failed to load map from file.");
            return false;
        }

        // Verificar duplicado
        if (mapExists(map.getName())) {
            System.out.println("A map with the name '" + map.getName() + "' already exists.");
            return false;
        }

        Repositories.getInstance().getMapRepository().addMap(map);
        System.out.println("Map loaded successfully from file.");
        return true;
    }

    private boolean mapExists(String name) {
        return Repositories.getInstance()
                .getMapRepository()
                .getMaps()
                .stream()
                .anyMatch(m -> m.getName().equalsIgnoreCase(name));
    }
}
