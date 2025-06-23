// MarcaCLI.java
package br.inatel.cli;

import br.inatel.dao.MarcaDAO;
import br.inatel.model.Marca;
import br.inatel.model.MarcaPatrocinadorInfo;

import java.util.List;
import java.util.Scanner;

public class MarcaCLI {
    public static void process(int op, Scanner in) {
        MarcaDAO dao = new MarcaDAO();
        switch(op) {
            case 1:
                System.out.print("Nome da Marca: ");
                String nm = in.nextLine();
                dao.insertMarca(new Marca(nm));
                System.out.println("✔ Marca inserida");
                break;
            case 2:
                System.out.print("ID Marca: ");
                int ui = Integer.parseInt(in.nextLine());
                System.out.print("Novo nome: ");
                dao.updateNome(ui, in.nextLine());
                System.out.println("✔ Atualizada");
                break;
            case 3:
                System.out.print("ID Marca para excluir: ");
                dao.deleteMarca(Integer.parseInt(in.nextLine()));
                System.out.println("✔ Excluída");
                break;
            case 4:
                dao.listAll().forEach(m -> System.out.println(m));
                break;
            case 5:
                System.out.print("ID Marca: ");
                Marca m = dao.findById(Integer.parseInt(in.nextLine()));
                System.out.println(m!=null? m:"Não encontrada");
                break;
            case 6:
                List<MarcaPatrocinadorInfo> rels = dao.listMarcasComPatrocinador();
                rels.forEach(r-> System.out.println(r));
                break;
            default:
                System.out.println("Op. inválida");
        }
    }
}
