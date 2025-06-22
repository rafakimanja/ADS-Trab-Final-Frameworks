package dao;

import jakarta.persistence.EntityManager;
import model.Processo;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class ProcessoDAOTest {

    @Test
    public void testSalvarProcesso() {
        EntityManager em = mock(EntityManager.class);
        ProcessoDAO dao = new ProcessoDAO(em);

        Processo entidade = new Processo();
        dao.salvar(entidade);

        verify(em).persist(entidade);
    }
}