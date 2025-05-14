package pt.ipp.isep.dei.domain;

public class Cargo {

    private int quantity;
    private boolean status;
    private CargoType type;

    Cargo(int quantity, boolean status, CargoType type){
        this.quantity = quantity;
        this.status = status;
        this.type = type;
    }
}
