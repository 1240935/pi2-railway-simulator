package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.Position;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.domain.IndustryType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadMapController {

    public boolean loadMapFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Ler MapName, MapSize, MapScale
            String name = reader.readLine().split(";")[1];
            int size = Integer.parseInt(reader.readLine().split(";")[1]);
            int scale = Integer.parseInt(reader.readLine().split(";")[1]);

            // Verificar se já existe mapa com este nome
            if (mapExists(name)) {
                System.out.println("A map with the name '" + name + "' already exists.");
                return false;
            }

            Map map = new Map(size, name, scale);

            // Avançar para "Cities"
            while ((line = reader.readLine()) != null && !line.equals("Cities")) {
                // skip
            }

            // Ler header: Name;X;Y
            reader.readLine();

            // Ler cidades
            while ((line = reader.readLine()) != null && !line.equals("Industries")) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String cityName = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    City city = new City(cityName, new Position(x, y), new ArrayList<>());
                    map.addCity(city);
                }
            }

            // Ler header: Type;X;Y
            reader.readLine();

            // Ler indústrias
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String industryType = parts[0];
                    IndustryType type = IndustryType.valueOf(industryType.toUpperCase());
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);

                    Industry industry = new Industry(type, new Position(x, y));
                    map.addIndustry(industry);
                }
            }

            // Adicionar ao repositório
            Repositories.getInstance().getMapRepository().addMap(map);
            System.out.println("Map loaded successfully.");
            return true;

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Failed to load map: " + e.getMessage());
            return false;
        }
    }

    private boolean mapExists(String name) {
        for (Map existingMap : Repositories.getInstance().getMapRepository().getMaps()) {
            if (existingMap.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
