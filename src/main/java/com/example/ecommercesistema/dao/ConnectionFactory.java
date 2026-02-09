package com.example.ecommercesistema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection(){
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/crud_java_guilherme", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão" + e.getMessage());
        }
    }
}