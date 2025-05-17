package pt.ipp.isep.dei.domain;

public class Cargo {

    private int quantity;
    private boolean status;  // True para supply , False para demand
    private CargoType type;

    public Cargo(CargoType type, int quantity, boolean status) {
        this.type = type;
        this.quantity = quantity;
        this.status = status;
    }
    public CargoType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isSupply() {
        return status;
    }
}
