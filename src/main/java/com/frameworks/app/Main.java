package com.frameworks.app;

import com.frameworks.controller.ClienteController;
import com.frameworks.service.AdvogadoService;
import com.frameworks.service.ClienteService;
import com.frameworks.service.ProcessoService;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        Scanner scanner = new Scanner(System.in);

        AdvogadoService advogadoService = new AdvogadoService(emf);
        ClienteController clienteController = new ClienteController();
        ProcessoService processoService = new ProcessoService(emf);

        int opcao;
        do {
            System.out.println("\n===== SISTEMA JURÍDICO =====");
            System.out.println("1. Gerenciar Advogados");
            System.out.println("2. Gerenciar Clientes");
            System.out.println("3. Gerenciar Processos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> advogadoService.menu(scanner);
                case 2 -> clienteController.menu();
                case 3 -> processoService.menu(scanner);
            }
        } while (opcao != 0);

        emf.close();
        scanner.close();
        System.out.println("Sistema encerrado.");
    }
}
