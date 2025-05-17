package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry;

import java.util.ArrayList;
import java.util.List;

public class IndustryRepository {
    private final ArrayList<Industry> industries = new ArrayList<>();

    public void addIndustry(Industry industry) {
        industries.add(industry);
    }

    public List<Industry> getIndustries() {
        return industries;
    }

}
