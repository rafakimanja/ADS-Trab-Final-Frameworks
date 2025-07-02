package com.frameworks.controller;

import com.frameworks.service.ClienteService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Cliente;

import java.util.Scanner;

public class ClienteController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    ClienteService cs;

    public ClienteController(){
        cs = new ClienteService(emf);
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

            switch (opcao){
                case 1 -> cadastrar(scanner);
                case 2 -> listar();
                case 3 -> atualizar(scanner);
                case 4 -> deletar(scanner);
            }

        }
        while (opcao != 0);
    }

    public void cadastrar(Scanner scanner){
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF/CNPJ: ");
        String cpfCnpj = scanner.nextLine();

        System.out.print("Telefone (apenas números): ");
        Long telefone = scanner.nextLong(); scanner.nextLine();

        Exception ex = cs.cadastraCliente(nome, cpfCnpj, telefone);

        if (ex != null) {
            if (ex instanceof IllegalArgumentException) {
                System.out.println("Dados invalidos: " + ex.getMessage());
            } else {
                System.out.println("Erro!: " + ex.getMessage());
            }
        } else {
            System.out.println("Cliente cadastrado com sucesso!");
        }
    }

    public void listar(){
        cs.listaClientes();
    }

    public void atualizar(Scanner scanner){
        cs.listaClientes();
        Exception ex = null;

        System.out.print("ID do cliente: ");
        Long id = scanner.nextLong(); scanner.nextLine();

        Cliente cliente = cs.buscaClienteById(id);

        if(cliente != null){
            try{
                System.out.print("Novo nome: ");
                cliente.setNome(scanner.nextLine());
                System.out.print("Novo CPF/CNPJ: ");
                cliente.setCpfCnpj(scanner.nextLine());
                System.out.print("Novo telefone: ");
                cliente.setTelefone(scanner.nextLong()); scanner.nextLine();
            } catch (IllegalArgumentException e){
                System.out.println("Dados invalidos: " + e.getMessage());
            }
            ex = cs.atualizaCliente(cliente);
        } else {
            System.out.println("Cliente nao encontrado.");
        }

        if (ex != null)
            System.out.println("Erro!: " + ex.getMessage());
        else
            System.out.println("Cliente atualizado com sucesso!");

    }

    public void deletar(Scanner scanner){
        cs.listaClientes();

        System.out.print("ID do cliente: ");
        Long id = scanner.nextLong(); scanner.nextLine();

        Exception ex = cs.removeCliente(id);

        if (ex != null)
            System.out.println("Erro!: " + ex.getMessage());
        else
            System.out.println("Cliente deletado com sucesso!");
    }
}
