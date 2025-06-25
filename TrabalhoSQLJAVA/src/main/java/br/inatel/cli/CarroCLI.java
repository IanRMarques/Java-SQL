// CarroCLI.java
package br.inatel.cli;

import br.inatel.dao.CarroDAO;
import br.inatel.dao.CarroPilotoDAO;
import br.inatel.model.Carro;
import br.inatel.model.CarroPilotoInfo;

import java.util.List;
import java.util.Scanner;

public class CarroCLI {
    public static void listarCarrosComPilotos() {
        List<CarroPilotoInfo> associacoes = new CarroPilotoDAO().listCarrosComPilotos();
        if (associacoes.isEmpty()) {
            System.out.println("Nenhuma associação encontrada");
        } else {
            associacoes.forEach(System.out::println);
        }
    }

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
                List<Carro> carros = dao.listAll();
                if (carros.isEmpty()) {
                    System.out.println("Nenhum carro cadastrado.");
                } else {
                    System.out.println("\n=== LISTA DE CARROS ===");
                    System.out.println("ID\tMarca ID\tNome");
                    System.out.println("----------------------------------");
                    for (Carro carro : carros) {
                        System.out.printf("%d\t%d\t\t%s%n",
                                carro.getIdCarro(),
                                carro.getIdMarca(),
                                carro.getNome());
                    }
                }
                break;
            case 5:
                System.out.print("ID Carro: ");
                Carro c = dao.findById(Integer.parseInt(in.nextLine()));
                System.out.println(c!=null?c:"Não encontrado");
                break;
            case 6: // JOIN (Novo)
                List<String> associacoes = dao.listarCarrosComPilotos();
                if (associacoes.isEmpty()) {
                    System.out.println("Nenhuma associação carro-piloto encontrada");
                } else {
                    associacoes.forEach(System.out::println);
                }
                break;

            case 7: // Associar Piloto (Novo - opcional)
                System.out.print("ID do Carro: ");
                int idCarro = Integer.parseInt(in.nextLine());
                System.out.print("ID do Piloto: ");
                int idPiloto = Integer.parseInt(in.nextLine());

                if (dao.associarPiloto(idCarro, idPiloto)) {
                    System.out.println("✔ Piloto associado com sucesso!");
                } else {
                    System.out.println("✖ Erro na associação");
                }
                break;
        }
    }
}