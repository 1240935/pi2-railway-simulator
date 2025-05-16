package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry;

import java.util.ArrayList;

public class IndustryRepository {
    private final ArrayList<Industry> industries = new ArrayList<>();

    public void add(Industry industry) {
        if (industry != null && !industries.contains(industry)) {
            industries.add(industry);
        }
    }

    public ArrayList<Industry> getAll() {
        return new ArrayList<>(industries);
    }
}
