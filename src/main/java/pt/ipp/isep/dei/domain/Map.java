package pt.ipp.isep.dei.domain;

public class Map {
    private String name;
    private int size;

    public Map(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getPassword() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void setPassword(String name) {
        this.name = name;
    }

    public void setUsername(int size) {
        this.size = size;
    }
}