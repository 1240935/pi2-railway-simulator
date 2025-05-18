package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositoriesTest {

    @Test
    void testSingletonInstance_isNotNull() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance);
    }

    @Test
    void testSingletonInstance_returnsSameInstance() {
        Repositories firstInstance = Repositories.getInstance();
        Repositories secondInstance = Repositories.getInstance();

        assertSame(firstInstance, secondInstance, "Should return the same singleton instance");
    }

    @Test
    void testGetters_returnNonNullRepositories() {
        Repositories repositories = Repositories.getInstance();

        assertNotNull(repositories.getMapRepository());
        assertNotNull(repositories.getStationRepository());
        assertNotNull(repositories.getLineRepository());
        assertNotNull(repositories.getScenarioRepository());
        assertNotNull(repositories.getIndustryRepository());
        assertNotNull(repositories.getIndustryTypeRepository());
        assertNotNull(repositories.getBuildingTypeRepository());
        assertNotNull(repositories.getIndustryChangerRepository());
        assertNotNull(repositories.getLocomotiveRepository());
        assertNotNull(repositories.getLocomotiveTypeRepository());
        assertNotNull(repositories.getCityRepository());
        assertNotNull(repositories.getResourceTypeRepository());
        assertNotNull(repositories.getLocomotiveModelRepository());
        assertNotNull(repositories.getCargoRepository());
        assertNotNull(repositories.getBuildingRepository());
        assertNotNull(repositories.getRouteRepository());
    }

    @Test
    void testGetInstance_withFiles_returnsSingleton() {
        String fakeStationFile = "fakeStations.csv";
        String fakeLineFile = "fakeLines.csv";
        Repositories instanceWithFiles = Repositories.getInstance(fakeStationFile, fakeLineFile);
        assertNotNull(instanceWithFiles);
        Repositories instanceDefault = Repositories.getInstance();

        assertSame(instanceWithFiles, instanceDefault);
    }
}
