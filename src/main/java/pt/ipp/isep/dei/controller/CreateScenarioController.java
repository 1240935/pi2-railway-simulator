package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class responsible for creating a new Scenario.
 * It handles the selection and configuration of maps, years,
 * locomotive types, industry types, resource import/export, and industry changers.
 */
public class CreateScenarioController {


    /**
     * Default constructor for CreateScenarioController.
     */
    public CreateScenarioController() {
    }

    public List<String> getAvailableMapNames() {
        MapListMapper mapListMapper = new MapListMapper();
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        List<MapDTO> mapDTOs = mapListMapper.toDTOList(maps);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < mapDTOs.size(); i++) {
            names.add(mapDTOs.get(i).getName());
        }

        return names;
    }

    public int getMapSizeByIndex(int index) {
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        return maps.get(index).getSize();
    }


  
    public List<String> getAvailableResourceTypeNames() {
        ResourceTypeListMapper resourceTypeListMapper = new ResourceTypeListMapper();
        List<ResourceType> types = Repositories.getInstance().getResourceTypeRepository().getAvailableResourceTypes();
        List<ResourceTypeDTO> dtos = resourceTypeListMapper.toDTOList(types);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            names.add(types.get(i).toString() +" " +dtos.get(i).toString());
        }

        return names;
    }

    /**
     * Retrieves all available industry types from the repository.
     *
     * @return List of available IndustryType objects.
     */
    public List<IndustryType> getAvailableIndustryTypes(){
        return Repositories.getInstance().getIndustryTypeRepository().getIndustryTypes();
    }

    /**
     * Retrieves all available locomotive types from the repository.
     *
     * @return List of available LocomotiveType objects.
     */
    public List<LocomotiveType> getAvailableLocomotivesTypes(){
        return Repositories.getInstance().getLocomotiveTypeRepository().getLocomotiveTypes();
    }

    /**
     * Retrieves the names of all available locomotive types.
     *
     * @return List of Strings representing locomotive type names.
     */
    public List<String> getAvailableLocomotiveTypeNames() {
        List<String> names = new ArrayList<>();
        for (LocomotiveType locomotiveType : getAvailableLocomotivesTypes()) {
            names.add(locomotiveType.toString());
        }
        return names;
    }


    /**
     * Retrieves the names of all available industry types.
     *
     * @return List of Strings representing industry type names.
     */
    public List<String> getAvailableIndustryTypeNames() {
        List<String> names = new ArrayList<>();
        for (IndustryType industryType : getAvailableIndustryTypes()) {
            names.add(industryType.toString());
        }
        return names;
    }



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
     * Retrieves all available locomotive types.
     *
     * @return List of LocomotiveType objects.
     */
    public List<LocomotiveType> getLocomotiveTypes() {
        return Repositories.getInstance().getLocomotiveTypeRepository().getLocomotiveTypes();
    }


    /**
     * Reads a list of integer selections from user input, separated by whitespace.
     * Invalid inputs are ignored with an error message.
     *
     * @param prompt String prompt message shown to the user.
     * @return List of integers parsed from user input.
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

    public Map selectMap(int mapIndex){
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        return maps.get(mapIndex);
    }

    /**
     * Creates a new Scenario using the selected parameters and adds it to the scenario repository.
     */
    public boolean createScenario(ScenarioDTO dto) {
        ScenarioMapper scenarioMapper = new ScenarioMapper();
        ScenarioRepository scenarioRepo = Repositories.getInstance().getScenarioRepository();
        Scenario scenario = scenarioMapper.toScenario(dto);
        scenarioRepo.addScenario(scenario);
        return true;
    }
}
