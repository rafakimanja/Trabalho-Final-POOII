package main.exemplo_javafx.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection getConnection(){
        String login = "postgres";
        String senha = "postgres";
        String urlcon = "jdbc:postgresql://localhost:5432/projeto-teste";
        try{
            return DriverManager.getConnection(urlcon, login, senha);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}