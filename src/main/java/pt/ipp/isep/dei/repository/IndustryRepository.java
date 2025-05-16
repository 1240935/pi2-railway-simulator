package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry;

import java.util.ArrayList;
import java.util.List;

public class IndustryRepository {

    private final List<Industry> industries = new ArrayList<>();

    public void add(Industry industry) {
        if (industry != null && !industries.contains(industry)) {
            industries.add(industry);
        }
    }

    public List<Industry> getAll() {
        return new ArrayList<>(industries);
    }
}
