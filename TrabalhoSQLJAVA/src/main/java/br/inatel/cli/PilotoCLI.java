// PilotoCLI.java
package br.inatel.cli;

import br.inatel.dao.PilotoDAO;
import br.inatel.dao.PilotoEquipamentoDAO;
import br.inatel.model.Piloto;
import br.inatel.model.PilotoEquipamentoInfo;

import java.util.List;
import java.util.Scanner;

public class PilotoCLI {
    public static void process(int op, Scanner in) {
        PilotoDAO dao = new PilotoDAO();
        switch(op) {
            case 1:
                System.out.print("Nome do Piloto: ");
                dao.insertPiloto(new Piloto(in.nextLine()));
                System.out.println("✔ Piloto inserido");
                break;
            case 2:
                System.out.print("ID Piloto: ");
                int pid = Integer.parseInt(in.nextLine());
                System.out.print("Novo nome: ");
                dao.updateNome(pid, in.nextLine());
                System.out.println("✔ Atualizado");
                break;
            case 3:
                System.out.print("ID Piloto excluir: ");
                dao.deletePiloto(Integer.parseInt(in.nextLine()));
                System.out.println("✔ Excluído");
                break;
            case 4:
                dao.listAll().forEach(p-> System.out.println(p));
                break;
            case 5:
                System.out.print("ID Piloto: ");
                Piloto p = dao.findById(Integer.parseInt(in.nextLine()));
                System.out.println(p!=null?p:"Não encontrado");
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
