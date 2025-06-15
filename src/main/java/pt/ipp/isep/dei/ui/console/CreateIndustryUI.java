package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateIndustryController;
import pt.ipp.isep.dei.dto.IndustryDTO;

import java.util.List;
import java.util.Scanner;

/**
 * Console interface for creating an industry.
 * Allows the user to choose a map, define coordinates, and select the industry type.
 */
public class CreateIndustryUI {

    private final CreateIndustryController controller = new CreateIndustryController();

    /**
     * Starts the flow to create an industry.
     * Requests selection of map, coordinates, and industry type from the user,
     * then sends the data to the controller for creation.
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
