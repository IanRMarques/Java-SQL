package br.inatel;

import br.inatel.dao.*;
import br.inatel.model.*;

public class Main {
    public static void main(String[] args) {
        // 1. Teste Marca
        Marca mercedes = new Marca("Mercedes", "Alemanha", 5);
        MarcaDAO marcaDAO = new MarcaDAO();
        marcaDAO.insertMarca(mercedes);
        System.out.println("Marca inserida: " + mercedes.getNome());

        // 2. Teste Piloto
        Piloto piloto1 = new Piloto("Lewis Hamilton", 100, "Masculino", "Reino Unido", true);
        PilotoDAO pilotoDAO = new PilotoDAO();
        pilotoDAO.insertPiloto(piloto1);
        System.out.println("Piloto inserido: " + piloto1.getNome());

        // 3. Teste Patrocinador
        Patrocinador petrobras = new Patrocinador("Petrobras", 95);
        PatrocinadorDAO patrocinadorDAO = new PatrocinadorDAO();
        patrocinadorDAO.insertPatrocinador(petrobras);
        System.out.println("Patrocinador inserido: " + petrobras.getNome());

        // 4. Teste Relacionamento Marca-Patrocinador
        MarcaPatrocinadorDAO mpDAO = new MarcaPatrocinadorDAO();
        mpDAO.insert(new MarcaPatrocinador(mercedes.getIdMarca(), petrobras.getIdPatrocinador()));
        System.out.println("Relacionamento Marca-Patrocinador criado");

        // 5. Listar dados
        System.out.println("\n--- LISTAGEM COMPLETA ---");
        System.out.println("Marcas:");
        marcaDAO.listAll().forEach(m -> System.out.println("- " + m.getNome() + " (" + m.getNacionalidade() + ")"));

        System.out.println("\nPilotos:");
        pilotoDAO.listAll().forEach(p -> System.out.println("- " + p.getNome() + (p.isCampeao() ? " (Campeão)" : "")));

        System.out.println("\nPatrocinadores:");
        patrocinadorDAO.listAll().forEach(p -> System.out.println("- " + p.getNome() + " (Fama: " + p.getFama() + ")"));
    }
}