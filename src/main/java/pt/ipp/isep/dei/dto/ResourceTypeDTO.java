package pt.ipp.isep.dei.dto;

public class ResourceTypeDTO {
    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() { // Getter for name
        return name;
    }

    public void setName(String name) { // Setter for name
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + value + ")";
    }
}