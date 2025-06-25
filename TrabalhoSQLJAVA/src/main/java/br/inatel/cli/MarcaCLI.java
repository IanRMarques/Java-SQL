package br.inatel.cli;

import br.inatel.dao.MarcaDAO;
import br.inatel.model.Marca;
import br.inatel.model.MarcaPatrocinadorInfo;
import br.inatel.dao.MarcaPatrocinadorDAO;
import java.util.List;
import java.util.Scanner;

public class MarcaCLI {

    public static void listarPatrocinadoresPorMarca() {
        List<MarcaPatrocinadorInfo> associacoes = new MarcaPatrocinadorDAO().listPatrocinadoresPorMarca();
        if (associacoes.isEmpty()) {
            System.out.println("Nenhuma associação encontrada");
        } else {
            associacoes.forEach(System.out::println);
        }
    }

    public static void associarPatrocinador(Scanner in) {
        System.out.print("ID da Marca: ");
        int idMarca = Integer.parseInt(in.nextLine());
        System.out.print("ID do Patrocinador: ");
        int idPatrocinador = Integer.parseInt(in.nextLine());

        if (new MarcaPatrocinadorDAO().insert(idMarca, idPatrocinador)) {
            System.out.println("✔ Patrocinador associado com sucesso!");
        } else {
            System.out.println("✖ Erro ao associar patrocinador");
        }
    }

    public static void process(int op, Scanner in) {
        MarcaDAO dao = new MarcaDAO();
        switch (op) {
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
                int idExcluir = Integer.parseInt(in.nextLine());

                // Verifica dependências em cascata
                boolean temDependencias = dao.verificarDependenciasCascata(idExcluir);

                if (temDependencias) {
                    System.out.println("Atenção: Esta marca possui:");
                    System.out.println("- Carros vinculados");
                    System.out.println("- Relacionamentos com pilotos");
                    System.out.print("Deseja excluir TODOS os registros vinculados? (S/N): ");
                    String resposta = in.nextLine();

                    if (resposta.equalsIgnoreCase("S")) {
                        boolean success = dao.deleteMarca(idExcluir, true);
                        System.out.println(success ? "✔ Todos os registros foram excluídos"
                                : "✖ Falha na exclusão completa");
                    } else {
                        System.out.println("✖ Exclusão cancelada pelo usuário");
                    }
                } else {
                    boolean success = dao.deleteMarca(idExcluir);
                    System.out.println(success ? "✔ Marca excluída" : "✖ Falha na exclusão");
                }
                break;
            case 4:
                dao.listAll().forEach(System.out::println);
                break;
            case 5:
                System.out.print("ID Marca: ");
                Marca m = dao.findById(Integer.parseInt(in.nextLine()));
                System.out.println(m != null ? m : "Não encontrada");
                break;
            case 6:
                List<MarcaPatrocinadorInfo> rels = dao.listMarcasComPatrocinador();
                rels.forEach(System.out::println);
                break;
            default:
                System.out.println("Op. inválida");
        }
    }
}