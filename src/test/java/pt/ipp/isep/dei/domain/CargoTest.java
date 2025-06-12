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
    @Test
    void constructor_ShouldThrowException_WhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Cargo(ResourceType.COAL, -10));
    }
    @Test
    void setQuantity_ShouldUpdateQuantity() {
        Cargo cargo = new Cargo(ResourceType.COAL, 50);
        cargo.setQuantity(120);
        assertEquals(120, cargo.getQuantity());
    }
    @Test
    void setQuantity_ShouldThrowException_WhenNegative() {
        Cargo cargo = new Cargo(ResourceType.COAL, 50);
        assertThrows(IllegalArgumentException.class, () -> cargo.setQuantity(-5));
    }
    @Test
    void toString_ShouldReturnExpectedFormat() {
        Cargo cargo = new Cargo(ResourceType.COFFEE, 100);
        assertEquals("Cargo:COFFEE", cargo.toString());
    }
}
