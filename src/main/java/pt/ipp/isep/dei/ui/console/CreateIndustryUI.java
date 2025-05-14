package pt.ipp.isep.dei.ui.console;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.controller.*;
import java.util.Scanner;

public class CreateIndustryUI {
    //  cria uma instancia do controller
    private CreateIndustryController controller = new CreateIndustryController();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        //printa os mapas disponiveis
        System.out.println("Available maps:");
        var maps = controller.getAvailableMaps();
        for (int i = 0; i < maps.size(); i++) {
            System.out.println(i + ": " + maps.get(i).getName());
        }
        // pede para escolher o mapa pelo seu index
        System.out.print("Select map by number: ");
        int mapIndex = scanner.nextInt();
        // seleciona esse mapa
        controller.setMap(maps.get(mapIndex));

        // seleção das coordenadas para criação da posição
        System.out.print("Enter X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter Y coordinate: ");
        int y = scanner.nextInt();
        Position pos = controller.createPosition(x, y);
/*
        // se as coordenadas não estiverem disponiveis, finaliza o creator (meio errado)
        if (!controller.checkCoordinates(pos)) {
            System.out.println("Invalid coordinates. Exiting.");
            return;
        }
*/
        //mostra os tipos de industria disponiveis (não bem feito)
        System.out.println("Available industry types:");
        IndustryType[] types = controller.getAvailableIndustriesTypes();
        for (int i = 0; i < types.length; i++) {
            System.out.println(i + ": " + types[i]);
        }
        System.out.print("Select industry type: ");
        // seleciona o tipo
        controller.setType(types[scanner.nextInt()]);

        //mostra os tipos de recursos disponiveis (não bem feito)
        System.out.println("Available resource types:");
        ResourceType[] resources = controller.getAvailableResourceTypes();
        for (int i = 0; i < resources.length; i++) {
            System.out.println(i + ": " + resources[i]);
        }
        System.out.print("Select resource type: ");
        //seleciona o tipo
        controller.setResourceType(resources[scanner.nextInt()]);

        //cria a industria
        Industry industry = controller.createIndustry(pos);
        System.out.println("Industry created successfully!");
        System.out.println(industry);
    }
}