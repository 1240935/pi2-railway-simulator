package pt.ipp.isep.dei.domain;

public class StationCenter {
    private Position position;

    public StationCenter(Position position){
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
