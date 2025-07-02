package integracao;

import dao.ClienteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Cliente;
import org.junit.jupiter.api.*;

public class ClienteDAOIntegracaoTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private ClienteDAO clienteDAO;

    @BeforeAll
    public static void setupClass() {
        emf = Persistence.createEntityManagerFactory("default"); // usa o nome correto da unidade
    }

    @AfterAll
    public static void tearDownClass() {
        if (emf != null) emf.close();
    }

    @BeforeEach
    public void setup() {
        em = emf.createEntityManager();
        clienteDAO = new ClienteDAO(em);
    }

    @AfterEach
    public void tearDown() {
        if (em != null) em.close();
    }

    @Test
    public void deveSalvarClienteNoBanco() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente de Teste");
        cliente.setCpfCnpj("12345678901");
        cliente.setTelefone(999999999L);

        em.getTransaction().begin();
        clienteDAO.salvar(cliente);
        em.getTransaction().commit();

        Assertions.assertNotNull(cliente.getId());
    }

    @Test
    public void deveBuscarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Buscado");
        cliente.setCpfCnpj("98765432100");
        cliente.setTelefone(888888888L);

        em.getTransaction().begin();
        clienteDAO.salvar(cliente);
        em.getTransaction().commit();

        Cliente encontrado = clienteDAO.buscarPorId(cliente.getId());

        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("98765432100", encontrado.getCpfCnpj());
    }
}
