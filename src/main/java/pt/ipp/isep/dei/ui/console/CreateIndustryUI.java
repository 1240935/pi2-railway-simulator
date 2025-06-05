package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateIndustryController;
import pt.ipp.isep.dei.domain.IndustryType;
import pt.ipp.isep.dei.dto.IndustryDTO;

import java.util.List;
import java.util.Scanner;

public class CreateIndustryUI {


    private final CreateIndustryController controller = new CreateIndustryController();


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

        IndustryType selectedType = controller.getIndustryTypeByIndex(industryTypeIndex); // User input

        IndustryDTO dto = new IndustryDTO();
        dto.setIndustryType(selectedType);
        dto.setxCoordinate(x);
        dto.setyCoordinate(y);
        dto.setSize(size);

        boolean result = controller.createIndustry(dto,mapIndex);


        if (result) {
            System.out.println("Industry was created successfully.");
        } else {
            System.out.println("Failed to create industry.");
        }
    }
}
