// CarroPilotoDAO.java
package br.inatel.dao;

import br.inatel.model.CarroPilotoInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroPilotoDAO {
    public boolean insert(int idCarro, int idPiloto) {
        String sql = "INSERT INTO Carro_Piloto (id_carro, id_piloto) VALUES (?, ?)";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idCarro);
            p.setInt(2, idPiloto);
            p.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro inserir Carro_Piloto: "+ e.getMessage());
            return false;
        }
    }
    public boolean delete(int idCarro, int idPiloto) {
        String sql = "DELETE FROM Carro_Piloto WHERE id_carro = ? AND id_piloto = ?";
        try (Connection c = ConnectionDAO.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idCarro);
            p.setInt(2, idPiloto);
            return p.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println("Erro delete Carro_Piloto: "+ e.getMessage());
            return false;
        }
    }
    public List<CarroPilotoInfo> listCarrosComPilotos() {
        String sql =
                "SELECT c.nome AS carroNome, p.nome AS pilotoNome " +
                        "FROM Carro_Piloto cp " +
                        "JOIN Carro c ON cp.id_carro = c.id_carro " +
                        "JOIN Piloto p ON cp.id_piloto = p.id_piloto";
        List<CarroPilotoInfo> L = new ArrayList<>();
        try (Connection c = ConnectionDAO.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                L.add(new CarroPilotoInfo(
                        rs.getString("carroNome"),
                        rs.getString("pilotoNome")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro JOIN Carro–Piloto: "+ e.getMessage());
        }
        return L;
    }
}
