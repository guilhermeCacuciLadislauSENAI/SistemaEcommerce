package com.example.ecommercesistema.dao;

import com.example.ecommercesistema.model.Cliente;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    // CREATE
    public void salvar (Cliente cliente) throws SQLException {
        String sql = """
                INSERT INTO cliente (nome, cpf, telefone, email, endereco)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = connectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());

            stmt.executeUpdate();
        }
    }

    //READ
    public List<Cliente> listarTodos () throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = connectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                c.setEndereco(rs.getString("endereco"));

                lista.add(c);
            }

        }
        return lista;
    }

    //UPDATE
    public void atualizar (Cliente cliente) throws SQLException {
        String sql = """
                UPDATE cliente
                SET nome = ?, cpf = ?, telefone = ?, email = ?, endereco = ?
                WHERE id = ?
                """;

        try (Connection conn = connectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());
            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();
        }
    }

    //DELETE
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
