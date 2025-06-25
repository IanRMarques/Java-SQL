package br.inatel.dao;

import br.inatel.model.PilotoEquipamentoInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotoEquipamentoDAO {

    // Método para inserir uma associação entre Piloto e Equipamento
    public boolean insert(int idPiloto, int idEquipamento) {
        String sql = "INSERT INTO Piloto_Equipamento (id_piloto, id_equipamento) VALUES (?, ?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idPiloto);
            p.setInt(2, idEquipamento);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao associar Piloto-Equipamento: " + e.getMessage());
            return false;
        }
    }

    // Método para remover uma associação entre Piloto e Equipamento
    public boolean delete(int idPiloto, int idEquipamento) {
        String sql = "DELETE FROM Piloto_Equipamento WHERE id_piloto = ? AND id_equipamento = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idPiloto);
            p.setInt(2, idEquipamento);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao desassociar Piloto-Equipamento: " + e.getMessage());
            return false;
        }
    }

    // Método para listar os equipamentos associados a pilotos
    public List<PilotoEquipamentoInfo> listEquipamentosPorPiloto() {
        String sql = "SELECT p.nome AS pilotoNome, e.nome AS equipamentoNome " +
                "FROM Piloto_Equipamento pe " +
                "JOIN Piloto p ON pe.id_piloto = p.id_piloto " +
                "JOIN Equipamento_Seguranca e ON pe.id_equipamento = e.id_equipamento";

        List<PilotoEquipamentoInfo> associacoes = new ArrayList<>();

        // Conexão e execução da consulta
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            // Verificando se há resultados
            if (!rs.next()) {
                System.out.println("Nenhuma associação encontrada.");
            } else {
                do {
                    PilotoEquipamentoInfo info = new PilotoEquipamentoInfo(
                            rs.getString("pilotoNome"),
                            rs.getString("equipamentoNome")
                    );
                    associacoes.add(info);
                    System.out.println("Piloto: " + rs.getString("pilotoNome") + ", Equipamento: " + rs.getString("equipamentoNome")); // Log para depuração
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar associações Piloto-Equipamento: " + e.getMessage());
        }
        return associacoes;
    }
}
