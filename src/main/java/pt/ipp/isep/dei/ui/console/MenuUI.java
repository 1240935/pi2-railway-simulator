package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.Editor;
import pt.ipp.isep.dei.domain.Player;

import java.util.Scanner;

public class MenuUI {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("=== Railway System Menu ===");
        System.out.println("1. Login as Player");
        System.out.println("2. Login as Editor");
        System.out.println("0. Exit");

        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1 -> {
                Player player = loginAsPlayer();
                if (player != null) showPlayerMenu(player);
            }
            case 2 -> {
                Editor editor = loginAsEditor();
                if (editor != null) showEditorMenu(editor);
            }
            case 0 -> System.out.println("Exiting...");
            default -> {
                System.out.println("Invalid option.");
                start();
            }
        }
    }



    private Player loginAsPlayer() {
        String username = promptUsername();
        String password = promptPassword();

        if (!isValidPassword(password)) {
            System.out.println("Invalid password format.");
            return null;
        }

        System.out.print("Enter budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        return new Player(username, password, budget);
    }

    private Editor loginAsEditor() {
        String username = promptUsername();
        String password = promptPassword();

        if (!isValidPassword(password)) {
            System.out.println("Invalid password format.");
            return null;
        }

        return new Editor(username, password);
    }

    private void showPlayerMenu(Player player) {
        while (true) {
            System.out.println("\n=== Player Menu ===");
            System.out.println("5. Build Station");
            System.out.println("6. Upgrade Station");
            System.out.println("7. View Station Details");
            System.out.println("9. Buy Locomotive");
            System.out.println("10. Select Route");
            System.out.println("12. Simulator");
            System.out.println("13. Train Route Verification");
            System.out.println("14. Railway Maintenance");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "5" -> new BuildStationUI().start();
                case "6" -> new UpgradeStationUI().start();
                case "7" -> new ViewStationDetailsUI().start();
                case "9" -> new BuyLocomotiveUI().start();
                case "10" -> new SelectRouteUI().start();
                case "12" -> new SimulatorUI().start();
                case "13" -> new VerifyTrainTravelUI().start();
                case "14" -> new RailwayMaintenanceUI().start();
                case "0" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void showEditorMenu(Editor editor) {
        while (true) {
            System.out.println("\n=== Editor Menu ===");
            System.out.println("1. Create Map");
            System.out.println("2. Create Industry");
            System.out.println("3. Add City");
            System.out.println("4. Create Scenario");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> new CreateMapUI().start();
                case "2" -> new CreateIndustryUI().start();
                case "3" -> new AddCityUI().start();
                case "4" -> new CreateScenarioUI().start();
                case "0" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }


    private String promptUsername() {
        System.out.print("Username (letters, digits, '.', '-', '_'): ");
        return scanner.nextLine();
    }

    private String promptPassword() {
        System.out.print("Password (7 chars, 3 uppercase, 2 digits): ");
        return scanner.nextLine();
    }

    private boolean isValidPassword(String password) {
        if (password.length() != 7) return false;

        int upper = 0, digits = 0;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) upper++;
            else if (Character.isDigit(c)) digits++;
            else if (!Character.isLetterOrDigit(c)) return false;
        }

        return upper >= 3 && digits >= 2;
    }
}
