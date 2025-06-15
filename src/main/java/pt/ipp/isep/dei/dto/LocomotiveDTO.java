package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.LocomotiveModel;

/**
 * Data Transfer Object (DTO) representing a locomotive.
 * Contains the locomotive model information.
 */
public class LocomotiveDTO {

    /**
     * The model of the locomotive.
     */
    private LocomotiveModel locomotiveModel;

    /**
     * Default constructor.
     */
    public LocomotiveDTO() {}

    /**
     * Constructs a {@code LocomotiveDTO} with the specified locomotive model.
     *
     * @param locomotiveModel the locomotive model to set
     */
    public LocomotiveDTO(LocomotiveModel locomotiveModel) {
        this.locomotiveModel = locomotiveModel;
    }

    /**
     * Returns the locomotive model.
     *
     * @return the locomotive model
     */
    public LocomotiveModel getLocomotiveModel() {
        return locomotiveModel;
    }

    /**
     * Sets the locomotive model.
     *
     * @param locomotiveModel the locomotive model to set
     */
    public void setLocomotiveModel(LocomotiveModel locomotiveModel) {
        this.locomotiveModel = locomotiveModel;
    }
}
