package pt.ipp.isep.dei.ui.console;
import pt.ipp.isep.dei.controller.CreateMapController;
import java.util.Scanner;

public class CreateMapUI {
    // vai depender do controller para tudo
    private CreateMapController controller;

    // inicializa o controller
    public CreateMapUI() {
        controller = new CreateMapController();
    }

    // recebe o nome e size
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter map name: ");
        String name = scanner.nextLine();
        System.out.print("Enter map size: ");
        int size = scanner.nextInt();

        // chama o controller para criar o mapa no repositório
        controller.createMap(size, name);
        System.out.println("Map created successfully.");
    }

}