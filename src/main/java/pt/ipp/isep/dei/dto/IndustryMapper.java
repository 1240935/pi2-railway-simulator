package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.IndustryRepository;
import pt.ipp.isep.dei.repository.IndustryTypeRepository;

import java.util.List;

    public class IndustryMapper {

        public Industry toIndustry(IndustryDTO industryDTO){
            return IndustryRepository.createIndustry(
                    industryDTO.getIndustryType(),
                    industryDTO.getxCoordinate(),
                    industryDTO.getyCoordinate(),
                    industryDTO.getSize()
            );
        }
    }

