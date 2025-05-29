package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;


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

            // Avançar até "Cities"
            while ((line = reader.readLine()) != null && !line.equals("Cities")) {
                // skip
            }

            // Ler header das Cities
            reader.readLine();

            // Ler as cidades
            while ((line = reader.readLine()) != null && !line.equals("CityBlocks")) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String cityName = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    City city = new City(cityName, new Position(x, y), new ArrayList<>());
                    map.addCity(city);
                }
            }

            // Ler header dos CityBlocks
            reader.readLine();

            // Ler os city blocks
            while ((line = reader.readLine()) != null && !line.equals("Industries")) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String cityName = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);

                    City targetCity = findCityByName(map, cityName);
                    if (targetCity != null) {
                        targetCity.getBlocks().add(new CityBlock(new Position(x, y)));
                    } else {
                        System.out.println("City block refers to unknown city: " + cityName);
                    }
                }
            }

            // Ler header das Industries
            reader.readLine();

            // Ler as indústrias
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String industryType = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);

                    IndustryType type = IndustryType.valueOf(industryType.toUpperCase());
                    Industry industry = new Industry(type, new Position(x, y));
                    map.addIndustry(industry);
                }
            }

            // Adicionar ao repositório
            Repositories.getInstance().getMapRepository().addMap(map);
            System.out.println("Map loaded successfully.");
            return true;

        } catch (IOException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
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

    private City findCityByName(Map map, String name) {
        for (City city : map.getCities()) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        return null;
    }
}

