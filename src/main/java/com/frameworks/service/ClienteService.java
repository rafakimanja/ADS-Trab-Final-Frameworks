package com.frameworks.service;

import dao.ClienteDAO;
import model.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Scanner;

public class ClienteService {

    private EntityManagerFactory emf;

    public ClienteService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Exception cadastraCliente(String nome, String cpfCnpj, Long telefone) {
        EntityManager em = emf.createEntityManager();
        Cliente cliente = new Cliente();

        try{
            cliente.setNome(nome);
            cliente.setCpfCnpj(cpfCnpj);
            cliente.setTelefone(telefone);
        } catch (IllegalArgumentException e){
            return e;
        }

        try {
            em.getTransaction().begin();
            ClienteDAO dao = new ClienteDAO(em);
            dao.salvar(cliente);
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return e;
        } finally {
            em.close();
        }
    }

    public void listaClientes() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Cliente> clientes = em.createQuery("FROM Cliente", Cliente.class).getResultList();
            clientes.forEach(c -> {
                System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() +
                        " | CPF/CNPJ: " + c.getCpfCnpj() + " | Telefone: " + c.getTelefone());
            });
        } finally {
            em.close();
        }
    }

    public Cliente buscaClienteById(Long id){
        EntityManager em = emf.createEntityManager();
        ClienteDAO dao = new ClienteDAO(em);
        return dao.buscarPorId(id);
    }

    public Exception atualizaCliente(Cliente c) {
        EntityManager em = emf.createEntityManager();
        ClienteDAO dao = new ClienteDAO(em);

        try {
            em.getTransaction().begin();
            dao.atualizar(c);
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return e;
        } finally {
            em.close();
        }
    }

    public Exception removeCliente(Long id) {
        EntityManager em = emf.createEntityManager();
        ClienteDAO dao = new ClienteDAO(em);

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
