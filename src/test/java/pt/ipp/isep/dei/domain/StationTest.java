package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    private Station station;
    private StationCenter center;
    private Position stationPosition;
    private StationType stationType;

    private Industry industryInsideRadius;
    private Industry industryOutsideRadius;

    private City cityInsideRadius;
    private City cityOutsideRadius;

    // Configura os objetos usados nos testes, incluindo indústrias e cidades dentro e fora do raio da estação
    @BeforeEach
    void setUp() {
        stationPosition = new Position(0, 0);
        center = new StationCenter(stationPosition);
        stationType = StationType.DEPOT; // Assume DEPOT tem raio definido, ex: 10

        IndustryType industryType = IndustryType.COAL_MINE;

        industryInsideRadius = new Industry(industryType, new Position(1, 1));
        industryOutsideRadius = new Industry(industryType, new Position(100, 100));

        cityInsideRadius = new City("CityInside", new Position(2, 2), Collections.emptyList());
        cityOutsideRadius = new City("CityOutside", new Position(200, 200), Collections.emptyList());

        station = new Station("TestStation", center, stationPosition, stationType,
                Collections.emptyList(), Collections.emptyList());
    }

    // Testa getters e setters da classe Station, incluindo nomes, posição, tipo, buildings, demandas e entregas
    @Test
    void testGettersAndSetters() {
        station.setName("NewName");
        assertEquals("NewName", station.getName());

        StationCenter newCenter = new StationCenter(new Position(5, 5));
        station.setCenter(newCenter);
        assertEquals(newCenter, station.getCenter());

        Position newPosition = new Position(3, 3);
        station.setPosition(newPosition);
        assertEquals(newPosition, station.getPosition());

        station.setType(StationType.STATION);
        assertEquals(StationType.STATION, station.getType());

        // Testa adição e atualização da lista de buildings
        Building building1 = new Building(BuildingType.LARGE_HOTEL);
        station.addBuilding(building1);
        assertTrue(station.getBuildings().contains(building1));

        List<Building> newBuildings = List.of(new Building(BuildingType.SMALL_HOTEL));
        station.setBuildings(newBuildings);
        assertEquals(newBuildings, station.getBuildings());

        // Testa demandas e suprimentos
        List<Cargo> demands = List.of(new Cargo(ResourceType.COAL, 5));
        List<Cargo> supplies = List.of(new Cargo(ResourceType.FOOD, 3));
        station.setDemands(demands);
        station.setSupplies(supplies);

        assertEquals(demands, station.getDemands());
        assertEquals(supplies, station.getSupplies());

        // Testa nível de demanda
        station.setDemandLevel(7);
        assertEquals(7, station.getDemandLevel());

        // Testa contagem, incremento e reset de entregas
        assertEquals(0, station.getDeliveriesCount());
        station.incrementDeliveries();
        assertEquals(1, station.getDeliveriesCount());
        station.resetDeliveries();
        assertEquals(0, station.getDeliveriesCount());
    }

    // Testa obtenção dos cargos (supplies e demands) considerando indústrias e cidades dentro e fora do raio da estação
    @Test
    void testGetCargoesWithIndustriesAndCitiesInsideRadius() {
        List<Industry> industries = Arrays.asList(industryInsideRadius, industryOutsideRadius);
        List<City> cities = Arrays.asList(cityInsideRadius, cityOutsideRadius);

        CargoStatus cargoStatus = station.getCargoes(industries, cities);

        // Verifica cargos fornecidos e demandados de indústrias dentro do raio
        assertTrue(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.COAL));
        assertTrue(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.CHEMICALS)); // se aplicável
        assertTrue(cargoStatus.getDemanded().stream().anyMatch(c -> c.getType() == ResourceType.FOOD));

        // Verifica cargos fornecidos e demandados de cidades dentro do raio
        assertTrue(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.PASSENGERS));
        assertTrue(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.MAIL));
        assertTrue(cargoStatus.getDemanded().stream().anyMatch(c -> c.getType() == ResourceType.GOODS));
        assertTrue(cargoStatus.getDemanded().stream().anyMatch(c -> c.getType() == ResourceType.FOOD));

        // Confirma que cargos fora do raio não aparecem
        assertFalse(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.URANIUM));
        assertFalse(cargoStatus.getDemanded().stream().anyMatch(c -> c.getType() == ResourceType.URANIUM));
    }

    // Testa obtenção somente dos suprimentos (supplies) das indústrias e cidades dentro do raio da estação
    @Test
    void testGetSuppliesOnly() {
        List<Industry> industries = Arrays.asList(industryInsideRadius);
        List<City> cities = Arrays.asList(cityInsideRadius);

        List<Cargo> supplies = station.getSuppliesOnly(industries, cities);

        // Verifica que os cargos fornecidos estão presentes
        assertTrue(supplies.stream().anyMatch(c -> c.getType() == ResourceType.COAL));
        assertTrue(supplies.stream().anyMatch(c -> c.getType() == ResourceType.CHEMICALS));
        assertTrue(supplies.stream().anyMatch(c -> c.getType() == ResourceType.PASSENGERS));
        assertTrue(supplies.stream().anyMatch(c -> c.getType() == ResourceType.MAIL));

        // Verifica que cargos demandados não estão incluídos
        assertFalse(supplies.stream().anyMatch(c -> c.getType() == ResourceType.FOOD));
    }

    // Testa o método toString que deve retornar o nome da estação
    @Test
    void testToString() {
        assertEquals("TestStation", station.toString());
    }
}
