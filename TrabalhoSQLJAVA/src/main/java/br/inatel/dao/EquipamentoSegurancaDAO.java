package br.inatel.dao;

import br.inatel.model.EquipamentoSeguranca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoSegurancaDAO {
    public boolean insertEquipamento(EquipamentoSeguranca equipamento) {
        String sql = "INSERT INTO EquipamentoSeguranca (capacete, macacao, bota, luva) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, equipamento.getCapacete());
            pstmt.setString(2, equipamento.getMacacao());
            pstmt.setString(3, equipamento.getBota());
            pstmt.setString(4, equipamento.getLuva());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    equipamento.setIdEquipamento(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir equipamento: " + e.getMessage());
            return false;
        }
    }

    public List<EquipamentoSeguranca> listAll() {
        String sql = "SELECT * FROM EquipamentoSeguranca";
        List<EquipamentoSeguranca> equipamentos = new ArrayList<>();

        try (Connection conn = ConnectionDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EquipamentoSeguranca equipamento = new EquipamentoSeguranca(
                        rs.getString("capacete"),
                        rs.getString("macacao"),
                        rs.getString("bota"),
                        rs.getString("luva")
                );
                equipamento.setIdEquipamento(rs.getInt("id_equipamento"));
                equipamentos.add(equipamento);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar equipamentos: " + e.getMessage());
        }
        return equipamentos;
    }

    public boolean deleteEquipamento(int idEquipamento) {
        String sql = "DELETE FROM EquipamentoSeguranca WHERE id_equipamento = ?";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEquipamento);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar equipamento: " + e.getMessage());
            return false;
        }
    }
}