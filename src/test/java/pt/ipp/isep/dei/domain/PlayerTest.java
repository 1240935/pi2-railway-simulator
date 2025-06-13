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

    // Testa se o nome de utilizador é retornado corretamente
    @Test
    public void testGetUsername() {
        assertEquals("user123", player.getUsername());
    }

    // Testa se a password é retornada corretamente
    @Test
    public void testGetPassword() {
        assertEquals("pass123", player.getPassword());
    }

    // Testa se o orçamento inicial é retornado corretamente
    @Test
    public void testGetBudget() {
        assertEquals(100, player.getBudget());
    }

    // Testa se pode pagar um custo quando o orçamento é suficiente
    @Test
    public void testCanAfford_WhenEnoughBudget_ReturnsTrue() {
        assertTrue(player.canAfford(50));
        assertTrue(player.canAfford(100));
    }

    // Testa se não pode pagar um custo maior do que o orçamento
    @Test
    public void testCanAfford_WhenNotEnoughBudget_ReturnsFalse() {
        assertFalse(player.canAfford(150));
    }

    // Testa dedução de um valor menor que o orçamento
    @Test
    public void testDeductBudget_WhenAmountIsLessThanBudget() {
        player.deductBudget(30);
        assertEquals(70, player.getBudget());
    }

    // Testa dedução de um valor igual ao orçamento
    @Test
    public void testDeductBudget_WhenAmountIsEqualToBudget() {
        player.deductBudget(100);
        assertEquals(0, player.getBudget());
    }

    // Testa que não há alteração quando o valor a deduzir é maior que o orçamento
    @Test
    public void testDeductBudget_WhenAmountIsGreaterThanBudget_NoChange() {
        player.deductBudget(150);
        assertEquals(100, player.getBudget());
    }

    // Testa se adicionar um valor ao orçamento funciona corretamente
    @Test
    public void testAddBudget() {
        player.addBudget(50);
        assertEquals(150, player.getBudget());
    }

    // Testa se deduzir 0 do orçamento não altera o valor
    @Test
    public void testDeductBudget_ZeroAmount() {
        player.deductBudget(0);
        assertEquals(100, player.getBudget(), "Deduzir zero não deve alterar o orçamento");
    }

    // Testa se adicionar 0 ao orçamento não altera o valor
    @Test
    public void testAddBudget_ZeroAmount() {
        player.addBudget(0);
        assertEquals(100, player.getBudget(), "Adicionar zero não deve alterar o orçamento");
    }

    // Testa se adicionar um valor negativo subtrai do orçamento (comportamento incorreto)
    @Test
    public void testAddBudget_NegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> player.addBudget(-20));
    }
}
