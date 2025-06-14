package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.repository.LocomotiveRepository;

public class LocomotiveMapper {
        public Locomotive toLocomotive(LocomotiveDTO locomotiveDTO, int id){
            return LocomotiveRepository.createLocomotive(locomotiveDTO.getLocomotiveModel(),id);
        }
}
