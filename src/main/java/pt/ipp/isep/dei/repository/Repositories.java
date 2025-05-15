package pt.ipp.isep.dei.repository;

public class Repositories {
    // Cria o repositório unico a ser usado
    private static Repositories instance;
    // Objeto que vai ser referênciado nos .getMapRepository() <-(concede acesso ao repositorio)
    private MapRepository mapRepository;
    private StationRepository stationRepo;
    private ScenarioRepository scenarioRepo;

    // Inicialisa os Repositories, nada fora desta classe pode instanciar repositórios
    private Repositories() {
        mapRepository = new MapRepository();
        stationRepo = new StationRepository();
        scenarioRepo = new ScenarioRepository();
    }

    // Cria um repositório caso não exista, se existir, retorna o existente, assim só existe 1
    public static Repositories getInstance() {
        if (instance == null) {
            instance = new Repositories();
        }
        return instance;
    }

    // Getter do repositório de mapas
    public MapRepository getMapRepository() {
        return mapRepository;
    }

    // Getter do repositório de cenarios
    public ScenarioRepository getScenarioRepository() {
        return scenarioRepo;
    }

    // Getter do repositório de stations
    public StationRepository getStationRepository() {
        return stationRepo;
    }
}