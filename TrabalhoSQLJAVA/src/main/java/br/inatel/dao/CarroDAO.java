package br.inatel.dao;

import br.inatel.model.Carro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {
    // ========== CRUD BÁSICO ========== //
    public boolean insertCarro(Carro c1) {
        String sql = "INSERT INTO Carro (id_marca, nome) VALUES (?, ?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setInt(1, c1.getIdMarca());
            p.setString(2, c1.getNome());
            p.executeUpdate();
            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) c1.setIdCarro(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserir Carro: " + e.getMessage());
            return false;
        }
    }

    public boolean updateNome(int id, String novo) {
        String sql = "UPDATE Carro SET nome = ? WHERE id_carro = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, novo);
            p.setInt(2, id);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro update Carro: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCarro(int id) {
        String sql = "DELETE FROM Carro WHERE id_carro = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro delete Carro: " + e.getMessage());
            return false;
        }
    }

    public Carro findById(int id) {
        String sql = "SELECT id_carro, id_marca, nome FROM Carro WHERE id_carro = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Carro c1 = new Carro(rs.getInt("id_marca"), rs.getString("nome"));
                    c1.setIdCarro(rs.getInt("id_carro"));
                    return c1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro find Carro: " + e.getMessage());
        }
        return null;
    }

    public List<Carro> listAll() {
        String sql = "SELECT id_carro, id_marca, nome FROM Carro";
        List<Carro> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Carro c1 = new Carro(rs.getInt("id_marca"), rs.getString("nome"));
                c1.setIdCarro(rs.getInt("id_carro"));
                L.add(c1);
            }
        } catch (SQLException e) {
            System.out.println("Erro list Carro: " + e.getMessage());
        }
        return L;
    }

    // ========== MÉTODOS PARA ASSOCIAÇÃO CARRO-PILOTO ========== //

    /**
     * Associa um carro a um piloto na tabela Carro_Piloto
     */
    public boolean associarPiloto(int idCarro, int idPiloto) {
        String sql = "INSERT INTO Carro_Piloto (id_carro, id_piloto) VALUES (?, ?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, idCarro);
            p.setInt(2, idPiloto);
            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao associar carro-piloto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas as associações carro-piloto existentes
     */
    public List<String> listarCarrosComPilotos() {
        String sql = "SELECT c.nome AS carro, p.nome AS piloto " +
                "FROM Carro_Piloto cp " +
                "JOIN Carro c ON cp.id_carro = c.id_carro " +
                "JOIN Piloto p ON cp.id_piloto = p.id_piloto";

        List<String> resultados = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                resultados.add("Carro: " + rs.getString("carro") +
                        " | Piloto: " + rs.getString("piloto"));
            }
        } catch (SQLException e) {
            System.out.println("Erro no JOIN Carro-Piloto: " + e.getMessage());
        }
        return resultados;
    }

    /**
     * Verifica se um carro já está associado a algum piloto
     */
    public boolean verificarAssociacoes(int idCarro) {
        String sql = "SELECT COUNT(*) FROM Carro_Piloto WHERE id_carro = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, idCarro);
            ResultSet rs = p.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao verificar associações: " + e.getMessage());
            return false;
        }
    }
}