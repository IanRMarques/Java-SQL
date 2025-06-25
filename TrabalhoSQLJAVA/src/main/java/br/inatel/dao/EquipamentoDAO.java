// src/br/inatel/dao/EquipamentoDAO.java
package br.inatel.dao;

import br.inatel.model.Equipamento;
import br.inatel.model.EquipamentoPiloto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO {

    public boolean insertEquipamento(Equipamento eq) {
        String sql = "INSERT INTO Equipamento (nome, tipo, idPiloto) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, eq.getNome());
            ps.setString(2, eq.getTipo());
            ps.setInt(3, eq.getIdPiloto());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) eq.setIdEquipamento(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir equipamento: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTipo(int idEquipamento, String novoTipo) {
        String sql = "UPDATE Equipamento SET tipo = ? WHERE idEquipamento = ?";
        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoTipo);
            ps.setInt(2, idEquipamento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tipo: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEquipamento(int idEquipamento) {
        String sql = "DELETE FROM Equipamento WHERE idEquipamento = ?";
        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEquipamento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar equipamento: " + e.getMessage());
            return false;
        }
    }

    public Equipamento findById(int idEquipamento) {
        String sql = "SELECT * FROM Equipamento WHERE idEquipamento = ?";
        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEquipamento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Equipamento eq = new Equipamento(
                            rs.getString("nome"),
                            rs.getString("tipo"),
                            rs.getInt("idPiloto")
                    );
                    eq.setIdEquipamento(idEquipamento);
                    return eq;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar equipamento: " + e.getMessage());
        }
        return null;
    }

    public List<Equipamento> listAll() {
        String sql = "SELECT * FROM Equipamento";
        List<Equipamento> lista = new ArrayList<>();
        try (Connection conn = ConnectionDAO.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Equipamento eq = new Equipamento(
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getInt("idPiloto")
                );
                eq.setIdEquipamento(rs.getInt("idEquipamento"));
                lista.add(eq);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar equipamentos: " + e.getMessage());
        }
        return lista;
    }

    public List<EquipamentoPiloto> listEquipamentosComPilotos() {
        String sql =
                "SELECT e.nome AS equipamentoNome, p.nome AS pilotoNome " +
                        "FROM Equipamento e " +
                        "JOIN Piloto p ON e.idPiloto = p.idPiloto";
        List<EquipamentoPiloto> lista = new ArrayList<>();
        try (Connection conn = ConnectionDAO.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new EquipamentoPiloto(
                        rs.getString("equipamentoNome"),
                        rs.getString("pilotoNome")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro no JOIN Equipamento–Piloto: " + e.getMessage());
        }
        return lista;
    }
}