package pt.ipp.isep.dei.domain;

public class Cargo {

    private ResourceType type;
    private int quantity;

    public Cargo(ResourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public ResourceType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }
}
