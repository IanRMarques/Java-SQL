package br.inatel.dao;

import br.inatel.model.Piloto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroPilotoDAO {
    public boolean insertRelacionamento(int idCarro, int idPiloto) {
        String sql = "INSERT INTO Carro_Piloto (id_carro, id_piloto) VALUES (?, ?)";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCarro);
            pstmt.setInt(2, idPiloto);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir relacionamento: " + e.getMessage());
            return false;
        }
    }

    public List<Piloto> listPilotosPorCarro(int idCarro) {
        String sql = "SELECT p.* FROM Piloto p " +
                "JOIN Carro_Piloto cp ON p.idPiloto = cp.id_piloto " +
                "WHERE cp.id_carro = ?";
        List<Piloto> pilotos = new ArrayList<>();

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCarro);
            try (ResultSet rs = pstmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pilotos: " + e.getMessage());
        }
        return pilotos;
    }
}