package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateIndustryController;
import pt.ipp.isep.dei.dto.IndustryDTO;

import java.util.List;
import java.util.Scanner;

/**
 * Interface de consola para criação de uma indústria.
 * Permite ao utilizador escolher um mapa, definir coordenadas e tipo de indústria.
 */
public class CreateIndustryUI {

    private final CreateIndustryController controller = new CreateIndustryController();

    /**
     * Inicia o fluxo para criar uma indústria.
     * Solicita seleção do mapa, coordenadas e tipo de indústria ao utilizador,
     * depois envia os dados ao controlador para criação.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available maps:");
        List<String> mapNames = controller.getAvailableMapNames();
        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ": " + mapNames.get(i));
        }

        System.out.print("Select map by number: ");
        int mapIndex = scanner.nextInt() - 1;

        int size = controller.getMapSizeByIndex(mapIndex);

        System.out.print("Enter X coordinate: ");
        int x = scanner.nextInt();

        System.out.print("Enter Y coordinate: ");
        int y = scanner.nextInt();

        System.out.println("Available industry types:");
        List<String> industryTypeNames = controller.getAvailableIndustryTypeNames();
        for (int i = 0; i < industryTypeNames.size(); i++) {
            System.out.println(i + ": " + industryTypeNames.get(i));
        }

        System.out.print("Select industry type: ");
        int industryTypeIndex = scanner.nextInt();

        IndustryDTO dto = new IndustryDTO();
        dto.setIndustryType(controller.getIndustryTypeByIndex(industryTypeIndex));
        dto.setxCoordinate(x);
        dto.setyCoordinate(y);
        dto.setSize(size);

        controller.createIndustry(dto, mapIndex);
    }
}
