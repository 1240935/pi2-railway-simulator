package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.IndustryType;

import java.util.ArrayList;
import java.util.Arrays;

public class IndustryTypeRepository {
    public ArrayList<IndustryType> getAvailableIndustryTypes() {
        return new ArrayList<>(Arrays.asList(IndustryType.values()));
    }
}
