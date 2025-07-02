package com.frameworks.service;

import dao.AdvogadoDAO;
import dao.ClienteDAO;
import dao.ProcessoDAO;
import model.Advogado;
import model.Cliente;
import model.Processo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessoService {

    private EntityManagerFactory emf;

    public ProcessoService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void menu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- PROCESSOS ---");
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
        EntityManager em = emf.createEntityManager();
        ProcessoDAO processoDAO = new ProcessoDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);
        AdvogadoDAO advogadoDAO = new AdvogadoDAO(em);

        try {
            System.out.print("Número do processo: ");
            String numero = sc.nextLine();
            System.out.print("Status: ");
            String status = sc.nextLine();
            System.out.print("Vara: ");
            String vara = sc.nextLine();

            System.out.print("ID do Cliente: ");
            Long idCli = sc.nextLong(); sc.nextLine();
            Cliente cliente = clienteDAO.buscarPorId(idCli);

            List<Advogado> advogados = new ArrayList<>();
            while (true) {
                System.out.print("ID do Advogado (ou 0 para parar): ");
                Long idAdv = sc.nextLong(); sc.nextLine();
                if (idAdv == 0) break;
                Advogado advogado = advogadoDAO.buscarPorId(idAdv);
                if (advogado != null) {
                    advogados.add(advogado);
                } else {
                    System.out.println("Advogado não encontrado.");
                }
            }

            if (cliente == null || advogados.isEmpty()) {
                System.out.println("Cliente ou advogados inválidos.");
                return;
            }

            Processo processo = new Processo();
            processo.setNumeroProcesso(numero);
            processo.setStatus(status);
            processo.setVara(vara);
            processo.setCliente(cliente);
            processo.setAdvogados(advogados);

            em.getTransaction().begin();
            processoDAO.salvar(processo);
            em.getTransaction().commit();
            System.out.println("Processo cadastrado.");
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

    public void atualizar(Scanner sc) {
        listar();
        System.out.print("ID do processo: ");
        Long id = sc.nextLong(); sc.nextLine();

        EntityManager em = emf.createEntityManager();
        ProcessoDAO processoDAO = new ProcessoDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);
        AdvogadoDAO advogadoDAO = new AdvogadoDAO(em);

        try {
            Processo p = processoDAO.buscarPorId(id);
            if (p == null) {
                System.out.println("Processo não encontrado.");
                return;
            }

            System.out.print("Novo número do processo: ");
            p.setNumeroProcesso(sc.nextLine());
            System.out.print("Novo status: ");
            p.setStatus(sc.nextLine());
            System.out.print("Nova vara: ");
            p.setVara(sc.nextLine());

            System.out.print("Novo ID do cliente: ");
            Long idCli = sc.nextLong(); sc.nextLine();
            Cliente cliente = clienteDAO.buscarPorId(idCli);
            if (cliente != null) {
                p.setCliente(cliente);
            }

            List<Advogado> novosAdvogados = new ArrayList<>();
            while (true) {
                System.out.print("ID do Advogado (ou 0 para parar): ");
                Long idAdv = sc.nextLong(); sc.nextLine();
                if (idAdv == 0) break;
                Advogado advogado = advogadoDAO.buscarPorId(idAdv);
                if (advogado != null) {
                    novosAdvogados.add(advogado);
                }
            }
            p.setAdvogados(novosAdvogados);

            em.getTransaction().begin();
            processoDAO.atualizar(p);
            em.getTransaction().commit();
            System.out.println("Processo atualizado.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao atualizar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void remover(Scanner sc) {
        listar();
        System.out.print("ID do processo: ");
        Long id = sc.nextLong(); sc.nextLine();

        EntityManager em = emf.createEntityManager();
        ProcessoDAO processoDAO = new ProcessoDAO(em);

        try {
            em.getTransaction().begin();
            processoDAO.remover(id);
            em.getTransaction().commit();
            System.out.println("Processo removido.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao remover: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
