// EquipamentoCLI.java
package br.inatel.cli;

import br.inatel.dao.EquipamentoSegurancaDAO;
import br.inatel.dao.PilotoEquipamentoDAO;
import br.inatel.model.EquipamentoSeguranca;
import br.inatel.model.PilotoEquipamentoInfo;

import java.util.List;
import java.util.Scanner;

public class EquipamentoCLI {
    public static void process(int op, Scanner in) {
        EquipamentoSegurancaDAO dao = new EquipamentoSegurancaDAO();
        switch(op) {
            case 1:
                System.out.print("Nome do Equipamento: ");
                dao.insertEquipamento(new EquipamentoSeguranca(in.nextLine()));
                System.out.println("✔ Equipamento inserido");
                break;
            case 2:
                System.out.print("ID Equipamento: ");
                int eid = Integer.parseInt(in.nextLine());
                System.out.print("Novo nome: ");
                dao.updateNome(eid, in.nextLine());
                System.out.println("✔ Atualizado");
                break;
            case 3:
                System.out.print("ID Equipamento excluir: ");
                dao.deleteEquipamento(Integer.parseInt(in.nextLine()));
                System.out.println("✔ Excluído");
                break;
            case 4:
                dao.listAll().forEach(e-> System.out.println(e));
                break;
            case 5:
                System.out.print("ID Equipamento: ");
                EquipamentoSeguranca e = dao.findById(Integer.parseInt(in.nextLine()));
                System.out.println(e!=null?e:"Não encontrado");
                break;
            case 6:
                PilotoEquipamentoDAO pedao = new PilotoEquipamentoDAO();
                List<PilotoEquipamentoInfo> rels = pedao.listEquipamentosPorPiloto();
                rels.forEach(r-> System.out.println(r));
                break;
            default:
                System.out.println("Op. inválida");
        }
    }
}
