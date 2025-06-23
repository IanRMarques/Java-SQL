package br.inatel;

import java.util.Scanner;
import br.inatel.cli.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== TABELAS ===");
            System.out.println("1) Marca");
            System.out.println("2) Carro");
            System.out.println("3) Piloto");
            System.out.println("4) Equipamento de Segurança");
            System.out.println("5) Patrocinador");
            System.out.println("0) Sair");
            System.out.print("Escolha a tabela: ");
            int t = Integer.parseInt(in.nextLine());
            if (t==0) break;

            System.out.println("\n--- OPERAÇÕES ---");
            System.out.println("1) Insert");
            System.out.println("2) Update");
            System.out.println("3) Delete");
            System.out.println("4) Select All");
            System.out.println("5) Select One");
            System.out.println("6) Select com JOIN");
            System.out.print("Opção: ");
            int op = Integer.parseInt(in.nextLine());

            switch(t) {
                case 1: MarcaCLI.process(op, in); break;
                case 2: CarroCLI.process(op, in); break;
                case 3: PilotoCLI.process(op, in); break;
                case 4: EquipamentoCLI.process(op, in); break;
                case 5: PatrocinadorCLI.process(op, in); break;
                default: System.out.println("Tabela inválida");
            }
        }
        System.out.println("Fim.");
        in.close();
    }
}
