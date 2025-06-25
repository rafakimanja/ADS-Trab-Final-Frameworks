package bd;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class JPATest {

    @Test
    @DisplayName("testando e integração do JPA")
    public void testEntityManagerFactoryCreation(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        Assertions.assertNotNull(em);

        em.close();
        emf.close();
    }
}
