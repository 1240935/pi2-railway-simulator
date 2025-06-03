package pt.ipp.isep.dei.dto;

public class MapDTO {
    private int size;
    private String name;
    private int scale;

    public MapDTO(int size, String name, int scale) {
        this.size = size;
        this.name = name;
        this.scale = scale;
    }

    public MapDTO(){
        this.size = 1;
        this.name = "Default";
        this.scale = 1;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public int getScale() {
        return scale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
