package dao;

import jakarta.persistence.EntityManager;
import model.Processo;

public class ProcessoDAO implements GenericDAO<Processo> {
    private EntityManager em;

    public ProcessoDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Processo entidade) {
        em.persist(entidade);
    }

    public Processo buscarPorId(Long id) {
        return em.find(Processo.class, id);
    }

    public void atualizar(Processo entidade) {
        em.merge(entidade);
    }

    public void remover(Long id) {
        Processo entidade = em.find(Processo.class, id);
        if (entidade != null) em.remove(entidade);
    }
}