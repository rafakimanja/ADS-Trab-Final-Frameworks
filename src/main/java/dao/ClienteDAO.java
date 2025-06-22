package dao;

import jakarta.persistence.EntityManager;
import model.Cliente;

public class ClienteDAO implements GenericDAO<Cliente> {
    private EntityManager em;

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Cliente entidade) {
        em.persist(entidade);
    }

    public Cliente buscarPorId(Long id) {
        return em.find(Cliente.class, id);
    }

    public void atualizar(Cliente entidade) {
        em.merge(entidade);
    }

    public void remover(Long id) {
        Cliente entidade = em.find(Cliente.class, id);
        if (entidade != null) em.remove(entidade);
    }
}