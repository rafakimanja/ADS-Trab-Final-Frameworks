package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ProcessoTest {

    private Processo processo;

    @BeforeEach
    public void setup() {
        processo = new Processo();
        processo.setNumeroProcesso("ABC1234/2023-1");
        processo.setVara("Vara Cível");
        processo.setCliente(new Cliente()); // cliente básico só para não ser nulo
    }

    @Test
    public void processoValido() {
        Assertions.assertEquals("ABC1234/2023-1", processo.getNumeroProcesso());
    }

    @Test
    @DisplayName("Teste de erro para numero de processo invalido")
    public void processoInvalido() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            processo.setNumeroProcesso("ABC@123");
        });
        Assertions.assertEquals("Número do processo contém caracteres inválidos", ex.getMessage());
    }

    @Test
    @DisplayName("Teste de erro para cliente nulo")
    public void clienteNulo() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            processo.setCliente(null);
        });
        Assertions.assertEquals("Cliente não pode ser nulo", ex.getMessage());
    }
}
