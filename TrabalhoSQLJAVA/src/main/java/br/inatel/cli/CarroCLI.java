// CarroCLI.java
package br.inatel.cli;

import br.inatel.dao.CarroDAO;
import br.inatel.dao.CarroPilotoDAO;
import br.inatel.model.Carro;
import br.inatel.model.CarroPilotoInfo;

import java.util.List;
import java.util.Scanner;

public class CarroCLI {
    public static void process(int op, Scanner in) {
        CarroDAO dao = new CarroDAO();
        switch(op) {
            case 1:
                System.out.print("ID da Marca: ");
                int mid = Integer.parseInt(in.nextLine());
                System.out.print("Nome do Carro: ");
                String nm = in.nextLine();
                dao.insertCarro(new Carro(mid, nm));
                System.out.println("✔ Carro inserido");
                break;
            case 2:
                System.out.print("ID Carro: ");
                int cid = Integer.parseInt(in.nextLine());
                System.out.print("Novo nome: ");
                dao.updateNome(cid, in.nextLine());
                System.out.println("✔ Atualizado");
                break;
            case 3:
                System.out.print("ID Carro excluir: ");
                dao.deleteCarro(Integer.parseInt(in.nextLine()));
                System.out.println("✔ Excluído");
                break;
            case 4:
                dao.listAll().forEach(c-> System.out.println(c));
                break;
            case 5:
                System.out.print("ID Carro: ");
                Carro c = dao.findById(Integer.parseInt(in.nextLine()));
                System.out.println(c!=null?c:"Não encontrado");
                break;
            case 6:
                CarroPilotoDAO cpdao = new CarroPilotoDAO();
                List<CarroPilotoInfo> rels = cpdao.listCarrosComPilotos();
                rels.forEach(r-> System.out.println(r));
                break;
            default:
                System.out.println("Op. inválida");
        }
    }
}
