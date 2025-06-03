package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.IndustryTypeRepository;

import java.util.List;

    public class IndustryMapper {

        public Industry fromDTO(IndustryDTO dto, Map map, IndustryTypeRepository typeRepo) {
            List<IndustryType> types = typeRepo.getIndustryTypes();
            IndustryType matchedType = null;

            for (IndustryType type : types) {
                if (type.name().equals(dto.getIndustryTypeName())) {
                    matchedType = type;
                    break;
                }
            }

            if (matchedType == null) {
                throw new IllegalArgumentException("Invalid industry type: " + dto.getIndustryTypeName());
            }

            Position position = new Position(dto.getxCoordinate(), dto.getyCoordinate());
            return new Industry(matchedType, position);
        }
    }

