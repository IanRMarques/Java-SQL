package br.inatel.cli;

import br.inatel.dao.PilotoDAO;
import br.inatel.dao.PilotoEquipamentoDAO;
import br.inatel.model.Piloto;
import br.inatel.model.PilotoEquipamentoInfo;
import br.inatel.dao.CarroPilotoDAO;  // Importando o CarroPilotoDAO
import java.util.List;
import java.util.Scanner;

public class PilotoCLI {

    // Método para listar os equipamentos associados aos pilotos
    public void listarEquipamentosPorPiloto() {
        System.out.println("Executando consulta de Equipamentos por Piloto...");
        List<PilotoEquipamentoInfo> associacoes = new PilotoEquipamentoDAO().listEquipamentosPorPiloto();
        if (associacoes.isEmpty()) {
            System.out.println("Nenhuma associação encontrada.");
        } else {
            System.out.println("\n=== EQUIPAMENTOS POR PILOTO ===");
            associacoes.forEach(System.out::println); // Exibe a lista de associações
        }
    }

    // Processamento das opções no menu CLI
    public static void process(int op, Scanner in) {
        PilotoDAO dao = new PilotoDAO();

        switch(op) {
            case 1: // Insert
                System.out.print("Nome do Piloto: ");
                dao.insertPiloto(new Piloto(in.nextLine()));
                System.out.println("✔ Piloto inserido");
                break;

            case 2: // Update
                System.out.print("ID Piloto: ");
                int pid = Integer.parseInt(in.nextLine());
                System.out.print("Novo nome: ");
                dao.updateNome(pid, in.nextLine());
                System.out.println("✔ Atualizado");
                break;

            case 3: // Delete
                System.out.print("ID Piloto excluir: ");
                dao.deletePiloto(Integer.parseInt(in.nextLine()));
                System.out.println("✔ Excluído");
                break;

            case 4: // Select All
                dao.listAll().forEach(System.out::println);
                break;

            case 5: // Select One
                System.out.print("ID Piloto: ");
                Piloto p = dao.findById(Integer.parseInt(in.nextLine()));
                System.out.println(p != null ? p : "Não encontrado");
                break;

            case 6: // Listar Equipamentos por Piloto
                new PilotoCLI().listarEquipamentosPorPiloto(); // Chama o método não estático
                break;

            case 7: // Associar Carro ao Piloto
                associarCarroPiloto(in); // Corrigido para chamar associarCarroPiloto corretamente
                break;

            default:
                System.out.println("Opção inválida");
        }
    }

    // Método para associar o carro ao piloto
    public static void associarCarroPiloto(Scanner in) {
        System.out.print("ID do Carro: ");
        int idCarro = Integer.parseInt(in.nextLine());
        System.out.print("ID do Piloto: ");
        int idPiloto = Integer.parseInt(in.nextLine());

        // Agora chamando corretamente o método associarCarroPiloto da classe CarroPilotoDAO
        CarroPilotoDAO carroPilotoDAO = new CarroPilotoDAO();
        if (carroPilotoDAO.insert(idCarro, idPiloto)) {
            System.out.println("✔ Carro associado ao piloto com sucesso!");
        } else {
            System.out.println("✖ Erro ao associar carro ao piloto.");
        }
    }

    // Método para associar o equipamento ao piloto
    public static void associarEquipamentoPiloto(Scanner in) {
        System.out.print("ID do Piloto: ");
        int idPiloto = Integer.parseInt(in.nextLine());
        System.out.print("ID do Equipamento: ");
        int idEquipamento = Integer.parseInt(in.nextLine());

        // Usando o DAO para associar o equipamento ao piloto
        PilotoEquipamentoDAO pilotoEquipamentoDAO = new PilotoEquipamentoDAO();
        if (pilotoEquipamentoDAO.insert(idPiloto, idEquipamento)) {
            System.out.println("✔ Equipamento associado ao piloto com sucesso!");
        } else {
            System.out.println("✖ Erro ao associar equipamento ao piloto.");
        }
    }
}
