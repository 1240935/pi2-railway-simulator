package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LocomotiveTest {

    @Test
    public void testGetId() {
        LocomotiveModel model = LocomotiveModel.BOBO;  // Pode escolher qualquer modelo válido
        int expectedId = 123;

        Locomotive locomotive = new Locomotive(expectedId, model);

        assertEquals(expectedId, locomotive.getId(), "Deve retornar o id correto");
    }

    @Test
    public void testLocomotiveModelAssignment() {
        LocomotiveModel model = LocomotiveModel.GG1;
        Locomotive locomotive = new Locomotive(1, model);
        assertNotNull(locomotive, "Locomotive deve ser criada com modelo válido");
    }
    // Testa se o getter do modelo retorna o modelo corretamente
    @Test
    public void testGetLocomotiveModel() {
        LocomotiveModel model = LocomotiveModel.MIKADO;
        Locomotive locomotive = new Locomotive(10, model);
        assertEquals(model, locomotive.getLocomotiveModel(), "Deve retornar o modelo correto");
    }
    // Testa se um ID válido é aceite
    @Test
    public void testCheckId_Valid() {
        assertTrue(Locomotive.checkId(5), "ID positivo deve ser considerado válido");
    }

    // Testa se um ID inválido é rejeitado
    @Test
    public void testCheckId_Invalid() {
        assertFalse(Locomotive.checkId(0), "ID zero deve ser considerado inválido");
        assertFalse(Locomotive.checkId(-10), "ID negativo deve ser considerado inválido");
    }
    // Testa se um modelo válido é aceite
    @Test
    public void testCheckModel_Valid() {
        LocomotiveModel model = LocomotiveModel.BOBO; // assume-se que tem custo >= 0 e tipo não nulo
        assertTrue(Locomotive.checkModel(model), "Modelo válido deve ser aceite");
    }
    // Testa se um modelo nulo é rejeitado
    @Test
    public void testCheckModel_NullModel() {
        assertFalse(Locomotive.checkModel(null), "Modelo null deve ser considerado inválido");
    }
    // Testa a representação em string do objeto Locomotive
    @Test
    public void testToStringFormat() {
        LocomotiveModel model = LocomotiveModel.BOBO;
        Locomotive locomotive = new Locomotive(99, model);
        String expected = "99 " + model.toString();
        assertEquals(expected, locomotive.toString(), "toString deve retornar 'id modelo'");
    }

}
