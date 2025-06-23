// MarcaDAO.java
package br.inatel.dao;

import br.inatel.model.Marca;
import br.inatel.model.MarcaPatrocinadorInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {
    public boolean insertMarca(Marca m) {
        String sql = "INSERT INTO Marca (nome) VALUES (?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, m.getNome());
            p.executeUpdate();
            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) m.setIdMarca(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserir Marca: " + e.getMessage());
            return false;
        }
    }

    public boolean updateNome(int id, String nome) {
        String sql = "UPDATE Marca SET nome = ? WHERE id_marca = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, nome);
            p.setInt(2, id);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro update Marca: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMarca(int id) {
        String sql = "DELETE FROM Marca WHERE id_marca = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro delete Marca: " + e.getMessage());
            return false;
        }
    }

    public Marca findById(int id) {
        String sql = "SELECT id_marca, nome FROM Marca WHERE id_marca = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Marca m = new Marca(rs.getString("nome"));
                    m.setIdMarca(rs.getInt("id_marca"));
                    return m;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro find Marca: " + e.getMessage());
        }
        return null;
    }

    public List<Marca> listAll() {
        String sql = "SELECT id_marca, nome FROM Marca";
        List<Marca> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Marca m = new Marca(rs.getString("nome"));
                m.setIdMarca(rs.getInt("id_marca"));
                L.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro list Marca: " + e.getMessage());
        }
        return L;
    }

    public List<MarcaPatrocinadorInfo> listMarcasComPatrocinador() {
        String sql =
                "SELECT m.nome AS marcaNome, p.nome AS patrocinadorNome " +
                        "FROM Marca m " +
                        "JOIN Marca_Patrocinador mp ON m.id_marca = mp.id_marca " +
                        "JOIN Patrocinador p ON p.id_patrocinador = mp.id_patrocinador";
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
            System.out.println("Erro JOIN Marca–Patrocinador: " + e.getMessage());
        }
        return L;
    }
}
