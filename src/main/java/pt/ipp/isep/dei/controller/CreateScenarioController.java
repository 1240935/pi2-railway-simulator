package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class responsible for creating a new Scenario.
 * It handles the selection and configuration of maps, years,
 * locomotive types, industry types, resource import/export, and industry changers.
 */
public class CreateScenarioController {
    private Map selectedMap;
    private int selectedYear;
    private List<LocomotiveType> locomotiveTypes;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;
    private List<IndustryChanger> changers;

    /**
     * Default constructor for CreateScenarioController.
     */
    public CreateScenarioController() {
    }

    /**
     * Retrieves all available maps from the repository.
     *
     * @return List of available Map objects.
     */
    public List<Map> getAvailableMaps() {
        return Repositories.getInstance().getMapRepository().getMaps();
    }

    /**
     * Retrieves all available resource types from the repository.
     *
     * @return List of available ResourceType objects.
     */
    public List<ResourceType> getAvailableResourceType() {
        return Repositories.getInstance().getResourceTypeRepository().getAvailableResourceTypes();
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
     * Retrieves the names of all available maps.
     *
     * @return List of Strings representing map names.
     */
    public List<String> getAvailableMapNames() {
        List<String> names = new ArrayList<>();
        for (Map map : getAvailableMaps()) {
            names.add(map.getName());
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

    /**
     * Retrieves the names of all available resource types for import/export.
     *
     * @return List of Strings representing resource type names.
     */
    public List<String> getAvailableImpor_ExportationNames() {
        List<String> names = new ArrayList<>();
        for (ResourceType resourceType : getAvailableResourceType()) {
            names.add(resourceType.toString());
        }
        return names;
    }

    /**
     * Retrieves the names of all available industry changers.
     *
     * @return List of Strings representing industry changer names.
     */
    public List<String> getAvailableIndustryChangerNames() {
        List<String> names = new ArrayList<>();
        for (IndustryChanger industryChanger : getAvailableIndustryChangers()) {
            names.add(industryChanger.toString());
        }
        return names;
    }

    /**
     * Selects a Map from a given list by its choice index.
     *
     * @param maps   List of Map objects to choose from.
     * @param choice 1-based index of the selected map.
     * @return Selected Map object.
     */
    public Map chooseMap(List<Map> maps, int choice){
        return maps.get(choice-1);
    }

    /**
     * Selects multiple LocomotiveType objects based on a list of choice indices.
     *
     * @param types   List of LocomotiveType objects to choose from.
     * @param choices List of 1-based indices representing selected locomotive types.
     * @return List of selected LocomotiveType objects.
     */
    public List<LocomotiveType> chooseLocomotiveTypes(List<LocomotiveType> types, List<Integer> choices) {
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
     * Gets the list of selected resource types for exportation.
     *
     * @return List of ResourceType objects for export.
     */
    public List<ResourceType> getExportation() {
        return exportation;
    }

    /**
     * Gets the list of selected resource types for importation.
     *
     * @return List of ResourceType objects for import.
     */
    public List<ResourceType> getImportation() {
        return importation;
    }

    /**
     * Gets the list of selected industry changers.
     *
     * @return List of IndustryChanger objects.
     */
    public List<IndustryChanger> getChangers() {
        return changers;
    }

    /**
     * Selects multiple IndustryChanger objects based on a list of choice indices.
     *
     * @param types   List of IndustryChanger objects to choose from.
     * @param choices List of 1-based indices representing selected changers.
     * @return List of selected IndustryChanger objects.
     */
    public List<IndustryChanger> chooseIndustryChanger(List<IndustryChanger> types, List<Integer> choices) {
        List<IndustryChanger> selected = new ArrayList<>();
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
     * Selects multiple ResourceType objects for importation based on choice indices.
     *
     * @param types   List of ResourceType objects to choose from.
     * @param choices List of 1-based indices representing selected resources.
     * @return List of selected ResourceType objects for import.
     */
    public List<ResourceType> chooseImportation(List<ResourceType> types, List<Integer> choices) {
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
     * Selects multiple ResourceType objects for exportation based on choice indices.
     *
     * @param types   List of ResourceType objects to choose from.
     * @param choices List of 1-based indices representing selected resources.
     * @return List of selected ResourceType objects for export.
     */
    public List<ResourceType> chooseExportation(List<ResourceType> types, List<Integer> choices) {
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
     * Sets the currently selected map.
     *
     * @param map Map object to be selected.
     */
    public void setMap(Map map) {
        this.selectedMap = map;
    }

    /**
     * Sets the year for the scenario.
     *
     * @param year Integer representing the selected year.
     */
    public void setYear(int year) {
        this.selectedYear = year;
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
     * Retrieves all available industry changers.
     *
     * @return List of IndustryChanger objects.
     */
    public List<IndustryChanger> getAvailableIndustryChangers() {
        return Repositories.getInstance().getIndustryChangerRepository().getAvailableIndustryChanger();
    }

    /**
     * Sets the resource types selected for importation.
     *
     * @param importation List of ResourceType objects for import.
     */
    public void setImportation(List<ResourceType> importation) {
        this.importation = importation;
    }

    /**
     * Sets the resource types selected for exportation.
     *
     * @param exportation List of ResourceType objects for export.
     */
    public void setExportation(List<ResourceType> exportation) {
        this.exportation = exportation;
    }

    /**
     * Sets the list of industry changers.
     *
     * @param changers List of IndustryChanger objects.
     */
    public void setChangers(List<IndustryChanger> changers) {
        this.changers = changers;
    }

    /**
     * Sets the list of locomotive types for the scenario.
     *
     * @param locomotiveTypes List of LocomotiveType objects.
     */
    public void setLocomotiveTypes(List<LocomotiveType> locomotiveTypes) {
        this.locomotiveTypes = locomotiveTypes;
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

    /**
     * Creates a new Scenario using the selected parameters and adds it to the scenario repository.
     */
    public void createScenario() {
        Scenario scenario = new Scenario(selectedMap, selectedYear, locomotiveTypes, changers, exportation, importation);
        Repositories.getInstance().getScenarioRepository().addScenario(scenario);
    }
}
