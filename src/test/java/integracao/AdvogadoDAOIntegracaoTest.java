package integracao;

import dao.AdvogadoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Advogado;
import org.junit.jupiter.api.*;

public class AdvogadoDAOIntegracaoTest {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private AdvogadoDAO advogadoDAO;

    @BeforeAll
    public static void setupClass() {
        emf = Persistence.createEntityManagerFactory("default");
    }

    @AfterAll
    public static void tearDownClass() {
        if (emf != null) emf.close();
    }

    @BeforeEach
    public void setup() {
        em = emf.createEntityManager();
        advogadoDAO = new AdvogadoDAO(em);
    }

    @AfterEach
    public void tearDown() {
        if (em != null) em.close();
    }

    @Test
    public void deveSalvarAdvogadoNoBanco() {
        Advogado adv = new Advogado();
        adv.setNome("Ana Paula");
        adv.setNumeroOAB("MG123456");
        adv.setEspecialidade("Trabalhista");

        em.getTransaction().begin();
        advogadoDAO.salvar(adv);
        em.getTransaction().commit();

        Assertions.assertNotNull(adv.getId());
    }

    @Test
    public void deveBuscarAdvogadoPorId() {
        Advogado adv = new Advogado();
        adv.setNome("Carlos Oliveira");
        adv.setNumeroOAB("RJ654321");
        adv.setEspecialidade("Cível");

        em.getTransaction().begin();
        advogadoDAO.salvar(adv);
        em.getTransaction().commit();

        Advogado encontrado = advogadoDAO.buscarPorId(adv.getId());
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Carlos Oliveira", encontrado.getNome());
    }
}
