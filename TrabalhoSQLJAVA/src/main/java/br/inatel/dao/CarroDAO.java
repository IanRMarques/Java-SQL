package br.inatel.dao;

import br.inatel.model.Carro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {
    public boolean insertCarro(Carro carro) {
        String sql = "INSERT INTO Carro (modelo, fabricante, ano, idPiloto) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionDAO.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, carro.getModelo());
            pstmt.setString(2, carro.getFabricante());
            pstmt.setInt(3, carro.getAno());
            pstmt.setInt(4, carro.getIdPiloto());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    carro.setIdCarro(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir carro: " + e.getMessage());
            return false;
        }
    }

    public List<Carro> listAll() {
        String sql = "SELECT * FROM Carro";
        List<Carro> carros = new ArrayList<>();

        try (Connection conn = ConnectionDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("modelo"),
                        rs.getString("fabricante"),
                        rs.getInt("ano"),
                        rs.getInt("idPiloto")
                );
                carro.setIdCarro(rs.getInt("idCarro"));
                carros.add(carro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar carros: " + e.getMessage());
        }
        return carros;
    }
}