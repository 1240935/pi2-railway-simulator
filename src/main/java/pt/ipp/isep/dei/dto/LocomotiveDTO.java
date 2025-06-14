package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.LocomotiveModel;

public class LocomotiveDTO {
    private LocomotiveModel locomotiveModel;

    public LocomotiveDTO(){}

    public LocomotiveDTO(LocomotiveModel locomotiveModel){
        this.locomotiveModel = locomotiveModel;
    }

    public LocomotiveModel getLocomotiveModel() {
        return locomotiveModel;
    }

    public void setLocomotiveModel(LocomotiveModel locomotiveModel) {
        this.locomotiveModel = locomotiveModel;
    }
}
