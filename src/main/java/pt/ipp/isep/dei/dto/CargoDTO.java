package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.ResourceType;

public class CargoDTO {
    private ResourceType type;
    private int quantity;

    public CargoDTO(){}

    public CargoDTO(ResourceType type, int quantity){
        this.quantity = quantity;
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return ("Type: " + type + "\nQuantity: " + quantity);
    }
}
