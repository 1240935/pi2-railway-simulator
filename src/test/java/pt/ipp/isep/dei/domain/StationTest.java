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

    @BeforeEach
    void setUp() {
        stationPosition = new Position(0, 0);
        center = new StationCenter(stationPosition);
        stationType = StationType.DEPOT;  // Supondo que SMALL tem um raio definido

        // Criando cargas dummy para export/import da indústria
        IndustryType industryType = IndustryType.COAL_MINE;

        industryInsideRadius = new Industry(industryType, new Position(1, 1));
        industryOutsideRadius = new Industry(industryType, new Position(100, 100));


        // Cidade que fornece passageiros e mail e demanda goods e food
        cityInsideRadius = new City("CityInside", new Position(2, 2), Collections.emptyList());
        cityOutsideRadius = new City("CityOutside", new Position(200, 200), Collections.emptyList());

        // Criar a estação com nome, center, posição e tipo
        station = new Station("TestStation", center, stationPosition, stationType,
                Collections.emptyList(), Collections.emptyList());
    }

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

        Building building = new Building(BuildingType.LARGE_HOTEL);
        station.setBuilding(building);
        assertEquals(building, station.getBuilding());
    }

    @Test
    void testGetCargoesWithIndustriesAndCitiesInsideRadius() {
        List<Industry> industries = Arrays.asList(industryInsideRadius, industryOutsideRadius);
        List<City> cities = Arrays.asList(cityInsideRadius, cityOutsideRadius);

        CargoStatus cargoStatus = station.getCargoes(industries, cities);

        // Como apenas os dentro do raio contam, esperamos as cargas de industryInsideRadius e cityInsideRadius
        // IndustryInside exports COAL, CHEMICALS; imports FOOD
        // CityInside supplies PASSENGERS, MAIL; demands PASSENGERS, MAIL, GOODS, FOOD

        // Verificar que cargos exportados e importados da indústria foram incluídos
        assertTrue(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.COAL));
        assertTrue(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.CHEMICALS));
        assertTrue(cargoStatus.getDemanded().stream().anyMatch(c -> c.getType() == ResourceType.FOOD));

        // Verificar que cargos da cidade dentro do raio estão incluídos
        assertTrue(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.PASSENGERS));
        assertTrue(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.MAIL));
        assertTrue(cargoStatus.getDemanded().stream().anyMatch(c -> c.getType() == ResourceType.GOODS));
        assertTrue(cargoStatus.getDemanded().stream().anyMatch(c -> c.getType() == ResourceType.FOOD));

        // Garantir que cargos da indústria/cidade fora do raio não foram incluídos
        assertFalse(cargoStatus.getSupplied().stream().anyMatch(c -> c.getType() == ResourceType.URANIUM));
    }

    @Test
    void testGetSuppliesOnly() {
        List<Industry> industries = Arrays.asList(industryInsideRadius);
        List<City> cities = Arrays.asList(cityInsideRadius);

        List<Cargo> supplies = station.getSuppliesOnly(industries, cities);

        // IndustryInside exports COAL and CHEMICALS
        assertTrue(supplies.stream().anyMatch(c -> c.getType() == ResourceType.COAL));
        assertTrue(supplies.stream().anyMatch(c -> c.getType() == ResourceType.CHEMICALS));

        // CityInside supplies PASSENGERS and MAIL
        assertTrue(supplies.stream().anyMatch(c -> c.getType() == ResourceType.PASSENGERS));
        assertTrue(supplies.stream().anyMatch(c -> c.getType() == ResourceType.MAIL));
    }

    @Test
    void testToString() {
        assertEquals("TestStation", station.toString());
    }
}
