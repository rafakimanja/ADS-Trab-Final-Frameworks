package com.frameworks.controller;

import com.frameworks.service.AdvogadoService;
import com.frameworks.service.ClienteService;
import com.frameworks.service.ProcessoService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Advogado;
import model.Cliente;
import model.Processo;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ProcessoController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    ProcessoService ps;
    ClienteService cs;
    AdvogadoService as;

    public ProcessoController(){
        ps = new ProcessoService(emf);
        cs = new ClienteService(emf);
        as = new AdvogadoService(emf);
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
        }
        while (opcao != 0);
    }

    public void cadastrar(Scanner scanner){
        List<Advogado> advogados = new ArrayList<>();

        System.out.print("Número do processo: ");
        String numero = scanner.nextLine();
        System.out.print("Status: ");
        String status = scanner.nextLine();
        System.out.print("Vara: ");
        String vara = scanner.nextLine();

        cs.listaClientes();
        System.out.print("ID do Cliente: ");
        Long idCli = scanner.nextLong(); scanner.nextLine();
        Cliente cliente = cs.buscaClienteById(idCli);

        if(cliente == null ){
            System.out.println("Cliente nao encontrado");
            return;
        }

        as.listarAdvogados();
        while (true) {
            System.out.print("ID do Advogado (ou 0 para parar): ");
            Long idAdv = scanner.nextLong(); scanner.nextLine();

            if (idAdv == 0)
                break;

            Advogado advogado = as.buscaAdvogadoById(idAdv);
            if (advogado != null) {

                boolean flag = true;
                for (Advogado adv: advogados){
                    if(Objects.equals(adv.getId(), idAdv)){
                        System.out.println("Advogado ja escolhido para o processo");
                        flag = false;
                    }
                }

                if(flag)
                    advogados.add(advogado);

            } else {
                System.out.println("Advogado nao encontrado.");
            }
        }

        if (advogados.isEmpty()) {
            System.out.println("Advogados invalidos.");
            return;
        }

        Exception ex = ps.cadastraProcesso(numero, status, vara, cliente, advogados);

        if (ex != null) {
            if (ex instanceof IllegalArgumentException) {
                System.out.println("Dados invalidos: " + ex.getMessage());
            } else {
                System.out.println("Erro!: " + ex.getMessage());
            }
        } else {
            System.out.println("Processo cadastrado com sucesso!");
        }
    }

    public void listar(){
        ps.listarProcessos();
    }

    public void atualizar(Scanner scanner){
        List<Advogado> advogados = new ArrayList<>();
        Exception ex = null;

        ps.listarProcessos();
        System.out.print("ID do processo: ");
        Long id = scanner.nextLong(); scanner.nextLine();

        Processo p = ps.buscaProcessoById(id);
        if(p == null){
            System.out.println("Processo nao encontrado");
            return;
        }


        cs.listaClientes();
        System.out.print("ID do novo cliente: ");
        Long idCli = scanner.nextLong(); scanner.nextLine();

        Cliente c = cs.buscaClienteById(idCli);
        if(c == null){
            System.out.println("Cliente nao encontrado");
            return;
        }


        as.listarAdvogados();
        while (true) {
            System.out.print("ID do Advogado (ou 0 para parar): ");
            Long idAdv = scanner.nextLong(); scanner.nextLine();

            if (idAdv == 0)
                break;

            Advogado advogado = as.buscaAdvogadoById(idAdv);
            if (advogado != null) {

                boolean flag = true;
                for (Advogado adv: advogados){
                    if(Objects.equals(adv.getId(), idAdv)){
                        System.out.println("Advogado ja escolhido para o processo");
                        flag = false;
                    }
                }

                if(flag)
                    advogados.add(advogado);

            } else {
                System.out.println("Advogado nao encontrado.");
            }
        }

        if (advogados.isEmpty()) {
            System.out.println("Advogados invalidos.");
            return;
        }

        System.out.print("Novo número do processo: ");
        String newNumero = scanner.nextLine();
        System.out.print("Novo status: ");
        String newStatus = scanner.nextLine();
        System.out.print("Nova vara: ");
        String newVara = scanner.nextLine();

        try{
            p.setCliente(c);
            p.setAdvogados(advogados);
            p.setNumeroProcesso(newNumero);
            p.setStatus(newStatus);
            p.setVara(newVara);
        } catch (IllegalArgumentException e) {
            System.out.println("Dados invalidos: " + e.getMessage());
        }

        ex = ps.atualizaProcesso(p);

        if (ex != null)
            System.out.println("Erro!: " + ex.getMessage());
        else
            System.out.println("Processo atualizado com sucesso!");
    }

    public void remover(Scanner scanner){
        ps.listarProcessos();
        Exception ex;

        System.out.print("ID do processo: ");
        Long id = scanner.nextLong(); scanner.nextLine();

        ex = ps.removerProcesso(id);

        if (ex != null)
            System.out.println("Erro!: " + ex.getMessage());
        else
            System.out.println("Processo deletado com sucesso!");
    }

}
