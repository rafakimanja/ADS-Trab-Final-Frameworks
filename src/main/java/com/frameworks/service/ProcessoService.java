package com.frameworks.service;

import dao.ProcessoDAO;
import model.Advogado;
import model.Cliente;
import model.Processo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
public class ProcessoService {

    private EntityManagerFactory emf;

    public ProcessoService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Exception cadastraProcesso(String numero, String status, String vara, Cliente cliente, List<Advogado> advogados) {
        EntityManager em = emf.createEntityManager();
        ProcessoDAO processoDAO = new ProcessoDAO(em);
        Processo processo = new Processo();

        try{
            processo.setNumeroProcesso(numero);
            processo.setStatus(status);
            processo.setVara(vara);
            processo.setCliente(cliente);
            processo.setAdvogados(advogados);
        } catch (IllegalArgumentException e){
            return e;
        }

        try{
            em.getTransaction().begin();
            processoDAO.salvar(processo);
            em.getTransaction().commit();
            return null;
        }
        catch(Exception e){
            em.getTransaction().rollback();
            return e;
        } finally{
            em.close();
        }
    }

    public void listarProcessos() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Processo> processos = em.createQuery("FROM Processo", Processo.class).getResultList();
            for (Processo p : processos) {
                System.out.println("ID: " + p.getId() + " | Número: " + p.getNumeroProcesso()
                        + " | Cliente: " + p.getCliente().getNome()
                        + " | Advogados: " + p.getAdvogados().stream().map(Advogado::getNome).toList()
                        + " | Vara: " + p.getVara()
                        + " | Status: " + p.getStatus());
            }
        } finally {
            em.close();
        }
    }

    public Processo buscaProcessoById(Long id){
        EntityManager em = emf.createEntityManager();
        ProcessoDAO processoDAO = new ProcessoDAO(em);
        return processoDAO.buscarPorId(id);
    }

    public Exception atualizaProcesso(Processo p) {
        EntityManager em = emf.createEntityManager();
        ProcessoDAO processoDAO = new ProcessoDAO(em);
        try{
            em.getTransaction().begin();
            processoDAO.atualizar(p);
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return e;
        } finally {
            em.close();
        }
    }

    public Exception removerProcesso(Long id) {
        EntityManager em = emf.createEntityManager();
        ProcessoDAO processoDAO = new ProcessoDAO(em);

        try {
            em.getTransaction().begin();
            processoDAO.remover(id);
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
