package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Cargo;
import pt.ipp.isep.dei.domain.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class CargoRepository {

    private final List<Cargo> cargoes = new ArrayList<>();

    /**
     * Adds a Cargo to the repository.
     *
     * @param cargo The Cargo to add.
     */
    public void addCargo(Cargo cargo) {
        if (cargo != null && !cargoes.contains(cargo)) {
            cargoes.add(cargo);
        }
    }

    /**
     * Retrieves all Cargoes in the repository.
     *
     * @return List of all Cargo objects.
     */
    public List<Cargo> getCargoes() {
        return new ArrayList<>(cargoes); // Defensive copy
    }

    /**
     * Finds a Cargo corresponding to a given ResourceType.
     *
     * @param resourceType The ResourceType to look for.
     * @return Corresponding Cargo if found; null otherwise.
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

