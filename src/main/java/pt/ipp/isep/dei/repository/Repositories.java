package pt.ipp.isep.dei.repository;

public class Repositories {
    // Cria o repositório unico a ser usado
    private static Repositories instance;
    // Objeto que vai ser referênciado nos .getMapRepository() <-(concede acesso ao repositorio)
    private MapRepository mapRepository;

    // Inicialisa o mapRepository, nada fora desta classe pode instanciar repositórios
    private Repositories() {
        mapRepository = new MapRepository();
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
}