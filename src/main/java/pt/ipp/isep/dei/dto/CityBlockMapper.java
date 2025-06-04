package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.CityBlock;
import pt.ipp.isep.dei.repository.CityBlockRepository;
import java.util.List;

public class CityBlockMapper {
    public List<CityBlock> toCityBlock(CityBlockDTO dto) {
        if(dto.isRandomize()){
            return CityBlockRepository.generateBlocks(
                    dto.getxCoordinate(),
                    dto.getyCoordinate(),
                    dto.getBlocks()
                    );
        }
        else {
            return CityBlockRepository.createBlocks(
                    dto.getPositions(),
                    dto.getBlocks()
            );
        }
    }
}