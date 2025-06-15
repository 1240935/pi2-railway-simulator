package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.repository.LocomotiveRepository;

/**
 * Mapper class responsible for converting a {@link LocomotiveDTO}
 * into a {@link Locomotive} domain object.
 */
public class LocomotiveMapper {

    /**
     * Converts a {@link LocomotiveDTO} and an ID into a {@link Locomotive} domain object.
     *
     * This method delegates the creation of the {@code Locomotive} to the {@link LocomotiveRepository}.
     *
     * @param locomotiveDTO the {@code LocomotiveDTO} to convert
     * @param id the identifier to assign to the created {@code Locomotive}
     * @return the created {@code Locomotive} domain object
     */
    public Locomotive toLocomotive(LocomotiveDTO locomotiveDTO, int id) {
        return LocomotiveRepository.createLocomotive(locomotiveDTO.getLocomotiveModel(), id);
    }
}
