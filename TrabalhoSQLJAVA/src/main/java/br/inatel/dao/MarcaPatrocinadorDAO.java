// src/main/java/br/inatel/dao/MarcaPatrocinadorDAO.java
package br.inatel.dao;

import br.inatel.model.MarcaPatrocinador;

import java.sql.*;

public class MarcaPatrocinadorDAO {

    public boolean insert(MarcaPatrocinador mp) {
        String sql = "INSERT INTO Marca_Patrocinador (id_marca, id_patrocinador) VALUES (?, ?)";
        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mp.getIdMarca());
            ps.setInt(2, mp.getIdPatrocinador());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir relacionamento: " + e.getMessage());
            return false;
        }
    }
}