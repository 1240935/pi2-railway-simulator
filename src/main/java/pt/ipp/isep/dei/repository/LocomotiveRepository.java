package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.ArrayList;

public class LocomotiveRepository {
    private final ArrayList<Locomotive> locomotives = new ArrayList<>();

    public void addLocomotive(Locomotive locomotive) { locomotives.add(locomotive); }

    public ArrayList<Locomotive> getAll() {
        return new ArrayList<>(locomotives);
    }

    public int nextID() {
        if (locomotives.isEmpty()) {
            return 1;
        }
        int maxId = 0;
        for (Locomotive l : locomotives) {
            if (l.getId() > maxId) {
                maxId = l.getId();
            }
        }
        return maxId + 1;
    }
}

