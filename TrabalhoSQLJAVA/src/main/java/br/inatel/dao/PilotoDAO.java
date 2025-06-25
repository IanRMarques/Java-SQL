package br.inatel.dao;

import br.inatel.model.Piloto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotoDAO {
    // ========== CRUD BÁSICO ========== //
    public boolean insertPiloto(Piloto p1) {
        String sql = "INSERT INTO Piloto (nome) VALUES (?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, p1.getNome());
            p.executeUpdate();
            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) p1.setIdPiloto(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserir Piloto: " + e.getMessage());
            return false;
        }
    }

    public boolean updateNome(int id, String novo) {
        String sql = "UPDATE Piloto SET nome = ? WHERE id_piloto = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, novo);
            p.setInt(2, id);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro update Piloto: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePiloto(int id) {
        String sql = "DELETE FROM Piloto WHERE id_piloto = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro delete Piloto: " + e.getMessage());
            return false;
        }
    }

    public Piloto findById(int id) {
        String sql = "SELECT id_piloto, nome FROM Piloto WHERE id_piloto = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Piloto p1 = new Piloto(rs.getString("nome"));
                    p1.setIdPiloto(rs.getInt("id_piloto"));
                    return p1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro find Piloto: " + e.getMessage());
        }
        return null;
    }

    public List<Piloto> listAll() {
        String sql = "SELECT id_piloto, nome FROM Piloto";
        List<Piloto> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Piloto p1 = new Piloto(rs.getString("nome"));
                p1.setIdPiloto(rs.getInt("id_piloto"));
                L.add(p1);
            }
        } catch (SQLException e) {
            System.out.println("Erro list Piloto: " + e.getMessage());
        }
        return L;
    }

    // ========== MÉTODOS PARA ASSOCIAÇÕES ========== //

    /**
     * Associa um equipamento de segurança ao piloto
     */
    public boolean associarEquipamento(int idPiloto, int idEquipamento) {
        String sql = "INSERT INTO Piloto_Equipamento (id_piloto, id_equipamento) VALUES (?, ?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, idPiloto);
            p.setInt(2, idEquipamento);
            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao associar equipamento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos os equipamentos de um piloto
     */
    public List<String> listarEquipamentosDoPiloto(int idPiloto) {
        String sql = "SELECT e.nome FROM Equipamento_Seguranca e " +
                "JOIN Piloto_Equipamento pe ON e.id_equipamento = pe.id_equipamento " +
                "WHERE pe.id_piloto = ?";

        List<String> equipamentos = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, idPiloto);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    equipamentos.add(rs.getString("nome"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar equipamentos: " + e.getMessage());
        }
        return equipamentos;
    }

    /**
     * Lista todos os carros associados a um piloto
     */
    public List<String> listarCarrosDoPiloto(int idPiloto) {
        String sql = "SELECT c.nome FROM Carro c " +
                "JOIN Carro_Piloto cp ON c.id_carro = cp.id_carro " +
                "WHERE cp.id_piloto = ?";

        List<String> carros = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, idPiloto);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    carros.add(rs.getString("nome"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar carros: " + e.getMessage());
        }
        return carros;
    }
}