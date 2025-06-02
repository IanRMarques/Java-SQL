package br.inatel.dao;

import br.inatel.model.PilotoEquipamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotoEquipamentoDAO {
    // Insere um novo relacionamento Piloto-Equipamento
    public boolean insert(PilotoEquipamento pilotoEquipamento) {
        String sql = "INSERT INTO Piloto_has_EquipamentoSeguranca (piloto_id_piloto, equipamento_id_equipamento) VALUES (?, ?)";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pilotoEquipamento.getIdPiloto());
            pstmt.setInt(2, pilotoEquipamento.getIdEquipamento());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir relacionamento: " + e.getMessage());
            return false;
        }
    }

    // Lista todos os equipamentos de um piloto específico
    public List<Integer> getEquipamentosByPiloto(int idPiloto) {
        String sql = "SELECT equipamento_id_equipamento FROM Piloto_has_EquipamentoSeguranca WHERE piloto_id_piloto = ?";
        List<Integer> equipamentosIds = new ArrayList<>();

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPiloto);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                equipamentosIds.add(rs.getInt("equipamento_id_equipamento"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar equipamentos do piloto: " + e.getMessage());
        }
        return equipamentosIds;
    }

    // Remove um relacionamento específico
    public boolean delete(int idPiloto, int idEquipamento) {
        String sql = "DELETE FROM Piloto_has_EquipamentoSeguranca WHERE piloto_id_piloto = ? AND equipamento_id_equipamento = ?";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPiloto);
            pstmt.setInt(2, idEquipamento);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar relacionamento: " + e.getMessage());
            return false;
        }
    }
}