package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map;

import static org.junit.jupiter.api.Assertions.*;

class RepositoriesTest {

    // Verifica se a instância singleton não é nula
    @Test
    void testSingletonInstance_isNotNull() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance, "The singleton instance should not be null");
    }

    // Verifica se o padrão singleton retorna sempre a mesma instância
    @Test
    void testSingletonInstance_returnsSameInstance() {
        Repositories firstInstance = Repositories.getInstance();
        Repositories secondInstance = Repositories.getInstance();

        assertSame(firstInstance, secondInstance, "Multiple calls to getInstance should return the same singleton instance");
    }

    // Verifica se todos os getters de repositórios retornam instâncias não nulas
    @Test
    void testGetters_returnNonNullRepositories() {
        Repositories repositories = Repositories.getInstance();

        assertNotNull(repositories.getMapRepository(), "MapRepository should not be null");
        assertNotNull(repositories.getStationRepository(), "StationRepository should not be null");
        assertNotNull(repositories.getLineRepository(), "LineRepository should not be null");
        assertNotNull(repositories.getScenarioRepository(), "ScenarioRepository should not be null");
        assertNotNull(repositories.getIndustryRepository(), "IndustryRepository should not be null");
        assertNotNull(repositories.getIndustryTypeRepository(), "IndustryTypeRepository should not be null");
        assertNotNull(repositories.getBuildingTypeRepository(), "BuildingTypeRepository should not be null");
        assertNotNull(repositories.getIndustryChangerRepository(), "IndustryChangerRepository should not be null");
        assertNotNull(repositories.getLocomotiveRepository(), "LocomotiveRepository should not be null");
        assertNotNull(repositories.getLocomotiveTypeRepository(), "LocomotiveTypeRepository should not be null");
        assertNotNull(repositories.getCityRepository(), "CityRepository should not be null");
        assertNotNull(repositories.getResourceTypeRepository(), "ResourceTypeRepository should not be null");
        assertNotNull(repositories.getLocomotiveModelRepository(), "LocomotiveModelRepository should not be null");
        assertNotNull(repositories.getCargoRepository(), "CargoRepository should not be null");
        assertNotNull(repositories.getBuildingRepository(), "BuildingRepository should not be null");
        assertNotNull(repositories.getRouteRepository(), "RouteRepository should not be null");
    }

    // Verifica se a instância criada com ficheiros também segue o padrão singleton
    @Test
    void testGetInstance_withFiles_returnsSingleton() {
        String fakeStationFile = "fakeStations.csv";
        String fakeLineFile = "fakeLines.csv";
        Repositories instanceWithFiles = Repositories.getInstance(fakeStationFile, fakeLineFile);
        assertNotNull(instanceWithFiles, "Instance created with files should not be null");

        Repositories instanceDefault = Repositories.getInstance();
        assertSame(instanceWithFiles, instanceDefault, "Instance created with files should be the same as default singleton instance");
    }

    // Verifica se o repositório de linhas é inicializado corretamente com ficheiros
    @Test
    void testGetInstance_withFiles_initializesLineRepository() {
        Repositories repos = Repositories.getInstance("stations.csv", "lines.csv");

        assertNotNull(repos.getLineRepository().getLines(), "LineRepository should be initialized with data from file");
    }

    // Verifica se os repositórios são objetos independentes
    @Test
    void testRepositoriesAreIndependentInstances() {
        Repositories repos = Repositories.getInstance();

        assertNotSame(repos.getLineRepository(), repos.getStationRepository(), "Each repository should be an independent instance");
    }

    // Verifica se um mapa adicionado persiste na instância singleton
    @Test
    void testMapRepository_addMap_persistsInSingleton() {
        Repositories repos = Repositories.getInstance();
        MapRepository mapRepo = repos.getMapRepository();

        mapRepo.addMap(new Map(20, "MapTest", 10));

        assertFalse(mapRepo.getMaps().isEmpty(), "Added map should persist in the singleton repository");
    }
}
