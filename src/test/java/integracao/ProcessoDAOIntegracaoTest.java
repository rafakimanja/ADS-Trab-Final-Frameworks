package integracao;

import dao.ClienteDAO;
import dao.ProcessoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Cliente;
import model.Processo;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProcessoDAOIntegracaoTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private ProcessoDAO processoDAO;
    private ClienteDAO clienteDAO;

    @BeforeAll
    public void init() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createQuery("DELETE FROM Processo").executeUpdate();
        em.createQuery("DELETE FROM Cliente").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterAll
    public void tearDownClass() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @BeforeEach
    public void setup() {
        // Cria um novo EntityManager para cada teste
        em = emf.createEntityManager();
        processoDAO = new ProcessoDAO(em);
        clienteDAO = new ClienteDAO(em);
    }

    @AfterEach
    public void tearDown() {
        if (em != null) em.close();
    }

    @Test
    public void deveSalvarProcessoNoBanco() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Processo");
        cliente.setCpfCnpj("11122233344");
        cliente.setTelefone(123456789L);

        Processo processo = new Processo();
        processo.setNumeroProcesso("PROC-2025/001");
        processo.setVara("Vara Cível");
        processo.setStatus("Ativo");
        processo.setCliente(cliente);

        em.getTransaction().begin();
        clienteDAO.salvar(cliente);
        processoDAO.salvar(processo);
        em.getTransaction().commit();

        Assertions.assertNotNull(processo.getId());
    }

    @Test
    public void deveBuscarProcessoPorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Buscado");
        cliente.setCpfCnpj("55566677788");
        cliente.setTelefone(987654321L);

        Processo processo = new Processo();
        processo.setNumeroProcesso("PROC-2025/002");
        processo.setVara("Vara Trabalhista");
        processo.setStatus("Ativo");
        processo.setCliente(cliente);

        em.getTransaction().begin();
        clienteDAO.salvar(cliente);
        processoDAO.salvar(processo);
        em.getTransaction().commit();

        Processo encontrado = processoDAO.buscarPorId(processo.getId());

        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("PROC-2025/002", encontrado.getNumeroProcesso());
    }
}
