package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Station;

import java.util.List;

public class Repositories {
    // Cria o repositório unico a ser usado
    private static Repositories instance;
    // Objeto que vai ser referênciado nos .getMapRepository() <-(concede acesso ao repositorio)
    private MapRepository mapRepository;
    private ScenarioRepository scenarioRepo;
    private LineRepository lineRepository;
    private StationRepository stationRepository;
    private IndustryRepository industryRepository;
    private BuildingTypeRepository buildingTypeRepository;
    private IndustryChangerRepository industryChangerRepository;
    private IndustryTypeRepository industryTypeRepository;
    private LocomotiveRepository locomotiveRepository;
    private LocomotiveTypeRepository locomotiveTypeRepository;

    // Inicialisa os Repositories, nada fora desta classe pode instanciar repositórios
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

    }

    // Cria um repositório caso não exista, se existir, retorna o existente, assim só existe 1
    public static Repositories getInstance() {
        if (instance == null) {
            instance = new Repositories();
        }
        return instance;
    }

    private Repositories(String stationFile, String lineFile) {
        stationRepository = new StationRepository();
        List<Station> stations = stationRepository.loadStations(stationFile);
        lineRepository = new LineRepository();
        lineRepository.loadLines(lineFile, stations);
    }

    public static Repositories getInstance(String stationFile, String lineFile) {
        if (instance == null) {
            instance = new Repositories(stationFile, lineFile);
        }
        return instance;
    }

    public StationRepository getStationRepository() {
        return stationRepository;
    }

    public LineRepository getLineRepository() {
        return lineRepository;
    }
    // Getter do repositório de mapas
    public MapRepository getMapRepository() {
        return mapRepository;
    }

    // Getter do repositório de cenarios
    public ScenarioRepository getScenarioRepository() {
        return scenarioRepo;
    }

    public IndustryRepository getIndustryRepository(){
        return  industryRepository;
    }

    public IndustryTypeRepository getIndustryTypeRepository() {
        return industryTypeRepository;
    }

    public BuildingTypeRepository getBuildingTypeRepository() {
        return buildingTypeRepository;
    }

    public IndustryChangerRepository getIndustryChangerRepository() {
        return industryChangerRepository;
    }

    public LocomotiveRepository getLocomotiveRepository() {
        return locomotiveRepository;
    }

    public LocomotiveTypeRepository getLocomotiveTypeRepository() {
        return locomotiveTypeRepository;
    }

    public ScenarioRepository getScenarioRepo() {
        return scenarioRepo;
    }



}