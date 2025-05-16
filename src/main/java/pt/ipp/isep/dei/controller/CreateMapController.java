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
    public boolean checkName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }
    public int checkSize(String sizeInput) {
        // Step 1: Is it a number?
        try {
            double temp = Double.parseDouble(sizeInput);

            // Step 2: Is it an integer? (e.g., 5.0 is ok, 5.5 is not)
            if (temp % 1 != 0) {
                System.out.println("Invalid size! Must be a whole number (no decimals).");
                System.exit(1);
            }

            int size = (int) temp;

            // Step 3: Is it positive?
            if (size <= 0) {
                System.out.println("Invalid size! Must be a positive integer.");
                System.exit(1);
            }

            return size;

        } catch (NumberFormatException e) {
            System.out.println("Invalid size! Must be a number.");
            System.exit(1);
        }

        return -1;
    }

    //Cria um mapa e guarda no repositório
    public void createMap(int size, String name) {
        if (!checkName(name)) {
            System.out.println("Invalid Map name!");
            System.exit(1);
        }

        Map map = new Map(name, size);
        //Repositories.getInstance().getMapRepository().addMap(map);
    }
}