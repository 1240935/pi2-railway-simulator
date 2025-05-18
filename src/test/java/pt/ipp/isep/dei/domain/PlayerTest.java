package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("user123", "pass123", 100);
    }

    @Test
    public void testGetUsername() {
        assertEquals("user123", player.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("pass123", player.getPassword());
    }

    @Test
    public void testGetBudget() {
        assertEquals(100, player.getBudget());
    }

    @Test
    public void testCanAfford_WhenEnoughBudget_ReturnsTrue() {
        assertTrue(player.canAfford(50));
        assertTrue(player.canAfford(100));
    }

    @Test
    public void testCanAfford_WhenNotEnoughBudget_ReturnsFalse() {
        assertFalse(player.canAfford(150));
    }

    @Test
    public void testDeductBudget_WhenAmountIsLessThanBudget() {
        player.deductBudget(30);
        assertEquals(70, player.getBudget());
    }

    @Test
    public void testDeductBudget_WhenAmountIsEqualToBudget() {
        player.deductBudget(100);
        assertEquals(0, player.getBudget());
    }

    @Test
    public void testDeductBudget_WhenAmountIsGreaterThanBudget_NoChange() {
        player.deductBudget(150);
        assertEquals(100, player.getBudget());
    }
}
