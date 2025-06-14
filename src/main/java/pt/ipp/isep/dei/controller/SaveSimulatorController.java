//package pt.ipp.isep.dei.controller;
//
//import pt.ipp.isep.dei.domain.Simulator;
//import pt.ipp.isep.dei.repository.Repositories;
//import pt.ipp.isep.dei.repository.SimulatorRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SaveSimulatorController {
//
//    private final SimulatorRepository repository;
//
//    public SaveSimulatorController() {
//        this.repository = Repositories.getInstance().getSimulatorRepository();
//    }
//
//    public boolean saveSimulatorToFile(Simulator simulator, String filePath) {
//        return repository.saveSimulatorToFile(simulator, filePath);
//    }
//
//    public List<Simulator> getSimulators() {
//        return repository.getSimulators();
//    }
//
//    public int getSimulatorCount() {
//        return repository.getSimulators().size();
//    }
//
//    public List<String> getSimulatorNames() {
//        List<String> names = new ArrayList<>();
//        for (Simulator simulator : repository.getSimulators()) {
//            names.add(simulator.getName());
//        }
//        return names;
//    }
//
//    public boolean saveSimulatorByIndex(int index, String filePath) {
//        List<Simulator> simulators = repository.getSimulators();
//        if (index < 0 || index >= simulators.size()) {
//            return false;
//        }
//        Simulator selectedSimulator = simulators.get(index);
//        return repository.saveSimulatorToFile(selectedSimulator, filePath);
//    }
//}