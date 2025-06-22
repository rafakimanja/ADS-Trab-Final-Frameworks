package dao;

import jakarta.persistence.EntityManager;
import model.Advogado;

public class AdvogadoDAO implements GenericDAO<Advogado> {
    private EntityManager em;

    public AdvogadoDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Advogado entidade) {
        em.persist(entidade);
    }

    public Advogado buscarPorId(Long id) {
        return em.find(Advogado.class, id);
    }

    public void atualizar(Advogado entidade) {
        em.merge(entidade);
    }

    public void remover(Long id) {
        Advogado entidade = em.find(Advogado.class, id);
        if (entidade != null) em.remove(entidade);
    }
}