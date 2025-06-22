package dao;

import jakarta.persistence.EntityManager;
import model.Advogado;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class AdvogadoDAOTest {

    @Test
    public void testSalvarAdvogado() {
        EntityManager em = mock(EntityManager.class);
        AdvogadoDAO dao = new AdvogadoDAO(em);

        Advogado entidade = new Advogado();
        dao.salvar(entidade);

        verify(em).persist(entidade);
    }
}