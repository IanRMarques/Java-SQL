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
            if (t == 0) break;

            System.out.println("\n--- OPERAÇÕES ---");
            System.out.println("1) Insert");
            System.out.println("2) Update");
            System.out.println("3) Delete");
            System.out.println("4) Select All");
            System.out.println("5) Select One");
            System.out.println("6) Select com JOIN");

            if (t == 1 || t == 2 || t == 3) {
                String associacao = "";
                if (t == 1) associacao = "Patrocinador";
                if (t == 2) associacao = "Piloto";
                if (t == 3) associacao = "Equipamento";
                System.out.println("7) Associar " + associacao);
            }

            System.out.print("Opção: ");
            int op = Integer.parseInt(in.nextLine());

            switch(t) {
                case 1:
                    if (op == 6) {
                        System.out.println("\n=== PATROCINADORES POR MARCA ===");
                        new MarcaCLI().listarPatrocinadoresPorMarca();
                    } else if (op == 7) {
                        System.out.println("\n=== Associar Patrocinador ===");
                        new MarcaCLI().associarPatrocinador(in);
                    } else {
                        MarcaCLI.process(op, in);
                    }
                    break;
                case 2:
                    if (op == 6) {
                        System.out.println("\n=== CARROS COM PILOTOS ===");
                        new CarroCLI().listarCarrosComPilotos();
                    } else {
                        CarroCLI.process(op, in);
                    }
                    break;
                case 3:
                    if (op == 6) {
                        System.out.println("\n=== EQUIPAMENTOS POR PILOTO ===");
                        new PilotoCLI().listarEquipamentosPorPiloto();
                    } else if (op == 7) {
                        System.out.println("\n=== Associar Equipamento ao Piloto ===");
                        new PilotoCLI().associarEquipamentoPiloto(in); // Alterado para associar Equipamento ao Piloto
                    } else {
                        PilotoCLI.process(op, in);
                    }
                    break;
                case 4:
                    EquipamentoCLI.process(op, in);
                    break;
                case 5:
                    PatrocinadorCLI.process(op, in);
                    break;
                default:
                    System.out.println("Tabela inválida");
            }
        }
        System.out.println("Fim.");
        in.close();
    }
}
