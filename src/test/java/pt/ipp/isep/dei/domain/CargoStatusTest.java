package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CargoStatusTest {

    // Testa se o método getSupplied() retorna corretamente a lista de carga fornecida
    @Test
    void getSupplied_ShouldReturnCorrectSuppliedCargoList() {
        Cargo coalCargo = new Cargo(ResourceType.COAL, 100);
        Cargo grainCargo = new Cargo(ResourceType.GRAIN, 50);
        List<Cargo> supplied = Arrays.asList(coalCargo, grainCargo);

        CargoStatus cargoStatus = new CargoStatus(supplied, List.of());

        assertEquals(supplied, cargoStatus.getSupplied());
    }

    // Testa se o método getDemanded() retorna corretamente a lista de carga exigida
    @Test
    void getDemanded_ShouldReturnCorrectDemandedCargoList() {
        Cargo oilCargo = new Cargo(ResourceType.OIL, 200);
        List<Cargo> demanded = List.of(oilCargo);

        CargoStatus cargoStatus = new CargoStatus(List.of(), demanded);

        assertEquals(demanded, cargoStatus.getDemanded());
    }

    // Testa se o método toString() contém os detalhes das cargas fornecidas e exigidas
    @Test
    void toString_ShouldContainSuppliedAndDemandedCargoDetails() {
        Cargo coalCargo = new Cargo(ResourceType.COAL, 100);
        Cargo grainCargo = new Cargo(ResourceType.GRAIN, 50);
        Cargo oilCargo = new Cargo(ResourceType.OIL, 200);

        List<Cargo> supplied = Arrays.asList(coalCargo, grainCargo);
        List<Cargo> demanded = List.of(oilCargo);

        CargoStatus cargoStatus = new CargoStatus(supplied, demanded);

        String toStringResult = cargoStatus.toString();

        assertTrue(toStringResult.contains("Supplied Cargoes:"));
        assertTrue(toStringResult.contains("COAL: 100"));
        assertTrue(toStringResult.contains("GRAIN: 50"));
        assertTrue(toStringResult.contains("Demanded Cargoes:"));
        assertTrue(toStringResult.contains("OIL: 200"));
    }

    // Testa se o construtor lança exceção quando a lista de fornecidos é nula
    @Test
    void constructor_ShouldThrowException_WhenSuppliedIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new CargoStatus(null, List.of()));
    }

    // Testa se o construtor lança exceção quando a lista de exigidos é nula
    @Test
    void constructor_ShouldThrowException_WhenDemandedIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new CargoStatus(List.of(), null));
    }

    // Testa se o método toString() lida corretamente com listas vazias (sem cargas)
    @Test
    void toString_ShouldHandleEmptyListsGracefully() {
        CargoStatus cargoStatus = new CargoStatus(List.of(), List.of());

        String result = cargoStatus.toString();

        assertTrue(result.contains("No available cargo"));
    }

    // Testa se o método toString() apresenta apenas as cargas fornecidas quando a lista de exigidos está vazia
    @Test
    void toString_ShouldReportOnlySupplied_WhenDemandedIsEmpty() {
        Cargo coalCargo = new Cargo(ResourceType.COAL, 100);
        CargoStatus cargoStatus = new CargoStatus(List.of(coalCargo), List.of());

        String result = cargoStatus.toString();

        assertTrue(result.contains("Supplied Cargoes:"));
        assertTrue(result.contains("COAL"));
        assertTrue(result.contains("No cargo demanded"));
    }
}
