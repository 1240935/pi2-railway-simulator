package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.ArrayList;

public class LocomotiveRepository {
    private final ArrayList<Locomotive> locomotives = new ArrayList<>();

    public void add(Locomotive locomotive) {
        if (locomotive != null && !locomotives.contains(locomotive)) {
            locomotives.add(locomotive);
        }
    }

    public ArrayList<Locomotive> getAll() {
        return new ArrayList<>(locomotives);
    }
}

