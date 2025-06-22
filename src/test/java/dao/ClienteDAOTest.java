package dao;

import jakarta.persistence.EntityManager;
import model.Cliente;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class ClienteDAOTest {

    @Test
    public void testSalvarCliente() {
        EntityManager em = mock(EntityManager.class);
        ClienteDAO dao = new ClienteDAO(em);

        Cliente entidade = new Cliente();
        dao.salvar(entidade);

        verify(em).persist(entidade);
    }
}