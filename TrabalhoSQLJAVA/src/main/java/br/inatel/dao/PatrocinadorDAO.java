package br.inatel.dao;

import br.inatel.model.Patrocinador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatrocinadorDAO {
    public boolean insertPatrocinador(Patrocinador patrocinador) {
        String sql = "INSERT INTO Patrocinador (nome, fama) VALUES (?, ?)";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, patrocinador.getNome());
            pstmt.setInt(2, patrocinador.getFama());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    patrocinador.setIdPatrocinador(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir patrocinador: " + e.getMessage());
            return false;
        }
    }

    public List<Patrocinador> listAll() {
        String sql = "SELECT * FROM Patrocinador";
        List<Patrocinador> patrocinadores = new ArrayList<>();

        try (Connection conn = ConnectionDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patrocinador patrocinador = new Patrocinador(
                        rs.getString("nome"),
                        rs.getInt("fama")
                );
                patrocinador.setIdPatrocinador(rs.getInt("idPatrocinador"));
                patrocinadores.add(patrocinador);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar patrocinadores: " + e.getMessage());
        }
        return patrocinadores;
    }
}