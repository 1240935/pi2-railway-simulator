package pt.ipp.isep.dei.repository;
;
import pt.ipp.isep.dei.domain.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;

public class ResourceTypeRepository {
        public ArrayList<ResourceType> getAvailableResourceTypes() {
            return new ArrayList<>(Arrays.asList(ResourceType.values()));
        }
    }
