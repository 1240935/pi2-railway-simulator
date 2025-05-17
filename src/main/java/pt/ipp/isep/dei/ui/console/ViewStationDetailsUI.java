package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.ViewStationController;
import pt.ipp.isep.dei.domain.*;

import java.util.List;
import java.util.Scanner;

public class ViewStationDetailsUI {
    public void start() {
        ViewStationController controller = new ViewStationController();
        List<Station> stations = controller.getAvailableStations();

        if (stations.isEmpty()) {
            System.out.println("There are no stations available.");
            return;
        }

        System.out.println("\nAvailable Stations:");
        for (int i = 0; i < stations.size(); i++) {
            System.out.printf("%d - %s%n", i, stations.get(i).getName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nSelect a station by number: ");
        int choice;

        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (choice < 0 || choice >= stations.size()) {
            System.out.println("Invalid station number.");
            return;
        }

        Station selectedStation = stations.get(choice);
        System.out.println("\n--- Station Details ---");
        System.out.println("Name: " + selectedStation.getName());

        BuildingType buildingType = controller.getBuildingType(selectedStation);
        System.out.println("Building Type: " + (buildingType != null ? buildingType : "None"));

        List<Cargo> cargoes = controller.getCargoes(selectedStation);
        if (cargoes == null || cargoes.isEmpty()) {
            System.out.println("No cargoes available.");
        } else {
            System.out.println("Cargoes:");
            for (Cargo cargo : cargoes) {
                String type = cargo.getType().toString();
                int quantity = cargo.getQuantity();
                String status = cargo.isSupply() ? "Supply" : "Demand";
                System.out.printf(" - %s | Quantity: %d | %s%n", type, quantity, status);
            }
        }
    }
}

