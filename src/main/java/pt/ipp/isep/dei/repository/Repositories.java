package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class that aggregates and manages all repository instances used in the system.
 * <p>
 * Provides centralized access to repositories for maps, scenarios, stations, lines, industries,
 * locomotives, resource types, and others.
 * Ensures only one instance exists throughout the application.
 * </p>
 */
public class Repositories {
    private final SimulatorRepository simulatorRepository = new SimulatorRepository();

    public SimulatorRepository getSimulatorRepository() {
        return simulatorRepository;
    }

    /**
     * Singleton instance of this class.
     */
    private static Repositories instance;

    /**
     * Repository for map data.
     */
    private MapRepository mapRepository;

    /**
     * Repository for scenarios.
     */
    private ScenarioRepository scenarioRepo;

    /**
     * Repository for railway lines.
     */
    private LineRepository lineRepository;

    /**
     * Repository for stations.
     */
    private StationRepository stationRepository;

    /**
     * Repository for industries.
     */
    private IndustryRepository industryRepository;

    /**
     * Repository for building types.
     */
    private BuildingTypeRepository buildingTypeRepository;

    /**
     * Repository for industry changers.
     */
    private IndustryChangerRepository industryChangerRepository;

    /**
     * Repository for industry types.
     */
    private IndustryTypeRepository industryTypeRepository;

    /**
     * Repository for locomotives.
     */
    private LocomotiveRepository locomotiveRepository;

    /**
     * Repository for locomotive types.
     */
    private LocomotiveTypeRepository locomotiveTypeRepository;

    /**
     * Repository for resource types.
     */
    private ResourceTypeRepository resourceTypeRepository;

    /**
     * Repository for cities.
     */
    private CityRepository cityRepository;

    /**
     * Repository for locomotive models.
     */
    private LocomotiveModelRepository locomotiveModelRepository;

    /**
     * Repository for cargo.
     */
    private CargoRepository cargoRepository;

    /**
     * Repository for buildings.
     */
    private BuildingRepository buildingRepository;

    /**
     * Repository for routes.
     */
    private RouteRepository routeRepository;

    private CityBlockRepository cityBlockRepository;

    private PointOfRouteRepository pointOfRouteRepository;

    private StationTypeRepository stationTypeRepository;

    private CargoModeRepository cargoModeRepository;

    /**
     * Private constructor to initialize all repositories.
     * This enforces singleton pattern by restricting instantiation to within this class.
     */
    private Repositories() {
        mapRepository = new MapRepository();
        stationRepository = new StationRepository();
        scenarioRepo = new ScenarioRepository();
        lineRepository = new LineRepository();
        industryRepository = new IndustryRepository();
        buildingTypeRepository = new BuildingTypeRepository();
        industryChangerRepository = new IndustryChangerRepository();
        industryTypeRepository = new IndustryTypeRepository();
        locomotiveRepository = new LocomotiveRepository();
        locomotiveTypeRepository = new LocomotiveTypeRepository();
        cityRepository = new CityRepository();
        resourceTypeRepository = new ResourceTypeRepository();
        locomotiveModelRepository = new LocomotiveModelRepository();
        cargoRepository = new CargoRepository();
        buildingRepository = new BuildingRepository();
        routeRepository = new RouteRepository();
        cityBlockRepository = new CityBlockRepository();
        pointOfRouteRepository = new PointOfRouteRepository();
        stationTypeRepository = new StationTypeRepository();
        cargoModeRepository = new CargoModeRepository();
    }

    /**
     * Returns the singleton instance of the repositories.
     * If the instance does not exist yet, it is created.
     *
     * @return the singleton {@link Repositories} instance
     */
    public static Repositories getInstance() {
        if (instance == null) {
            instance = new Repositories();
        }
        return instance;
    }

    /**
     * Constructor used for initialization from station and line files.
     * Loads stations and lines from the given files during instantiation.
     * This constructor is private to enforce singleton pattern.
     *
     * @param stationFile path to the file containing station data
     * @param lineFile    path to the file containing line data
     */
    private Repositories(String stationFile, String lineFile) {
        stationRepository = new StationRepository();
        List<Station> stations = stationRepository.loadStations(stationFile);
        lineRepository = new LineRepository();
        lineRepository.loadLines(lineFile, stations);
    }

