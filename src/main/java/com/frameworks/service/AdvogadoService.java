package com.frameworks.service;

import dao.AdvogadoDAO;
import model.Advogado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Scanner;

public class AdvogadoService {

    private EntityManagerFactory emf;

    public AdvogadoService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Exception cadastraAdvogado(String nome, String oab, String especialidade) {
        Advogado advogado = new Advogado();
        EntityManager em = emf.createEntityManager();

        try{
            advogado.setNome(nome);
            advogado.setNumeroOAB(oab);
            advogado.setEspecialidade(especialidade);
        } catch (IllegalArgumentException e){
            return e;
        }

        try {
            em.getTransaction().begin();
            AdvogadoDAO dao = new AdvogadoDAO(em);
            dao.salvar(advogado);
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return e;
        } finally {
            em.close();
        }
    }

    public void listarAdvogados() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Advogado> advogados = em.createQuery("FROM Advogado", Advogado.class).getResultList();
            advogados.forEach(a -> {
                System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome() + " | OAB: " + a.getNumeroOAB() + " | Especialidade: " + a.getEspecialidade());
            });
        } finally {
            em.close();
        }
    }

    public Advogado buscaAdvogadoById(Long id){
        EntityManager em = emf.createEntityManager();
        AdvogadoDAO dao = new AdvogadoDAO(em);
        return dao.buscarPorId(id);
    }

    public Exception atualizaAdvogado(Advogado a) {
        EntityManager em = emf.createEntityManager();
        AdvogadoDAO dao = new AdvogadoDAO(em);

        try {
            em.getTransaction().begin();
            dao.atualizar(a);
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return e;
        } finally {
            em.close();
        }
    }

    public Exception removeAdvogado(Long id) {
        EntityManager em = emf.createEntityManager();
        AdvogadoDAO dao = new AdvogadoDAO(em);

        try {
            em.getTransaction().begin();
            dao.remover(id);
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return e;
        } finally {
            em.close();
        }
    }
}

