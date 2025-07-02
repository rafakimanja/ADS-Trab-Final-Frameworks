package com.frameworks.app;

import com.frameworks.controller.AdvogadoController;
import com.frameworks.controller.ClienteController;
import com.frameworks.controller.ProcessoController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AdvogadoController advogadoController = new AdvogadoController();
        ClienteController clienteController = new ClienteController();
        ProcessoController processoController = new ProcessoController();

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
                case 1 -> advogadoController.menu(scanner);
                case 2 -> clienteController.menu(scanner);
                case 3 -> processoController.menu(scanner);
            }
        } while (opcao != 0);
        scanner.close();
        System.out.println("Sistema encerrado.");
    }
}
