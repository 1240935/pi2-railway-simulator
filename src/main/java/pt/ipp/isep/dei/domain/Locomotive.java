package pt.ipp.isep.dei.domain;

public class Locomotive {

    private final int id;
    private final LocomotiveModel locomotiveModel;

    public Locomotive(int id, LocomotiveModel locomotiveModel){
        this.id = id;
        this.locomotiveModel = locomotiveModel;
    }

    public int getId() {
        return id;
    }
}
