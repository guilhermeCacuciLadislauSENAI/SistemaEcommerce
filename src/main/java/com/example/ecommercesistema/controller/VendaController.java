package com.example.ecommercesistema.controller;

import com.example.ecommercesistema.dao.ClienteDAO;
import com.example.ecommercesistema.dao.ProdutoDAO;
import com.example.ecommercesistema.dao.VendaDAO;
import com.example.ecommercesistema.model.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaController {

    @FXML private ComboBox<Cliente> comboCliente;
    @FXML private ComboBox<Produto> comboProduto;
    @FXML private Spinner<Integer> spinnerQuantidade;
    @FXML private DatePicker dateData;

    @FXML private TableView<ItemVenda> tabelaItens;
    @FXML private TableColumn<ItemVenda, String> colProduto;
    @FXML private TableColumn<ItemVenda, Integer> colQuantidade;
    @FXML private TableColumn<ItemVenda, Double> colPrecoUnitario;
    @FXML private TableColumn<ItemVenda, Double> colPrecoTotal;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final VendaDAO vendaDAO = new VendaDAO();

    private List<ItemVenda> listaItens = new ArrayList<>();

    @FXML
    public void initialize() {

        // Configuração do Spinner
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinnerQuantidade.setValueFactory(valueFactory);

        // Configuração das colunas da tabela

        colProduto.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getProduto().getNome()
                )
        );

        colQuantidade.setCellValueFactory(
                new PropertyValueFactory<>("quantidade")
        );

        colPrecoUnitario.setCellValueFactory(
                new PropertyValueFactory<>("precoUnitario")
        );

        colPrecoTotal.setCellValueFactory(
                new PropertyValueFactory<>("subTotal")
        );

        carregarClientes();
        carregarProdutos();
    }

    private void carregarClientes() {
        try {
            comboCliente.setItems(
                    FXCollections.observableArrayList(clienteDAO.listarTodos())
            );
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao carregar clientes");
        }
    }

    private void carregarProdutos() {
        try {
            comboProduto.setItems(
                    FXCollections.observableArrayList(produtoDAO.listarTodos())
            );
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao carregar produtos");
        }
    }

    @FXML
    private void adicionarItem() {

        Produto produto = comboProduto.getValue();
        int quantidade = spinnerQuantidade.getValue();

        if (produto == null) {
            exibirAlerta("Erro", "Selecione um produto!");
            return;
        }

        // Pega o preço atual do produto
        double preco = produto.getPreco();

        ItemVenda item = new ItemVenda(produto, quantidade, preco);

        listaItens.add(item);

        atualizarTabela();
    }

    @FXML
    private void removerItem() {

        ItemVenda selecionado =
                tabelaItens.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            listaItens.remove(selecionado);
            atualizarTabela();
        } else {
            exibirAlerta("Erro", "Selecione um item!");
        }
    }

    private void atualizarTabela() {
        tabelaItens.setItems(
                FXCollections.observableArrayList(listaItens)
        );
    }

    @FXML
    private void salvarVenda() {

        try {

            Cliente cliente = comboCliente.getValue();
            LocalDate data = dateData.getValue();

            if (cliente == null || data == null || listaItens.isEmpty()) {
                exibirAlerta("Erro", "Preencha todos os campos e adicione itens!");
                return;
            }

            Venda venda = new Venda(data, cliente, listaItens);

            vendaDAO.salvar(venda);

            exibirAlerta("Erro", "Venda salva com sucesso!");

            limparCampos();

        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage());
        }
    }

    private void limparCampos() {

        comboCliente.getSelectionModel().clearSelection();
        comboProduto.getSelectionModel().clearSelection();
        spinnerQuantidade.getValueFactory().setValue(1);
        dateData.setValue(null);

        listaItens.clear();
        atualizarTabela();
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}