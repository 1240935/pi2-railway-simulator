package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CargoStatusTest {

    @Test
    void getSupplied_ShouldReturnCorrectSuppliedCargoList() {
        Cargo coalCargo = new Cargo(ResourceType.COAL, 100);
        Cargo grainCargo = new Cargo(ResourceType.GRAIN, 50);
        List<Cargo> supplied = Arrays.asList(coalCargo, grainCargo);

        CargoStatus cargoStatus = new CargoStatus(supplied, List.of());

        assertEquals(supplied, cargoStatus.getSupplied());
    }

    @Test
    void getDemanded_ShouldReturnCorrectDemandedCargoList() {
        Cargo oilCargo = new Cargo(ResourceType.OIL, 200);
        List<Cargo> demanded = List.of(oilCargo);

        CargoStatus cargoStatus = new CargoStatus(List.of(), demanded);

        assertEquals(demanded, cargoStatus.getDemanded());
    }

    @Test
    void toString_ShouldContainSuppliedAndDemandedCargoDetails() {
        Cargo coalCargo = new Cargo(ResourceType.COAL, 100);
        Cargo grainCargo = new Cargo(ResourceType.GRAIN, 50);
        Cargo oilCargo = new Cargo(ResourceType.OIL, 200);

        List<Cargo> supplied = Arrays.asList(coalCargo, grainCargo);
        List<Cargo> demanded = List.of(oilCargo);

        CargoStatus cargoStatus = new CargoStatus(supplied, demanded);

        String toStringResult = cargoStatus.toString();

        // Verifica se a string contém as descrições esperadas
        assertTrue(toStringResult.contains("Supplied Cargoes:"));
        assertTrue(toStringResult.contains("COAL: 100"));
        assertTrue(toStringResult.contains("GRAIN: 50"));
        assertTrue(toStringResult.contains("Demanded Cargoes:"));
        assertTrue(toStringResult.contains("OIL: 200"));
    }
}
