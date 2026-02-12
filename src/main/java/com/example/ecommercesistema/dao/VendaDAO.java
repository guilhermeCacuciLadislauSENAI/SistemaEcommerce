package com.example.ecommercesistema.dao;

import com.example.ecommercesistema.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    // CREATE
    public void salvar(Venda venda) throws SQLException {

        String sqlVenda = """
                INSERT INTO venda (cliente_id, data)
                VALUES (?, ?)
                """;

        String sqlItem = """
                INSERT INTO item_venda (venda_id, produto_id, quantidade, preco_unitario)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = connectionFactory.getConnection()) {

            conn.setAutoCommit(false); // inicia transação

            try (
                    PreparedStatement stmtVenda =
                            conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)
            ) {

                // Salva venda
                stmtVenda.setInt(1, venda.getCliente().getId());
                stmtVenda.setDate(2, Date.valueOf(venda.getData()));

                stmtVenda.executeUpdate();

                ResultSet rs = stmtVenda.getGeneratedKeys();

                int vendaId = 0;

                if (rs.next()) {
                    vendaId = rs.getInt(1);
                }

                // Salva itens
                try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {

                    for (ItemVenda item : venda.getItens()) {

                        stmtItem.setInt(1, vendaId);
                        stmtItem.setInt(2, item.getProduto().getId());
                        stmtItem.setInt(3, item.getQuantidade());
                        stmtItem.setDouble(4, item.getPrecoUnitario());

                        stmtItem.executeUpdate();
                    }
                }

                conn.commit(); // confirma tudo

            } catch (Exception e) {

                conn.rollback(); // desfaz se der erro
                throw new SQLException("Erro ao salvar venda: " + e.getMessage());
            }

        }
    }

    // READ
    public List<Venda> listarTodos() throws SQLException {

        List<Venda> lista = new ArrayList<>();

        String sql = """
                SELECT v.id, v.data,
                       c.id AS cliente_id,
                       c.nome AS cliente_nome
                FROM venda v
                INNER JOIN cliente c ON v.cliente_id = c.id
                """;

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));

                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setData(rs.getDate("data").toLocalDate());
                venda.setCliente(cliente);

                lista.add(venda);
            }
        }

        return lista;
    }
}