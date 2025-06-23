// src/main/java/br/inatel/dao/PatrocinadorDAO.java
package br.inatel.dao;

import br.inatel.model.MarcaPatrocinadorInfo;
import br.inatel.model.Patrocinador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatrocinadorDAO {

    public boolean insertPatrocinador(Patrocinador p1) {
        String sql = "INSERT INTO Patrocinador (nome) VALUES (?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            p.setString(1, p1.getNome());
            p.executeUpdate();

            // CORREÇÃO AQUI: usar getGeneratedKeys(), sem o ".get."
            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) {
                    p1.setIdPatrocinador(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserir Patrocinador: " + e.getMessage());
            return false;
        }
    }

    public boolean updateNome(int id, String novo) {
        String sql = "UPDATE Patrocinador SET nome = ? WHERE id_patrocinador = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, novo);
            p.setInt(2, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro update Patrocinador: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePatrocinador(int id) {
        String sql = "DELETE FROM Patrocinador WHERE id_patrocinador = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro delete Patrocinador: " + e.getMessage());
            return false;
        }
    }

    public Patrocinador findById(int id) {
        String sql = "SELECT id_patrocinador, nome FROM Patrocinador WHERE id_patrocinador = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Patrocinador p1 = new Patrocinador(rs.getString("nome"));
                    p1.setIdPatrocinador(rs.getInt("id_patrocinador"));
                    return p1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro find Patrocinador: " + e.getMessage());
        }
        return null;
    }

    public List<Patrocinador> listAll() {
        String sql = "SELECT id_patrocinador, nome FROM Patrocinador";
        List<Patrocinador> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                Patrocinador p1 = new Patrocinador(rs.getString("nome"));
                p1.setIdPatrocinador(rs.getInt("id_patrocinador"));
                L.add(p1);
            }
        } catch (SQLException e) {
            System.out.println("Erro list Patrocinador: " + e.getMessage());
        }
        return L;
    }

    public List<MarcaPatrocinadorInfo> listPatrocinadoresComMarcas() {
        String sql =
                "SELECT m.nome AS marcaNome, p.nome AS patrocinadorNome " +
                        "FROM Patrocinador p " +
                        "JOIN Marca_Patrocinador mp ON p.id_patrocinador = mp.id_patrocinador " +
                        "JOIN Marca m ON m.id_marca = mp.id_marca";

        List<MarcaPatrocinadorInfo> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                L.add(new MarcaPatrocinadorInfo(
                        rs.getString("marcaNome"),
                        rs.getString("patrocinadorNome")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro JOIN Patrocinador–Marca: " + e.getMessage());
        }
        return L;
    }
}
