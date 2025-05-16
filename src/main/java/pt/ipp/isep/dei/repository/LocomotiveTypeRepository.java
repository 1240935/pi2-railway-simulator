package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.LocomotiveType;

import java.util.ArrayList;
import java.util.Arrays;

public class LocomotiveTypeRepository {
    public ArrayList<LocomotiveType> getAvailableLocomotiveTypes() {
        return new ArrayList<>(Arrays.asList(LocomotiveType.values()));
    }
}
