// PilotoEquipamentoDAO.java
package br.inatel.dao;

import br.inatel.model.PilotoEquipamentoInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotoEquipamentoDAO {
    public boolean insert(int idPiloto, int idEquipamento) {
        String sql = "INSERT INTO Piloto_Equipamento (id_piloto, id_equipamento) VALUES (?, ?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idPiloto);
            p.setInt(2, idEquipamento);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserir Piloto_Equipamento: "+ e.getMessage());
            return false;
        }
    }
    public boolean delete(int idPiloto, int idEquipamento) {
        String sql = "DELETE FROM Piloto_Equipamento WHERE id_piloto = ? AND id_equipamento = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idPiloto);
            p.setInt(2, idEquipamento);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro delete Piloto_Equipamento: "+ e.getMessage());
            return false;
        }
    }
    public List<PilotoEquipamentoInfo> listEquipamentosPorPiloto() {
        String sql =
                "SELECT p.nome AS pilotoNome, e.nome AS equipamentoNome " +
                        "FROM Piloto_Equipamento pe " +
                        "JOIN Piloto p       ON pe.id_piloto     = p.id_piloto " +
                        "JOIN Equipamento_Seguranca e ON pe.id_equipamento = e.id_equipamento";
        List<PilotoEquipamentoInfo> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                L.add(new PilotoEquipamentoInfo(
                        rs.getString("pilotoNome"),
                        rs.getString("equipamentoNome")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro JOIN Piloto–Equipamento: "+ e.getMessage());
        }
        return L;
    }
}
