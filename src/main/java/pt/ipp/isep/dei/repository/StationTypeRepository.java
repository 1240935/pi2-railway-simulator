package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.StationType;

import java.util.Arrays;
import java.util.List;

/**
 * Repositório responsável por fornecer os tipos de estação disponíveis.
 */
public class StationTypeRepository {

    /**
     * Obtém a lista de todos os tipos de estação definidos.
     *
     * @return Lista imutável contendo todos os valores do enum StationType.
     */
    public List<StationType> getStationTypes() {
        return Arrays.asList(StationType.values());
    }
}
