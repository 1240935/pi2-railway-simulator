package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateScenarioController {
    private Map selectedMap;
    private int selectedYear;
    private List<LocomotiveType> locomotiveTypes;
    private List<IndustryType> industryTypes;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;
    private List<IndustryChanger> changers;

    public CreateScenarioController() {
    }

    public List<Map> getAvailableMaps() {
        return Repositories.getInstance().getMapRepository().getMaps();
    }


    public List<ResourceType> getAvailableResourceType() {
        return Repositories.getInstance().getResourceTypeRepository().getAvailableResourceTypes();
    }

    public List<IndustryType> getAvailableIndustryTypes(){
        return Repositories.getInstance().getIndustryTypeRepository().getAvailableIndustryTypes();
    }

    public List<LocomotiveType> getAvailableLocomotivesTypes(){
        return Repositories.getInstance().getLocomotiveTypeRepository().getAvailableLocomotiveTypes();
    }

    public List<String> getAvailableLocomotiveTypeNames() {
        List<String> names = new ArrayList<>();
        for (LocomotiveType locomotiveType : getAvailableLocomotivesTypes()) {
            names.add(locomotiveType.toString());
        }
        return names;
    }

    public List<String> getAvailableMapNames() {
        List<String> names = new ArrayList<>();
        for (Map map : getAvailableMaps()) {
            names.add(map.getName());
        }
        return names;
    }

    public List<String> getAvailableIndustryTypeNames() {
        List<String> names = new ArrayList<>();
        for (IndustryType industryType : getIndustryTypes()) {
            names.add(industryType.toString());
        }
        return names;
    }

    public List<String> getAvailableImpor_ExportationNames() {
        List<String> names = new ArrayList<>();
        for (ResourceType resourceType : getAvailableResourceType()) {
            names.add(resourceType.toString());
        }
        return names;
    }

    public List<String> getAvailableIndustryChangerNames() {
        List<String> names = new ArrayList<>();
        for (IndustryChanger industryChanger : getAvailableIndustryChangers()) {
            names.add(industryChanger.toString());
        }
        return names;
    }



    public Map chooseMap(List<Map> maps, int choice){
        Map map = maps.get(choice-1);
        return map;
    }

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

    public List<IndustryType> getIndustryTypes() {
        return industryTypes;
    }

    public List<ResourceType> getExportation() {
        return exportation;
    }

    public List<ResourceType> getImportation() {
        return importation;
    }

    public List<IndustryChanger> getChangers() {
        return changers;
    }

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

    public void setMap(Map map) {
        this.selectedMap = map;
    }

    public void setYear(int year) {
        this.selectedYear = year;
    }

    public List<LocomotiveType> getLocomotiveTypes() {
        return Repositories.getInstance().getLocomotiveTypeRepository().getAvailableLocomotiveTypes();
    }

    public List<IndustryChanger> getAvailableIndustryChangers() {
        return Repositories.getInstance().getIndustryChangerRepository().getAvailableIndustryChanger();
    }

    public void setIndustryTypes(List<IndustryType> types) {
        this.industryTypes = types;
    }

    public void setImportation(List<ResourceType> importation) {
        this.importation = importation;
    }

    public void setExportation(List<ResourceType> exportation) {
        this.exportation = exportation;
    }

    public void setChangers(List<IndustryChanger> changers) {
        this.changers = changers;
    }


    public void setLocomotiveTypes(List<LocomotiveType> locomotiveTypes) {
        this.locomotiveTypes = locomotiveTypes;
    }

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

    public Scenario createScenario() {
        return new Scenario(selectedMap, selectedYear, locomotiveTypes, industryTypes, changers, exportation, importation);
    }
}