    /**
     * Returns the singleton instance, initializing it with station and line files if not already created.
     *
     * @param stationFile path to the station data file
     * @param lineFile    path to the line data file
     * @return the singleton {@link Repositories} instance
     */
    public static Repositories getInstance(String stationFile, String lineFile) {
        if (instance == null) {
            instance = new Repositories(stationFile, lineFile);
        }
        return instance;
    }

    /**
     * Returns the repository managing resource types.
     *
     * @return the {@link ResourceTypeRepository} instance
     */
    public ResourceTypeRepository getResourceTypeRepository() {
        return resourceTypeRepository;
    }

    /**
     * Returns the repository managing stations.
     *
     * @return the {@link StationRepository} instance
     */
    public StationRepository getStationRepository() {
        return stationRepository;
    }

    /**
     * Returns the repository managing lines.
     *
     * @return the {@link LineRepository} instance
     */
    public LineRepository getLineRepository() {
        return lineRepository;
    }

    /**
     * Returns the repository managing maps.
     *
     * @return the {@link MapRepository} instance
     */
    public MapRepository getMapRepository() {
        return mapRepository;
    }

    /**
     * Returns the repository managing scenarios.
     *
     * @return the {@link ScenarioRepository} instance
     */
    public ScenarioRepository getScenarioRepository() {
        return scenarioRepo;
    }

    /**
     * Returns the repository managing industries.
     *
     * @return the {@link IndustryRepository} instance
     */
    public IndustryRepository getIndustryRepository() {
        return industryRepository;
    }

    /**
     * Returns the repository managing industry types.
     *
     * @return the {@link IndustryTypeRepository} instance
     */
    public IndustryTypeRepository getIndustryTypeRepository() {
        return industryTypeRepository;
    }

    /**
     * Returns the repository managing building types.
     *
     * @return the {@link BuildingTypeRepository} instance
     */
    public BuildingTypeRepository getBuildingTypeRepository() {
        return buildingTypeRepository;
    }

    /**
     * Returns the repository managing industry changers.
     *
     * @return the {@link IndustryChangerRepository} instance
     */
    public IndustryChangerRepository getIndustryChangerRepository() {
        return industryChangerRepository;
    }

    /**
     * Returns the repository managing locomotives.
     *
     * @return the {@link LocomotiveRepository} instance
     */
    public LocomotiveRepository getLocomotiveRepository() {
        return locomotiveRepository;
    }

    /**
     * Returns the repository managing locomotive types.
     *
     * @return the {@link LocomotiveTypeRepository} instance
     */
    public LocomotiveTypeRepository getLocomotiveTypeRepository() {
        return locomotiveTypeRepository;
    }


    /**
     * Returns the repository managing cities.
     *
     * @return the {@link CityRepository} instance
     */
    public CityRepository getCityRepository() {
        return cityRepository;
    }

    /**
     * Returns the repository managing locomotive models.
     *
     * @return the {@link LocomotiveModelRepository} instance
     */
    public LocomotiveModelRepository getLocomotiveModelRepository() {
        return locomotiveModelRepository;
    }

    /**
     * Returns the repository managing cargo.
     *
     * @return the {@link CargoRepository} instance
     */
    public CargoRepository getCargoRepository() {
        return cargoRepository;
    }

    /**
     * Returns the repository managing buildings.
     *
     * @return the {@link BuildingRepository} instance
     */
    public BuildingRepository getBuildingRepository() {
        return buildingRepository;
    }

    /**
     * Returns the repository managing routes.
     *
     * @return the {@link RouteRepository} instance
     */
    /**
     * Obtém o repositório de rotas.
     *
     * @return instância do RouteRepository
     */
    public RouteRepository getRouteRepository() {
        return routeRepository;
    }

    /**
     * Obtém o repositório de blocos de cidade.
     *
     * @return instância do CityBlockRepository
     */
    public CityBlockRepository getCityBlockRepository() {
        return cityBlockRepository;
    }

    /**
     * Obtém o repositório de pontos de rota.
     *
     * @return instância do PointOfRouteRepository
     */
    public PointOfRouteRepository getPointOfRouteRepository() {
        return pointOfRouteRepository;
    }

    /**
     * Obtém o repositório de tipos de estação.
     *
     * @return instância do StationTypeRepository
     */
    public StationTypeRepository getStationTypeRepository() {
        return stationTypeRepository;
    }

    /**
     * Obtém o repositório de modos de carga.
     *
     * @return instância do CargoModeRepository
     */
    public CargoModeRepository getCargoModeRepository() {
        return cargoModeRepository;
    }
}

