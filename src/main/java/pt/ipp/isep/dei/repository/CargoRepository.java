package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Cargo;
import pt.ipp.isep.dei.domain.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Cargo objects.
 */
public class CargoRepository {

    private final List<Cargo> cargoes = new ArrayList<>();

    /**
     * Adds a Cargo to the repository if it is not null and not already present.
     *
     * @param cargo the Cargo object to addScenario
     */
    public void addCargo(Cargo cargo) {
        if (cargo != null && !cargoes.contains(cargo)) {
            cargoes.add(cargo);
        }
    }

    /**
     * Returns a list of all Cargo objects currently stored.
     * A defensive copy is returned to prevent external modifications.
     *
     * @return a new list containing all Cargo objects
     */
    public List<Cargo> getCargoes() {
        return new ArrayList<>(cargoes);
    }

    /**
     * Finds and returns the Cargo associated with the specified ResourceType.
     *
     * @param resourceType the ResourceType to find the Cargo for
     * @return the corresponding Cargo if found; null otherwise
     */
    public Cargo getCargoForResourceType(ResourceType resourceType) {
        for (Cargo cargo : cargoes) {
            if (cargo.getType().equals(resourceType)) {
                return cargo;
            }
        }
        return null;
    }
}
