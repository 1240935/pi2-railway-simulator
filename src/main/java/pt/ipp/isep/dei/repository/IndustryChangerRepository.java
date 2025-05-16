package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.IndustryChanger;
import java.util.ArrayList;
import java.util.Arrays;

public class IndustryChangerRepository {
    public ArrayList<IndustryChanger> getAvailableIndustryChanger() {
        return new ArrayList<>(Arrays.asList(IndustryChanger.values()));
    }
}
