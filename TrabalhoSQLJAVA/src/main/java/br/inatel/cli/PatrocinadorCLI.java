// PatrocinadorCLI.java
package br.inatel.cli;

import br.inatel.dao.PatrocinadorDAO;
import br.inatel.model.MarcaPatrocinadorInfo;
import br.inatel.model.Patrocinador;

import java.util.List;
import java.util.Scanner;

public class PatrocinadorCLI {
    public static void process(int op, Scanner in) {
        PatrocinadorDAO dao = new PatrocinadorDAO();
        switch(op) {
            case 1:
                System.out.print("Nome do Patrocinador: ");
                dao.insertPatrocinador(new Patrocinador(in.nextLine()));
                System.out.println("✔ Inserido");
                break;
            case 2:
                System.out.print("ID Patrocinador: ");
                int pid = Integer.parseInt(in.nextLine());
                System.out.print("Novo nome: ");
                dao.updateNome(pid, in.nextLine());
                System.out.println("✔ Atualizado");
                break;
            case 3:
                System.out.print("ID Patrocinador excluir: ");
                dao.deletePatrocinador(Integer.parseInt(in.nextLine()));
                System.out.println("✔ Excluído");
                break;
            case 4:
                dao.listAll().forEach(p-> System.out.println(p));
                break;
            case 5:
                System.out.print("ID Patrocinador: ");
                Patrocinador p = dao.findById(Integer.parseInt(in.nextLine()));
                System.out.println(p!=null?p:"Não encontrado");
                break;
            case 6:
                List<MarcaPatrocinadorInfo> rels = dao.listPatrocinadoresComMarcas();
                rels.forEach(r-> System.out.println(r));
                break;
            default:
                System.out.println("Op. inválida");
        }
    }
}
