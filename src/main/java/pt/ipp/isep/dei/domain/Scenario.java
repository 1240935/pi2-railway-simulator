package pt.ipp.isep.dei.domain;

import java.util.List;

/**
 * Represents a railway scenario within the system.
 * A scenario is characterized by a map, a year, and several types
 * including locomotive types, industry types, industry changers,
 * and resource types for importation and exportation.
 */
public class Scenario {
    private Map map;
    private int year;
    private List<LocomotiveType> locomotiveType;
    private List<IndustryType> industryType;
    private List<IndustryChanger> industryChanger;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;

    /**
     * Constructs a new {@code Scenario} with the specified parameters.
     *
     * @param map the map associated with the scenario
     * @param year the year in which the scenario takes place
     * @param locomotiveType the list of locomotive types allowed in the scenario
     * @param industryType the list of industry types included in the scenario
     * @param industryChanger the list of industry changers in the scenario
     * @param importation the list of resource types imported in the scenario
     * @param exportation the list of resource types exported in the scenario
     */
    public Scenario(Map map, int year, List<LocomotiveType> locomotiveType, List<IndustryType> industryType,
                    List<IndustryChanger> industryChanger, List<ResourceType> importation, List<ResourceType> exportation) {
        this.map = map;
        this.year = year;
        this.locomotiveType = locomotiveType;
        this.industryType = industryType;
        this.industryChanger = industryChanger;
        this.importation = importation;
        this.exportation = exportation;
    }

    /**
     * Returns the list of resource types imported in this scenario.
     *
     * @return the list of imported resource types
     */
    public List<ResourceType> getImportation() {
        return importation;
    }

    /**
     * Returns the list of resource types exported in this scenario.
     *
     * @return the list of exported resource types
     */
    public List<ResourceType> getExportation() {
        return exportation;
    }

    /**
     * Returns the map associated with this scenario.
     *
     * @return the scenario's map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the year in which this scenario takes place.
     *
     * @return the scenario year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the list of locomotive types allowed in this scenario.
     *
     * @return the list of allowed locomotive types
     */
    public List<LocomotiveType> getLocomotiveTypes() {
        return locomotiveType;
    }
}
