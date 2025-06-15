package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class responsible for creating and configuring a new Scenario.
 * This includes selecting maps, configuring locomotive types, industry types,
 * resource imports/exports, and setting industry changers.
 */
public class CreateScenarioController {

    /**
     * Default constructor for CreateScenarioController.
     */
    public CreateScenarioController() {
    }

    /**
     * Retrieves the list of available map names from the repository.
     *
     * @return List of names of available maps.
     */
    public List<String> getAvailableMapNames() {
        MapListMapper mapListMapper = new MapListMapper();
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        List<MapDTO> mapDTOs = mapListMapper.toDTOList(maps);

        List<String> names = new ArrayList<>();
        for (MapDTO mapDTO : mapDTOs) {
            names.add(mapDTO.getName());
        }

        return names;
    }

    /**
     * Returns the size of the map identified by the given index.
     *
     * @param index Index of the map in the repository.
     * @return Size of the map.
     */
    public int getMapSizeByIndex(int index) {
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        return maps.get(index).getSize();
    }

    /**
     * Retrieves the list of names of all available resource types.
     *
     * @return List of resource type names.
     */
    public List<String> getAvailableResourceTypeNames() {
        ResourceTypeListMapper resourceTypeListMapper = new ResourceTypeListMapper();
        List<ResourceType> types = Repositories.getInstance().getResourceTypeRepository().getAvailableResourceTypes();
        List<ResourceTypeDTO> dtos = resourceTypeListMapper.toDTOList(types);

        List<String> names = new ArrayList<>();
        for (ResourceTypeDTO dto : dtos) {
            names.add(dto.toString());
        }

        return names;
    }

    /**
     * Retrieves all available industry types from the repository.
     *
     * @return List of IndustryType domain objects.
     */
    public List<IndustryType> getAvailableIndustryTypes(){
        return Repositories.getInstance().getIndustryTypeRepository().getIndustryTypes();
    }

    /**
     * Retrieves all available locomotive types from the repository.
     *
     * @return List of LocomotiveType domain objects.
     */
    public List<LocomotiveType> getAvailableLocomotivesTypes(){
        return Repositories.getInstance().getLocomotiveTypeRepository().getLocomotiveTypes();
    }

    /**
     * Returns the names of all available locomotive types as strings.
     *
     * @return List of locomotive type names.
     */
    public List<String> getAvailableLocomotiveTypeNames() {
        List<String> names = new ArrayList<>();
        for (LocomotiveType locomotiveType : getAvailableLocomotivesTypes()) {
            names.add(locomotiveType.toString());
        }
        return names;
    }

    /**
     * Returns the names of all available industry types as strings.
     *
     * @return List of industry type names.
     */
    public List<String> getAvailableIndustryTypeNames() {
        List<String> names = new ArrayList<>();
        for (IndustryType industryType : getAvailableIndustryTypes()) {
            names.add(industryType.toString());
        }
        return names;
    }

    /**
     * Selects locomotive types based on a list of user choices (indexes).
     *
     * @param choices List of integer indexes representing the user selection.
     * @return List of selected LocomotiveType objects.
     */
    public List<LocomotiveType> chooseLocomotiveTypes(List<Integer> choices) {
        List<LocomotiveType> types = getAvailableLocomotivesTypes();
        List<LocomotiveType> selected = new ArrayList<>();
        for (int index : choices) {
            if (index >= 1 && index <= types.size()) {
                selected.add(types.get(index - 1));
            } else {
                System.out.println("Invalid index: " + index);
            }
        }
        return selected;
    }

    /**
     * Selects resource types based on a list of user choices (indexes).
     *
     * @param choices List of integer indexes representing the user selection.
     * @return List of selected ResourceType objects.
     */
    public List<ResourceType> chooseResourceTypes(List<Integer> choices) {
        List<ResourceType> types = Repositories.getInstance().getResourceTypeRepository().getAvailableResourceTypes();
        List<ResourceType> selected = new ArrayList<>();
        for (int index : choices) {
            if (index >= 1 && index <= types.size()) {
                selected.add(types.get(index - 1));
            } else {
                System.out.println("Invalid index: " + index);
            }
        }

        return selected;
    }

    /**
     * Retrieves the list of port industries from the map identified by the given index.
     *
     * @param mapIndex Index of the map.
     * @return List of Industry objects that are ports.
     */
    public List<Industry> getPorts(int mapIndex){
        List<Industry> industries = getMapByIndex(mapIndex).getIndustries();
        List<Industry> ports = new ArrayList<>();

        for (Industry industry : industries) {
            if (industry.getType() == IndustryType.PORT) {
                ports.add(industry);
            }
        }

        return ports;
    }

    /**
     * Configures import and export resource types for all ports on the map identified by the given index.
     *
     * @param mapIndex Index of the map.
     * @param imports List of resource types to import.
     * @param exports List of resource types to export.
     */
    public void configurePorts(int mapIndex, List<ResourceType> imports, List<ResourceType> exports) {
        List<Industry> ports = getPorts(mapIndex);

        for (Industry port : ports) {
            port.getType().setImportation(imports);
            port.getType().setExportation(exports);
        }
    }

    /**
     * Retrieves all locomotive types from the repository.
     *
     * @return List of LocomotiveType objects.
     */
    public List<LocomotiveType> getLocomotiveTypes() {
        return Repositories.getInstance().getLocomotiveTypeRepository().getLocomotiveTypes();
    }

    /**
     * Reads a line of integer selections from user input.
     * User input must be integers separated by whitespace.
     * If the input is "!", returns null to indicate cancellation.
     * Invalid inputs are ignored with an error message.
     *
     * @param prompt Message to display when asking for input.
     * @return List of integers parsed from the input, or null if cancelled.
     */
    public List<Integer> readIntegerSelections(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        String input = scanner.nextLine();
        if(input.equals("!")){
            return null;
        } else {
            List<Integer> selections = new ArrayList<>();
            for (String s : input.trim().split("\\s+")) {
                try {
                    selections.add(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + s);
                }
            }
            return selections;
        }
    }

    /**
     * Retrieves the Map object by index from the map repository.
     *
     * @param mapIndex Index of the map.
     * @return Map object at the specified index.
     */
    public Map getMapByIndex(int mapIndex){
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        return maps.get(mapIndex);
    }

    /**
     * Creates a new Scenario from the provided ScenarioDTO and adds it to the scenario repository.
     *
     * @param dto Data transfer object containing scenario configuration.
     * @return true if the scenario was created successfully.
     */
    public boolean createScenario(ScenarioDTO dto) {
        ScenarioMapper scenarioMapper = new ScenarioMapper();
        ScenarioRepository scenarioRepo = Repositories.getInstance().getScenarioRepository();
        Scenario scenario = scenarioMapper.toScenario(dto);
        scenarioRepo.addScenario(scenario);
        return true;
    }
}
