package br.inatel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class ConnectionDAO {
    private static final String DATABASE = "ProjetoSQL";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/" + DATABASE + "?useSSL=false";

    Connection con; // conexão
    PreparedStatement pst; // declaração(query) preparada
    Statement st; // declaração(query)
    ResultSet rs; // resultado

    public void connectToDb() {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado com sucesso!");
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}