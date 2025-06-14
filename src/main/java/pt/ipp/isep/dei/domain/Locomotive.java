package pt.ipp.isep.dei.domain;
import java.io.Serializable;

/**
 * Represents a locomotive with a unique identifier and an associated locomotive model.
 */
public class Locomotive implements Serializable {

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

    public LocomotiveModel getLocomotiveModel() {
        return locomotiveModel;
    }

    /**
     * Validates whether the given ID is a valid locomotive ID.
     * A valid ID is a positive integer.
     *
     * @param id the ID to check
     * @return true if the ID is valid, false otherwise
     */
    public static boolean checkId(int id) {
        return id > 0;
    }

    /**
     * Validates whether the given locomotive model is acceptable.
     * A valid model is non-null, has non-negative cost, and a non-null type.
     *
     * @param model the locomotive model to validate
     * @return true if the model is valid, false otherwise
     */
    public static boolean checkModel(LocomotiveModel model) {
        return model != null &&
                model.getCost() >= 0 &&
                model.getType() != null;
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
