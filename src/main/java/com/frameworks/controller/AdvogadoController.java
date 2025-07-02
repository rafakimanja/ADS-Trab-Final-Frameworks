package com.frameworks.controller;

import com.frameworks.service.AdvogadoService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Advogado;

import java.util.Scanner;

public class AdvogadoController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    Scanner scanner = new Scanner(System.in);
    AdvogadoService as;

    public AdvogadoController(){
        as = new AdvogadoService(emf);
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
        }
        while (opcao != 0);
    }

    public void cadastrar(Scanner scanner){

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("OAB: ");
        String oab = scanner.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        Exception ex = as.cadastraAdvogado(nome, oab, especialidade);

        if (ex != null) {
            if (ex instanceof IllegalArgumentException) {
                System.out.println("Dados invalidos: " + ex.getMessage());
            } else {
                System.out.println("Erro!: " + ex.getMessage());
            }
        } else {
            System.out.println("Advogado cadastrado com sucesso!");
        }
    }

    public void listar(){
        as.listarAdvogados();
    }

    public void atualizar(Scanner scanner){
        as.listarAdvogados();
        Exception ex = null;

        System.out.print("ID do advogado: ");
        Long id = scanner.nextLong(); scanner.nextLine();

        Advogado advogado = as.buscaAdvogadoById(id);

        if(advogado != null){
            try{
                System.out.print("Novo nome: ");
                advogado.setNome(scanner.nextLine());
                System.out.print("Nova OAB: ");
                advogado.setNumeroOAB(scanner.nextLine());
                System.out.print("Nova especialidade: ");
                advogado.setEspecialidade(scanner.nextLine());
            } catch (IllegalArgumentException e){
                System.out.println("Dados invalidos: " + e.getMessage());
            }
            ex = as.atualizaAdvogado(advogado);
        } else {
            System.out.println("Advogado nao encontrado");
        }

        if (ex != null)
            System.out.println("Erro!: " + ex.getMessage());
        else
            System.out.println("Advogado atualizado com sucesso!");
    }

    public void remover(Scanner scanner){
        as.listarAdvogados();

        System.out.print("ID do advogado: ");
        Long id = scanner.nextLong(); scanner.nextLine();

        Exception ex = as.removeAdvogado(id);

        if (ex != null)
            System.out.println("Erro!: " + ex.getMessage());
        else
            System.out.println("Advogado deletado com sucesso!");
    }
}
