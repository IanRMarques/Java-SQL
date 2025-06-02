package br.inatel.dao;  // Corrigido para br.inatel

import br.inatel.model.Marca;  // Corrigido o import
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {
    public boolean insertMarca(Marca marca) {
        String sql = "INSERT INTO Marca (nome, nacionalidade, quantPilotos) VALUES (?, ?, ?)";  // SQL corrigido

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Parâmetros corrigidos
            pstmt.setString(1, marca.getNome());
            pstmt.setString(2, marca.getNacionalidade());
            pstmt.setInt(3, marca.getQuantPilotos());

            pstmt.executeUpdate();

            // Obter ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    marca.setIdMarca(rs.getInt(1));
                }
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir marca: " + e.getMessage());
            return false;
        }
    }

    public List<Marca> listAll() {
        String sql = "SELECT * FROM Marca";
        List<Marca> marcas = new ArrayList<>();

        try (Connection conn = ConnectionDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Marca marca = new Marca(
                        rs.getString("nome"),
                        rs.getString("nacionalidade"),
                        rs.getInt("quantPilotos")
                );
                marca.setIdMarca(rs.getInt("idMarca"));
                marcas.add(marca);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar marcas: " + e.getMessage());
        }
        return marcas;
    }
}