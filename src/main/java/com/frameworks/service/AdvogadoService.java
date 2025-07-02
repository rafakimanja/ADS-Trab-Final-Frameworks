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

    public void menu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- ADVOGADOS ---");
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
        System.out.print("OAB: ");
        String oab = sc.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        Advogado advogado = new Advogado();
        advogado.setNome(nome);
        advogado.setNumeroOAB(oab);
        advogado.setEspecialidade(especialidade);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            AdvogadoDAO dao = new AdvogadoDAO(em);
            dao.salvar(advogado);
            em.getTransaction().commit();
            System.out.println("Advogado cadastrado com sucesso.");
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
            List<Advogado> advogados = em.createQuery("FROM Advogado", Advogado.class).getResultList();
            advogados.forEach(a -> {
                System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome() + " | OAB: " + a.getNumeroOAB() + " | Especialidade: " + a.getEspecialidade());
            });
        } finally {
            em.close();
        }
    }

    public void atualizar(Scanner sc) {
        listar();
        System.out.print("ID do advogado: ");
        Long id = sc.nextLong(); sc.nextLine();

        EntityManager em = emf.createEntityManager();
        AdvogadoDAO dao = new AdvogadoDAO(em);

        try {
            Advogado a = dao.buscarPorId(id);
            if (a != null) {
                System.out.print("Novo nome: ");
                a.setNome(sc.nextLine());
                System.out.print("Nova OAB: ");
                a.setNumeroOAB(sc.nextLine());
                System.out.print("Nova especialidade: ");
                a.setEspecialidade(sc.nextLine());

                em.getTransaction().begin();
                dao.atualizar(a);
                em.getTransaction().commit();
                System.out.println("Advogado atualizado.");
            } else {
                System.out.println("Advogado não encontrado.");
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
        System.out.print("ID do advogado: ");
        Long id = sc.nextLong(); sc.nextLine();

        EntityManager em = emf.createEntityManager();
        AdvogadoDAO dao = new AdvogadoDAO(em);

        try {
            em.getTransaction().begin();
            dao.remover(id);
            em.getTransaction().commit();
            System.out.println("Advogado removido.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao remover: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}

