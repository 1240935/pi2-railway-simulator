package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.Editor;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.ui.gui.PlayerMenuLauncher;

import java.util.Scanner;

/**
 * Handles the main console interface for the railway game system,
 * including login functionality and role-based menu navigation.
 */
public class MenuUI {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Starts the main system menu, prompting the user to log in as a Player or Editor.
     * Redirects to the appropriate role-specific menu upon successful login.
     */
    /**
     * Starts the main system menu, prompting the user to log in as a Player or Editor.
     * Redirects to the appropriate role-specific menu upon successful login.
     * Repeats until the user chooses to exit.
     */
    public void start() {
        while (true) {
            System.out.println("=== Login Menu ===");
            System.out.println("1. Login as Editor");
            System.out.println("2. Login as Player");
            System.out.println("0. Exit");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    Editor editor = loginAsEditor();
                    if (editor != null) showEditorMenu(editor);
                }
                case 2 -> {
                    Player player = loginAsPlayer();
                    if (player != null) {
                        int uiChoice = promptUIChoice();
                        if (uiChoice == 1) {
                            showPlayerMenu(player);
                        } else if (uiChoice == 2) {
                            System.out.println("Launching graphical interface...");
                            PlayerMenuLauncher.launchUI(player);
                        } else {
                            System.out.println("Invalid UI choice. Returning to main menu.");
                        }
                    }
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }


    /**
     * Prompts the user for player credentials and budget, validates the password,
     * and returns a Player object if valid.
     *
     * @return a new {@link Player} instance if login is valid; otherwise, {@code null}
     */
    private Player loginAsPlayer() {
        String username = promptUsername();
        String password = promptPassword();

        if (!isValidPassword(password)) {
            System.out.println("Invalid password format.");
            return null;
        }

        System.out.print("Enter budget: ");
        int budget = scanner.nextInt();
        scanner.nextLine();

        return new Player(username, password, budget);
    }

    /**
     * Prompts the user for editor credentials and validates the password.
     *
     * @return a new {@link Editor} instance if login is valid; otherwise, {@code null}
     */
    private Editor loginAsEditor() {
        String username = promptUsername();
        String password = promptPassword();

        if (!isValidPassword(password)) {
            System.out.println("Invalid password format.");
            return null;
        }

        return new Editor(username, password);
    }

    /**
     * Displays the Player menu with user stories 5–14.
     * Executes the selected feature based on user input.
     *
     * @param player the {@link Player} who has logged in
     */
    private void showPlayerMenu(Player player) {
        while (true) {
            System.out.println("\n=== Player Menu ===");
            System.out.println("5. Build Station");
            System.out.println("6. Upgrade Station");
            System.out.println("7. View Station Details");
            System.out.println("9. Buy Locomotive");
            System.out.println("10. Assign Route");
            System.out.println("12. Simulator Menu");
            System.out.println("13. Train Route Verification");
            System.out.println("14. Railway Maintenance");
            System.out.println("23. Save a Simulator");
            System.out.println("24. Load a Simulator");
            System.out.println("27. Shortest Route Calculation");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "5" -> new BuildStationUI().start(player);
                case "6" -> new UpgradeStationUI().start(player);
                case "7" -> new ViewStationDetailsUI().start();
                case "9" -> new BuyLocomotiveUI().start(player);
                case "10" -> new AssignRouteUI().start();
                case "12" -> new SimulatorMenuUI(player).start();
                case "13" -> new VerifyTrainTravelUI().start();
                case "14" -> new RailwayMaintenanceUI().start();
                case "23" -> new SaveSimulatorUI().start();
                case "24" -> new LoadSimulatorUI().start();
                case "27" -> new ShortestPathUI().start();
                case "0" -> {
                    System.out.println("Logging out...");
                    System.out.println();
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Displays the Editor menu with user stories 1–4.
     * Executes the selected feature based on user input.
     *
     * @param editor the {@link Editor} who has logged in
     */
    private void showEditorMenu(Editor editor) {
        while (true) {
            System.out.println("\n=== Editor Menu ===");
            System.out.println("1. Create Map");
            System.out.println("2. Create Industry");
            System.out.println("3. Add City");
            System.out.println("4. Create Scenario");
            System.out.println("5. Save Map");
            System.out.println("6. Load Map");
            System.out.println("7. Save Scenario");
            System.out.println("8. Load Scenario");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> new CreateMapUI().start();
                case "2" -> new CreateIndustryUI().start();
                case "3" -> new AddCityUI().start();
                case "4" -> new CreateScenarioUI().start();
                case "5" -> new SaveMapUI().start();
                case "6" -> new LoadMapUI().start();
                case "7" -> new SaveScenarioUI().start();
                case "8" -> new LoadScenarioUI().start();
                case "0" -> {
                    System.out.println("Logging out...");
                    System.out.println();
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Prompts the user to enter a username.
     *
     * @return the username entered by the user
     */
    private String promptUsername() {
        System.out.print("Username (valid characters only): ");
        return scanner.nextLine();
    }

    /**
     * Prompts the user to enter a password.
     *
     * @return the password entered by the user
     */
    private String promptPassword() {
        System.out.print("Password (7 characters, including 3 capital and 2 numbers): ");
        return scanner.nextLine();
    }

    private int promptUIChoice() {
        System.out.println("Choose UI type:");
        System.out.println("1. Console UI");
        System.out.println("2. Graphical UI (JavaFX)");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }



    /**
     * Validates whether a given password meets the following constraints:
     * <ul>
     *     <li>Exactly 7 characters long</li>
     *     <li>At least 3 uppercase letters</li>
     *     <li>At least 2 digits</li>
     *     <li>All characters must be alphanumeric (letters or digits only)</li>
     * </ul>
     *
     * @param password the password string to validate
     * @return {@code true} if the password meets all the constraints, {@code false} otherwise
     */
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
