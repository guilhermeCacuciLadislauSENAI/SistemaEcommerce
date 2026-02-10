package com.example.ecommercesistema.controller;

import com.example.ecommercesistema.dao.ProdutoDAO;
import com.example.ecommercesistema.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdutosController {

    @FXML private TextField nomeProduto;
    @FXML private TextField precoProduto;
    @FXML private Spinner<Integer> estoqueProduto;

    @FXML private TableView <Produto> tabelaProdutos;
    @FXML private TableColumn <Produto, Integer> colId;
    @FXML private TableColumn <Produto, String> colProduto;
    @FXML private TableColumn <Produto, Double> colPreco;
    @FXML private TableColumn <Produto, Integer> colEstoque;

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private Produto produtoSelecionado;

    @FXML
    public void initialize() {

        // Inicializa o Spinner
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        estoqueProduto.setValueFactory(valueFactory);

        // Configuração da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));

        carregarProdutos();
    }

    private void carregarProdutos() {
        try {
            tabelaProdutos.getItems().setAll(produtoDAO.listarTodos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void atualizarTabela() {
        try {
            tabelaProdutos.setItems(
                    FXCollections.observableArrayList(produtoDAO.listarTodos())
            );
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao carregar produtos");
        }
    }

    @FXML
    public void salvarProduto() {
        try {
            if (nomeProduto.getText().isEmpty() || precoProduto.getText().isEmpty()) {
                exibirAlerta("Validação", "Preencha todos os campos!");
                return;
            }

            if (produtoSelecionado == null) {
                Produto p = new Produto(
                        nomeProduto.getText(),
                        Double.parseDouble(precoProduto.getText()),
                        estoqueProduto.getValue()
                );
                produtoDAO.salvar(p);
            } else {
                produtoSelecionado.setNome(nomeProduto.getText());
                produtoSelecionado.setPreco(Double.parseDouble(precoProduto.getText()));
                produtoSelecionado.setEstoque(estoqueProduto.getValue());
                produtoDAO.atualizar(produtoSelecionado);
            }

            atualizarTabela();
            limparCampos();

        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Preço inválido!");
        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage());
        }
    }

    @FXML
    public void excluirProduto() {
        if (produtoSelecionado != null) {
            try {
                produtoDAO.deletar(produtoSelecionado.getId());
                atualizarTabela();
                limparCampos();
            } catch (Exception e) {
                exibirAlerta("Erro", e.getMessage());
            }
        } else {
            exibirAlerta("Aviso", "Selecione um produto!");
        }
    }

    @FXML
    public void selecionarItem() {
        produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            nomeProduto.setText(produtoSelecionado.getNome());
            precoProduto.setText(String.valueOf(produtoSelecionado.getPreco()));
            estoqueProduto.getValueFactory()
                    .setValue(produtoSelecionado.getEstoque());
        }
    }

    @FXML
    public void limparCampos() {
        nomeProduto.clear();
        precoProduto.clear();
        estoqueProduto.getValueFactory().setValue(0);
        produtoSelecionado = null;
        tabelaProdutos.getSelectionModel().clearSelection();
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
