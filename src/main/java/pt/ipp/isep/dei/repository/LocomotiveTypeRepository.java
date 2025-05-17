package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.LocomotiveType;

import java.util.Arrays;
import java.util.List;

public class LocomotiveTypeRepository {

    public List<LocomotiveType> getLocomotiveTypes() {
        return Arrays.asList(LocomotiveType.values());
    }
}
