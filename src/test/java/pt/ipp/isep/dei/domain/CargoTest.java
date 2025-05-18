package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CargoTest {

    @Test
    void getType_ShouldReturnCorrectResourceType() {
        ResourceType type = ResourceType.COAL;
        Cargo cargo = new Cargo(type, 100);
        assertEquals(type, cargo.getType());
    }

    @Test
    void getQuantity_ShouldReturnCorrectQuantity() {
        int quantity = 100;
        Cargo cargo = new Cargo(ResourceType.COFFEE, quantity);
        assertEquals(quantity, cargo.getQuantity());
    }
}
