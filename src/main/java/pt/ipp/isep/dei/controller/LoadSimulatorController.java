//package pt.ipp.isep.dei.controller;
//
//import pt.ipp.isep.dei.domain.Simulator;
//import pt.ipp.isep.dei.repository.Repositories;
//
//public class LoadSimulatorController {
//
//    public boolean loadSerializedSimulator(String filePath) {
//        Simulator simulator = Repositories.getInstance().getSimulatorRepository().loadSimulatorFromFile(filePath);
//        if (simulator == null) {
//            System.out.println("Failed to load simulator from file.");
//            return false;
//        }
//
//        if (simulatorExists(simulator.getName())) {
//            System.out.println("A simulator with the name '" + simulator.getName() + "' already exists.");
//            return false;
//        }
//
//        Repositories.getInstance().getSimulatorRepository().addSimulator(simulator);
//        System.out.println("Simulator loaded successfully from file.");
//        return true;
//    }
//
//    private boolean simulatorExists(String name) {
//        return Repositories.getInstance()
//                .getSimulatorRepository()
//                .getSimulators()
//                .stream()
//                .anyMatch(s -> s.getName().equalsIgnoreCase(name));
//    }
//}