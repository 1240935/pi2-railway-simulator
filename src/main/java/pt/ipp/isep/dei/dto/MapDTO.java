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

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public int getScale() {
        return scale;
    }
}
