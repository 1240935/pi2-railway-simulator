package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.CityBlock;
import pt.ipp.isep.dei.repository.CityBlockRepository;

import java.util.List;

/**
 * Mapper class responsible for converting a {@link CityBlockDTO}
 * into a list of {@link CityBlock} domain objects.
 */
public class CityBlockMapper {

    /**
     * Converts a {@link CityBlockDTO} to a list of {@link CityBlock} objects.
     * <p>
     * If the DTO specifies randomization, blocks are generated with random positions
     * using the given x and y coordinates and block count.
     * Otherwise, blocks are created using the explicitly defined positions in the DTO.
     * </p>
     *
     * @param dto the {@link CityBlockDTO} to convert
     * @return a list of generated or created {@link CityBlock} objects
     */
    public List<CityBlock> toCityBlock(CityBlockDTO dto) {
        if (dto.isRandomize()) {
            return CityBlockRepository.generateBlocks(
                    dto.getxCoordinate(),
                    dto.getyCoordinate(),
                    dto.getBlocks()
            );
        } else {
            return CityBlockRepository.createBlocks(
                    dto.getPositions(),
                    dto.getBlocks()
            );
        }
    }
}
