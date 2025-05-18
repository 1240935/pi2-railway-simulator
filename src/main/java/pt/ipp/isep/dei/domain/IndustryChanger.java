package pt.ipp.isep.dei.domain;

/**
 * Represents different types of industry changers,
 * each with an associated effect value.
 */
public enum IndustryChanger {
    // Types of industry changers with their effect values
    WAR(1),
    VACCINATION_CAMPAIGN(1),
    SCHOOLING_PROGRAM(1);

    private int effect;

    /**
     * Constructor to assign effect value to the industry changer type.
     *
     * @param effect the effect value associated with the industry changer
     */
    IndustryChanger(int effect){
        this.effect = effect;
    }
}
