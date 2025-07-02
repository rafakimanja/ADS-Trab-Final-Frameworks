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

    public void menu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrar(scanner);
                case 2 -> listar();
                case 3 -> atualizar(scanner);
                case 4 -> remover(scanner);
            }
        } while (opcao != 0);
    }

    public void cadastrar(Scanner sc) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF/CNPJ: ");
        String cpfCnpj = sc.nextLine();
        System.out.print("Telefone (apenas números): ");
        Integer telefone = sc.nextInt(); sc.nextLine();

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpfCnpj(cpfCnpj);
        cliente.setTelefone(telefone);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ClienteDAO dao = new ClienteDAO(em);
            dao.salvar(cliente);
            em.getTransaction().commit();
            System.out.println("Cliente cadastrado com sucesso.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void listar() {
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

    public void atualizar(Scanner sc) {
        listar();
        System.out.print("ID do cliente: ");
        Long id = sc.nextLong(); sc.nextLine();

        EntityManager em = emf.createEntityManager();
        ClienteDAO dao = new ClienteDAO(em);

        try {
            Cliente c = dao.buscarPorId(id);
            if (c != null) {
                System.out.print("Novo nome: ");
                c.setNome(sc.nextLine());
                System.out.print("Novo CPF/CNPJ: ");
                c.setCpfCnpj(sc.nextLine());
                System.out.print("Novo telefone: ");
                c.setTelefone(sc.nextInt()); sc.nextLine();

                em.getTransaction().begin();
                dao.atualizar(c);
                em.getTransaction().commit();
                System.out.println("Cliente atualizado.");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void remover(Scanner sc) {
        listar();
        System.out.print("ID do cliente: ");
        Long id = sc.nextLong(); sc.nextLine();

        EntityManager em = emf.createEntityManager();
        ClienteDAO dao = new ClienteDAO(em);

        try {
            em.getTransaction().begin();
            dao.remover(id);
            em.getTransaction().commit();
            System.out.println("Cliente removido.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao remover: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
