package pt.ipp.isep.dei.controller;
import java.util.List;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;

public class CreateIndustryController {
    // coisas que o controller seleciona
    private Map selectedMap;
    private IndustryType selectedType;
    private ResourceType selectedResource;

    //construtor, vazio para já
    public CreateIndustryController() {}

    //mostra os mapas do repositório
    public List<Map> getAvailableMaps() {
        return Repositories.getInstance().getMapRepository().getMaps();
    }

    // seleciona o mapa
    public void setMap(Map map) {
        this.selectedMap = map;
    }

    //cria a posição
    public Position createPosition(int x, int y) {
        return new Position(x, y);
    }
/*
    //confirma q a posição está vazia
    public boolean checkCoordinates(Position pos) {
        return selectedMap != null && selectedMap.checkCoordinates(pos);
    }
*/
    // mostra os tipos disponiveis de industria da enum (ainda n implementado direito)
    public IndustryType[] getAvailableIndustriesTypes() {
        return IndustryType.values();
    }

    // seleciona o tipo
    public void setType(IndustryType type) {
        this.selectedType = type;
    }

    // mostra os tipos disponiveis de recursos da enum (ainda n implementado direito)
    public ResourceType[] getAvailableResourceTypes() {
        return ResourceType.values();
    }

    // seleciona o tipo de recurso
    public void setResourceType(ResourceType resourceType) {
        this.selectedResource = resourceType;
    }

    // cria a industria com os valores selecionados
    public Industry createIndustry(Position pos) {
        return new Industry(selectedType, pos, selectedResource);
    }
}
