package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class responsible for managing the creation of new Scenarios.
 * It handles the selection of maps, years, locomotive types, industry types,
 * resource import/export, and industry changers, and ultimately creates a Scenario.
 */
public class CreateScenarioController {

    private Map selectedMap;
    private int selectedYear;
    private List<LocomotiveType> locomotiveTypes;
    private List<IndustryType> industryTypes;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;
    private List<IndustryChanger> changers;

    /**
     * Default constructor.
     */
    public CreateScenarioController() {
    }

    /**
     * Returns the list of available maps from the repository.
     *
     * @return List of available Map objects.
     */
    public List<Map> getAvailableMaps() {
        return Repositories.getInstance().getMapRepository().getMaps();
    }

    /**
     * Returns the list of available resource types from the repository.
     *
     * @return List of available ResourceType objects.
     */
    public List<ResourceType> getAvailableResourceType() {
        return Repositories.getInstance().getResourceTypeRepository().getAvailableResourceTypes();
    }

    /**
     * Returns the list of available industry types from the repository.
     *
     * @return List of IndustryType objects.
     */
    public List<IndustryType> getAvailableIndustryTypes(){
        return Repositories.getInstance().getIndustryTypeRepository().getIndustryTypes();
    }

    /**
     * Returns the list of available locomotive types from the repository.
     *
     * @return List of LocomotiveType objects.
     */
    public List<LocomotiveType> getAvailableLocomotivesTypes(){
        return Repositories.getInstance().getLocomotiveTypeRepository().getLocomotiveTypes();
    }

    /**
     * Returns a list of available locomotive type names as Strings.
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
     * Returns a list of available map names as Strings.
     *
     * @return List of map names.
     */
    public List<String> getAvailableMapNames() {
        List<String> names = new ArrayList<>();
        for (Map map : getAvailableMaps()) {
            names.add(map.getName());
        }
        return names;
    }

    /**
     * Returns a list of available industry type names as Strings.
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
     * Returns a list of available resource type names as Strings for import/export.
     *
     * @return List of resource type names.
     */
    public List<String> getAvailableImpor_ExportationNames() {
        List<String> names = new ArrayList<>();
        for (ResourceType resourceType : getAvailableResourceType()) {
            names.add(resourceType.toString());
        }
        return names;
    }

    /**
     * Returns a list of available industry changer names as Strings.
     *
     * @return List of industry changer names.
     */
    public List<String> getAvailableIndustryChangerNames() {
        List<String> names = new ArrayList<>();
        for (IndustryChanger industryChanger : getAvailableIndustryChangers()) {
            names.add(industryChanger.toString());
        }
        return names;
    }

    /**
     * Selects a Map from the given list based on the user's choice index.
     *
     * @param maps List of available maps.
     * @param choice 1-based index of selected map.
     * @return Selected Map object.
     */
    public Map chooseMap(List<Map> maps, int choice){
        return maps.get(choice-1);
    }

    /**
     * Selects multiple LocomotiveTypes from a list based on user choices.
     *
     * @param types List of available LocomotiveTypes.
     * @param choices List of 1-based indices selected by the user.
     * @return List of selected LocomotiveTypes.
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

    // Getters for importation, exportation, and changers

    public List<ResourceType> getExportation() {
        return exportation;
    }

    public List<ResourceType> getImportation() {
        return importation;
    }

    public List<IndustryChanger> getChangers() {
        return changers;
    }

    /**
     * Selects multiple IndustryTypes from a list based on user choices.
     *
     * @param types List of available IndustryTypes.
     * @param choices List of 1-based indices selected by the user.
     * @return List of selected IndustryTypes.
     */
    public List<IndustryType> chooseIndustryTypes(List<IndustryType> types, List<Integer> choices) {
        List<IndustryType> selected = new ArrayList<>();
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
     * Selects multiple IndustryChangers from a list based on user choices.
     *
     * @param types List of available IndustryChangers.
     * @param choices List of 1-based indices selected by the user.
     * @return List of selected IndustryChangers.
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
     * Selects multiple ResourceTypes for importation from a list based on user choices.
     *
     * @param types List of available ResourceTypes.
     * @param choices List of 1-based indices selected by the user.
     * @return List of selected ResourceTypes for importation.
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
     * Selects multiple ResourceTypes for exportation from a list based on user choices.
     *
     * @param types List of available ResourceTypes.
     * @param choices List of 1-based indices selected by the user.
     * @return List of selected ResourceTypes for exportation.
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
     * Sets the selected map for the scenario.
     *
     * @param map The Map object to set.
     */
    public void setMap(Map map) {
        this.selectedMap = map;
    }

    /**
     * Sets the selected year for the scenario.
     *
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.selectedYear = year;
    }

    /**
     * Returns the list of locomotive types for the scenario.
     *
     * @return List of LocomotiveType objects.
     */
    public List<LocomotiveType> getLocomotiveTypes() {
        return Repositories.getInstance().getLocomotiveTypeRepository().getLocomotiveTypes();
    }

    /**
     * Returns the list of available industry changers.
     *
     * @return List of IndustryChanger objects.
     */
    public List<IndustryChanger> getAvailableIndustryChangers() {
        return Repositories.getInstance().getIndustryChangerRepository().getAvailableIndustryChanger();
    }

/**
 * Sets the list of resource types for importation.
 *
 * @param importation List of ResourceType objects to import
