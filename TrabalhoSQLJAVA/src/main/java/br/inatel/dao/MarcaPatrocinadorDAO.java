package br.inatel.dao;

import br.inatel.model.MarcaPatrocinadorInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaPatrocinadorDAO {
    public boolean insert(int idMarca, int idPatrocinador) {
        String sql = "INSERT INTO Marca_Patrocinador (id_marca, id_patrocinador) VALUES (?, ?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idMarca);
            p.setInt(2, idPatrocinador);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao associar Marca-Patrocinador: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idMarca, int idPatrocinador) {
        String sql = "DELETE FROM Marca_Patrocinador WHERE id_marca = ? AND id_patrocinador = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idMarca);
            p.setInt(2, idPatrocinador);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao desassociar Marca-Patrocinador: " + e.getMessage());
            return false;
        }
    }

    public List<MarcaPatrocinadorInfo> listPatrocinadoresPorMarca() {
        String sql = "SELECT m.nome AS marcaNome, p.nome AS patrocinadorNome " +
                "FROM Marca_Patrocinador mp " +
                "JOIN Marca m ON mp.id_marca = m.id_marca " +
                "JOIN Patrocinador p ON mp.id_patrocinador = p.id_patrocinador";

        List<MarcaPatrocinadorInfo> associacoes = new ArrayList<>();

        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while(rs.next()) {
                associacoes.add(new MarcaPatrocinadorInfo(
                        rs.getString("marcaNome"),
                        rs.getString("patrocinadorNome")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar associações Marca-Patrocinador: " + e.getMessage());
        }
        return associacoes;
    }
}