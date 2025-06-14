//package pt.ipp.isep.dei.ui.console;
//
//import pt.ipp.isep.dei.controller.SaveSimulatorController;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class SaveSimulatorUI {
//
//    private final SaveSimulatorController controller = new SaveSimulatorController();
//
//    public void start() {
//        Scanner scanner = new Scanner(System.in);
//
//        int simulatorCount = controller.getSimulatorCount();
//        if (simulatorCount == 0) {
//            System.out.println("No available simulators to save.");
//            return;
//        }
//
//        System.out.println("Available Simulators:");
//        List<String> simulatorNames = controller.getSimulatorNames();
//        for (int i = 0; i < simulatorNames.size(); i++) {
//            System.out.println((i + 1) + ". " + simulatorNames.get(i));
//        }
//
//        System.out.print("Choose the simulator number to save: ");
//        int index = scanner.nextInt() - 1;
//        scanner.nextLine();
//
//        System.out.print("Serialized File name (e.g., simulator.ser): ");
//        String filename = scanner.nextLine();
//
//        boolean success = controller.saveSimulatorByIndex(index, filename);
//        if (success) {
//            System.out.println("Simulator saved successfully.");
//        } else {
//            System.out.println("Failed to save simulator.");
//        }
//    }
//}