package pt.ipp.isep.dei.repository;
import pt.ipp.isep.dei.domain.BuildingType;
import java.util.Arrays;
import java.util.ArrayList;

    public class BuildingTypeRepository {
        public ArrayList<BuildingType> getAvailableBuildingTypes() {
            return new ArrayList<>(Arrays.asList(BuildingType.values()));
        }
    }

