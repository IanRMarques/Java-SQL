package br.inatel.dao;

import br.inatel.model.MarcaPatrocinador;
import java.sql.*;

public class MarcaPatrocinadorDAO {
    public boolean insert(MarcaPatrocinador mp) {
        String sql = "INSERT INTO Marca_Patrocinador (Marca_idMarca, Patrocinadores_idPatrocinadores) VALUES (?, ?)";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mp.getIdMarca());
            pstmt.setInt(2, mp.getIdPatrocinador());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir relacionamento: " + e.getMessage());
            return false;
        }
    }
}