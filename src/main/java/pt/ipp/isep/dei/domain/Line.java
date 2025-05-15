package pt.ipp.isep.dei.domain;

public class Line {
    private Station start;
    private Station end;
    private boolean isElectrified;
    private double distance;  // new field

    public Line(Station start, Station end, boolean isElectrified, double distance) {
        this.start = start;
        this.end = end;
        this.isElectrified = isElectrified;
        this.distance = distance;
    }

    // Getters
    public Station getStart() { return start; }
    public Station getEnd() { return end; }
    public boolean isElectrified() { return isElectrified; }
    public double getDistance() { return distance; }
}


