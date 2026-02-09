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

    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colEstoque;

    private ProdutoDAO dao = new ProdutoDAO();
    private Produto produtoSelecionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));

        estoqueProduto.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0)
        );

        atualizarTabela();
    }

    @FXML
    private void atualizarTabela() {
        try {
            tabelaProdutos.setItems(
                    FXCollections.observableArrayList(dao.listarTodos())
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
                dao.salvar(p);
            } else {
                produtoSelecionado.setNome(nomeProduto.getText());
                produtoSelecionado.setPreco(Double.parseDouble(precoProduto.getText()));
                produtoSelecionado.setEstoque(estoqueProduto.getValue());
                dao.atualizar(produtoSelecionado);
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
                dao.deletar(produtoSelecionado.getId());
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
