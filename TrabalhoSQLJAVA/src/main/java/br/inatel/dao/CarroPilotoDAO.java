package br.inatel.dao;

import br.inatel.model.CarroPilotoInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroPilotoDAO {

    // Método para inserir a associação entre Carro e Piloto
    public boolean insert(int idCarro, int idPiloto) {
        // Verificar se o carro existe
        if (!verificarExistenciaCarro(idCarro)) {
            System.out.println("Carro não encontrado!");
            return false;
        }

        // Verificar se o piloto existe
        if (!verificarExistenciaPiloto(idPiloto)) {
            System.out.println("Piloto não encontrado!");
            return false;
        }

        String sql = "INSERT INTO Carro_Piloto (id_carro, id_piloto) VALUES (?, ?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idCarro);
            p.setInt(2, idPiloto);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao associar Carro-Piloto: " + e.getMessage());
            return false;
        }
    }

    // Método para remover a associação entre Carro e Piloto
    public boolean delete(int idCarro, int idPiloto) {
        String sql = "DELETE FROM Carro_Piloto WHERE id_carro = ? AND id_piloto = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idCarro);
            p.setInt(2, idPiloto);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao desassociar Carro-Piloto: " + e.getMessage());
            return false;
        }
    }

    // Método para listar carros e pilotos associados
    public List<CarroPilotoInfo> listCarrosComPilotos() {
        String sql = "SELECT c.nome AS carroNome, p.nome AS pilotoNome " +
                "FROM Carro_Piloto cp " +
                "JOIN Carro c ON cp.id_carro = c.id_carro " +
                "JOIN Piloto p ON cp.id_piloto = p.id_piloto";

        List<CarroPilotoInfo> associacoes = new ArrayList<>();

        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while(rs.next()) {
                associacoes.add(new CarroPilotoInfo(
                        rs.getString("carroNome"),
                        rs.getString("pilotoNome")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar associações Carro-Piloto: " + e.getMessage());
        }
        return associacoes;
    }

    // Método para verificar se o carro existe
    private boolean verificarExistenciaCarro(int idCarro) {
        String sql = "SELECT COUNT(*) FROM Carro WHERE id_carro = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idCarro);
            ResultSet rs = p.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Carro existe
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar carro: " + e.getMessage());
        }
        return false; // Carro não existe
    }

    // Método para verificar se o piloto existe
    private boolean verificarExistenciaPiloto(int idPiloto) {
        String sql = "SELECT COUNT(*) FROM Piloto WHERE id_piloto = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idPiloto);
            ResultSet rs = p.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Piloto existe
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar piloto: " + e.getMessage());
        }
        return false; // Piloto não existe
    }
}
