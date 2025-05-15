package pt.ipp.isep.dei.repository;
import pt.ipp.isep.dei.domain.BuildingType;
import java.util.Arrays;
import java.util.List;

    public class BuildingTypeRepository {
        public List<BuildingType> getAvailableBuildingTypes() {
            return Arrays.asList(BuildingType.values());
        }
    }

