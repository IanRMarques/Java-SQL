package br.inatel.dao;

import br.inatel.model.Piloto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotoDAO {
    public boolean insertPiloto(Piloto piloto) {
        String sql = "INSERT INTO Piloto (nome, qpf, genero, nacionalidade, campeao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, piloto.getNome());
            pstmt.setInt(2, piloto.getQpf());
            pstmt.setString(3, piloto.getGenero());
            pstmt.setString(4, piloto.getNacionalidade());
            pstmt.setBoolean(5, piloto.isCampeao());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    piloto.setIdPiloto(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir piloto: " + e.getMessage());
            return false;
        }
    }

    public List<Piloto> listAll() {
        String sql = "SELECT * FROM Piloto";
        List<Piloto> pilotos = new ArrayList<>();

        try (Connection conn = ConnectionDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Piloto piloto = new Piloto(
                        rs.getString("nome"),
                        rs.getInt("qpf"),
                        rs.getString("genero"),
                        rs.getString("nacionalidade"),
                        rs.getBoolean("campeao")
                );
                piloto.setIdPiloto(rs.getInt("idPiloto"));
                pilotos.add(piloto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pilotos: " + e.getMessage());
        }
        return pilotos;
    }
}