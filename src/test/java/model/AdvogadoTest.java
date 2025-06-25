package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AdvogadoTest {

    private Advogado adv;

    @BeforeEach
    public void setup() {
        adv = new Advogado();
        adv.setNome("Maria Silva");
        adv.setNumeroOAB("SP123456");
        adv.setEspecialidade("Direito Penal");
    }

    @Test
    public void criaAdvogado() {
        Assertions.assertEquals("SP123456", adv.getNumeroOAB());
    }

    @Test
    @DisplayName("função para dar erro ao alterar o numero da OAB para um invalido")
    public void numOABInvalido() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            adv.setNumeroOAB("12345");
        });
        Assertions.assertEquals("Número OAB inválido. Deve seguir o padrão UF999999.", ex.getMessage());
    }

    @Test
    @DisplayName("Tete para dar erro ao submeter nome vazio")
    public void nomeVazio() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            adv.setNome(" ");
        });
        Assertions.assertEquals("Nome não pode ser vazio", ex.getMessage());
    }
}

