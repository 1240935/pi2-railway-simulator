package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.LocomotiveModel;

import java.util.ArrayList;

/**
 * Repository class responsible for storing and managing {@link Locomotive} objects.
 * Provides methods to add locomotives, retrieve all locomotives, and generate the next unique ID.
 */
public class LocomotiveRepository {

    /** Internal list that holds the stored locomotives. */
    private final ArrayList<Locomotive> locomotives = new ArrayList<>();

    /**
     * Adds a {@link Locomotive} to the repository.
     *
     * @param locomotive the {@link Locomotive} to be added
     */
    public void addLocomotive(Locomotive locomotive) {
        locomotives.add(locomotive);
    }

    /**
     * Returns a new list containing all locomotives stored in the repository.
     * Changes to the returned list do not affect the internal storage.
     *
     * @return a new {@link ArrayList} containing all locomotives
     */
    public ArrayList<Locomotive> getAll() {
        return new ArrayList<>(locomotives);
    }

    /**
     * Calculates and returns the next available unique ID for a new locomotive.
     * IDs start at 1 and increment based on the highest existing ID.
     *
     * @return the next unique locomotive ID
     */
    public int nextID() {
        if (locomotives.isEmpty()) {
            return 1;
        }
        int maxId = 0;
        for (Locomotive l : locomotives) {
            if (l.getId() > maxId) {
                maxId = l.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Creates a Locomotive instance if the ID and model are valid.
     *
     * @param locomotiveModel the model of the locomotive
     * @param id              the locomotive's ID
     * @return a new Locomotive instance or null if parameters are invalid
     */
    public static Locomotive createLocomotive(LocomotiveModel locomotiveModel, int id) {
        if (!Locomotive.checkId(id)) {
            System.out.println("Invalid id.");
            return null;
        }

        if (!Locomotive.checkModel(locomotiveModel)) {
            System.out.println("Invalid locomotive model.");
            return null;
        }

        Locomotive locomotive = new Locomotive(id, locomotiveModel);
        System.out.println("Locomotive bought successfully!");
        return locomotive;
    }
}
