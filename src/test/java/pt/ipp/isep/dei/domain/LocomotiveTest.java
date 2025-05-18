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

        // Como a classe Locomotive não tem getter para o modelo,
        // aqui apenas testamos a criação sem exceção.
        // Se quiser, podemos adicionar o getter na classe Locomotive.
        assertNotNull(locomotive, "Locomotive deve ser criada com modelo válido");
    }
}
