package pt.ipp.isep.dei.domain;

/**
 * Represents a locomotive with a unique identifier and an associated locomotive model.
 */
public class Locomotive {

    /** The unique identifier of the locomotive. */
    private final int id;

    /** The model of the locomotive. */
    private final LocomotiveModel locomotiveModel;

    /**
     * Constructs a Locomotive with the specified ID and model.
     *
     * @param id               the unique identifier of the locomotive
     * @param locomotiveModel  the model of the locomotive
     */
    public Locomotive(int id, LocomotiveModel locomotiveModel){
        this.id = id;
        this.locomotiveModel = locomotiveModel;
    }

    /**
     * Returns the unique identifier of the locomotive.
     *
     * @return the locomotive ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of the locomotive in the format:
     * "[model] [id]".
     *
     * @return a string with the locomotive's model and ID
     */
    @Override
    public String toString() {
        return id + " " + locomotiveModel;
    }
}
