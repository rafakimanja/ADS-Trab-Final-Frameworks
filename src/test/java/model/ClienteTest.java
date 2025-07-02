package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        cliente = new Cliente();
        cliente.setCpfCnpj("123.456.789-09");
        cliente.setNome("Empresa X");
        cliente.setTelefone(123456789L);
    }

    @Test
    public void clienteValido() {
        Assertions.assertEquals("123.456.789-09", cliente.getCpfCnpj());
    }

    @Test
    @DisplayName("Teste de erro para CPF/CNPJ invalido")
    public void cpfInvalido() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cliente.setCpfCnpj("1234");
        });
        Assertions.assertEquals("CPF/CNPJ inválido. Deve conter 11 (CPF) ou 14 (CNPJ) dígitos numéricos", ex.getMessage());
    }

    @Test
    @DisplayName("Teste de erro para nome vazio")
    public void nomeVazio() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cliente.setNome("");
        });
        Assertions.assertEquals("Nome não pode ser vazio", ex.getMessage());
    }
}
