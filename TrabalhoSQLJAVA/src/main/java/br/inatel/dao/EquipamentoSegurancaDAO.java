// EquipamentoSegurancaDAO.java
package br.inatel.dao;

import br.inatel.model.EquipamentoSeguranca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoSegurancaDAO {
    public boolean insertEquipamento(EquipamentoSeguranca e1) {
        String sql = "INSERT INTO Equipamento_Seguranca (nome) VALUES (?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, e1.getNome());
            p.executeUpdate();
            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) e1.setIdEquipamento(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserir Equipamento: " + e.getMessage());
            return false;
        }
    }

    public boolean updateNome(int id, String novo) {
        String sql = "UPDATE Equipamento_Seguranca SET nome = ? WHERE id_equipamento = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, novo);
            p.setInt(2, id);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro update Equipamento: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEquipamento(int id) {
        String sql = "DELETE FROM Equipamento_Seguranca WHERE id_equipamento = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro delete Equipamento: " + e.getMessage());
            return false;
        }
    }

    public EquipamentoSeguranca findById(int id) {
        String sql = "SELECT id_equipamento, nome FROM Equipamento_Seguranca WHERE id_equipamento = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    EquipamentoSeguranca e1 = new EquipamentoSeguranca(rs.getString("nome"));
                    e1.setIdEquipamento(rs.getInt("id_equipamento"));
                    return e1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro find Equipamento: " + e.getMessage());
        }
        return null;
    }

    public List<EquipamentoSeguranca> listAll() {
        String sql = "SELECT id_equipamento, nome FROM Equipamento_Seguranca";
        List<EquipamentoSeguranca> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                EquipamentoSeguranca e1 = new EquipamentoSeguranca(rs.getString("nome"));
                e1.setIdEquipamento(rs.getInt("id_equipamento"));
                L.add(e1);
            }
        } catch (SQLException e) {
            System.out.println("Erro list Equipamento: " + e.getMessage());
        }
        return L;
    }
}
