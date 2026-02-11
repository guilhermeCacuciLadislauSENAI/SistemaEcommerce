package com.example.ecommercesistema.controller;

import com.example.ecommercesistema.dao.ClienteDAO;
import com.example.ecommercesistema.dao.ProdutoDAO;
import com.example.ecommercesistema.dao.VendaDAO;
import com.example.ecommercesistema.model.Cliente;
import com.example.ecommercesistema.model.ItemVenda;
import com.example.ecommercesistema.model.Produto;
import com.example.ecommercesistema.model.Venda;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class VendaController {

    // ================================
    // COMPONENTES DA TELA (FXML)
    // ================================

    @FXML private ComboBox<Cliente> comboCliente;
    @FXML private ComboBox<Produto> comboProduto;
    @FXML private Spinner<Integer> spinnerQuantidade;

    @FXML private TextField txtPrecoUnitario;
    @FXML private TextField txtPrecoTotal;

    @FXML private TableView<ItemVenda> tabelaVenda;
    @FXML private TableColumn<ItemVenda, String> colProduto;
    @FXML private TableColumn<ItemVenda, Integer> colQuantidade;
    @FXML private TableColumn<ItemVenda, Double> colValorUni;
    @FXML private TableColumn<ItemVenda, Double> colValorTotal;

    // ================================
    // DAOs
    // ================================

    private final ClienteDAO clienteDao = new ClienteDAO();
    private final ProdutoDAO produtoDao = new ProdutoDAO();
    private final VendaDAO vendaDao = new VendaDAO();

    // ================================
    // OBJETO QUE REPRESENTA A VENDA
    // ================================

    private Venda vendaAtual;

    // ======================================================
    // MÉTODO EXECUTADO QUANDO A TELA CARREGA
    // ======================================================

    @FXML
    public void initialize() {

        // Criamos uma venda vazia na memória
        vendaAtual = new Venda();
        vendaAtual.setData(LocalDate.now());

        // Carregar clientes no ComboBox
        comboCliente.setItems(
                FXCollections.observableArrayList(clienteDao.listarTodos())
        );

        // Carregar produtos no ComboBox
        comboProduto.setItems(
                FXCollections.observableArrayList(produtoDao.listarTodos())
        );

        // Configuração das colunas da tabela

        colProduto.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getProduto().getNome()
                )
        );

        colQuantidade.setCellValueFactory(
                new PropertyValueFactory<>("quantidade")
        );

        colValorUni.setCellValueFactory(
                new PropertyValueFactory<>("precoUnitario")
        );

        colValorTotal.setCellValueFactory(
                new PropertyValueFactory<>("valorTotal")
        );

        // Configuração do Spinner
        spinnerQuantidade.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)
        );
    }

    // ======================================================
    // ADICIONAR ITEM À VENDA
    // ======================================================

    @FXML
    public void adicionarItem() {

        Produto produtoSelecionado = comboProduto.getValue();

        if (produtoSelecionado == null) {
            exibirAlerta("Validação", "Selecione um produto!");
            return;
        }

        int quantidade = spinnerQuantidade.getValue();

        ItemVenda item = new ItemVenda();
        item.setProduto(produtoSelecionado);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produtoSelecionado.getPreco());

        // Regra de negócio pertence ao Model
        vendaAtual.adicionarItem(item);

        atualizarTabela();
        atualizarTotal();
    }

    // ======================================================
    // REMOVER ITEM
    // ======================================================

    @FXML
    public void removerItem() {

        ItemVenda itemSelecionado =
                tabelaVenda.getSelectionModel().getSelectedItem();

        if (itemSelecionado == null) {
            exibirAlerta("Aviso", "Selecione um item!");
            return;
        }

        vendaAtual.removerItem(itemSelecionado);

        atualizarTabela();
        atualizarTotal();
    }

    // ======================================================
    // FINALIZAR VENDA
    // ======================================================

    @FXML
    public void finalizarVenda() {

        Cliente clienteSelecionado = comboCliente.getValue();

        if (clienteSelecionado == null) {
            exibirAlerta("Validação", "Selecione um cliente!");
            return;
        }

        if (vendaAtual.getItens().isEmpty()) {
            exibirAlerta("Validação", "Adicione pelo menos um produto!");
            return;
        }

        vendaAtual.setCliente(clienteSelecionado);

        try {
            vendaDao.salvar(vendaAtual);
            exibirAlerta("Sucesso", "Venda salva com sucesso!");
            limparTela();
        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage());
        }
    }

    // ======================================================
    // MÉTODOS AUXILIARES
    // ======================================================

    private void atualizarTabela() {
        tabelaVenda.setItems(
                FXCollections.observableArrayList(vendaAtual.getItens())
        );
    }

    private void atualizarTotal() {

        double total = 0;

        for (ItemVenda item : vendaAtual.getItens()) {
            total += item.getValorTotal();
        }

        txtPrecoTotal.setText(String.valueOf(total));
    }

    private void limparTela() {

        vendaAtual = new Venda();
        vendaAtual.setData(LocalDate.now());

        tabelaVenda.getItems().clear();
        comboCliente.getSelectionModel().clearSelection();
        comboProduto.getSelectionModel().clearSelection();
        txtPrecoTotal.clear();
    }

    // ALERTA PADRONIZADO (ERROR)
    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
