package com.example.ecommercesistema.controller;

import com.example.ecommercesistema.dao.ClienteDAO;
import com.example.ecommercesistema.model.Cliente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ClientesController {
    @FXML private TextField nomeCliente;
    @FXML private TextField cpfCliente;
    @FXML private TextField telefoneCliente;
    @FXML private TextField emailCliente;
    @FXML private TextField enderecoCliente;

    @FXML private TableView <Cliente> tabelaDadosClientes;
    @FXML private TableColumn <Cliente, Integer> colId;
    @FXML private TableColumn <Cliente, String> colNome;
    @FXML private TableColumn <Cliente, String> colCpf;
    @FXML private TableColumn <Cliente, String> colTelefone;
    @FXML private TableColumn <Cliente, String> colEmail;
    @FXML private TableColumn <Cliente, String> colEndereco;

    private final ClienteDAO clienteDao = new ClienteDAO();
    private Cliente clienteSelecionado;

    @FXML
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        carregarClientes();
    }

    public void carregarClientes(){
        try{
            tabelaDadosClientes.getItems().setAll(clienteDao.listarTodos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void atualizarTabela(){
        try{
            tabelaDadosClientes.setItems(FXCollections.observableArrayList(clienteDao.listarTodos()));
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao carregar os clientes");
        }
    }

    @FXML
    public void salvarClientes(){
        try{
            if (nomeCliente.getText().isEmpty() || cpfCliente.getText().isEmpty() || telefoneCliente.getText().isEmpty()
            || emailCliente.getText().isEmpty() || enderecoCliente.getText().isEmpty()){
                exibirAlerta("Validação", "Preencha todos os campos");
                return;
            }

            if (clienteSelecionado == null){
                Cliente c = new Cliente(
                        nomeCliente.getText(),
                        cpfCliente.getText(),
                        telefoneCliente.getText(),
                        emailCliente.getText(),
                        enderecoCliente.getText()
                );
                clienteDao.salvar(c);
            } else {
                clienteSelecionado.setNome(nomeCliente.getText());
                clienteSelecionado.setCpf(cpfCliente.getText());
                clienteSelecionado.setTelefone(telefoneCliente.getText());
                clienteSelecionado.setEmail(emailCliente.getText());
                clienteSelecionado.setEndereco(enderecoCliente.getText());
                clienteDao.atualizar(clienteSelecionado);
            }
            atualizarTabela();
            limparCampos();
        } catch (Exception e){
            exibirAlerta("Erro", e.getMessage());
        }
    }

    @FXML
    public void excluirCliente () {
        if (clienteSelecionado != null) {
            try{
                clienteDao.deletar(clienteSelecionado.getId());
                atualizarTabela();
                limparCampos();
            } catch (Exception e) {
                exibirAlerta("Erro", e.getMessage());
            }
        } else {
            exibirAlerta("Aviso", "Selecione um cliente!");
        }
    }

    @FXML
    public void selecionarCliente() {
        clienteSelecionado = tabelaDadosClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            nomeCliente.setText(clienteSelecionado.getNome());
            cpfCliente.setText(clienteSelecionado.getCpf());
            telefoneCliente.setText(clienteSelecionado.getTelefone());
            emailCliente.setText(clienteSelecionado.getEmail());
            enderecoCliente.setText(clienteSelecionado.getEndereco());
        }
    }

    @FXML
    public void limparCampos () {
        nomeCliente.clear();
        cpfCliente.clear();
        telefoneCliente.clear();
        emailCliente.clear();
        enderecoCliente.clear();
        clienteSelecionado = null;
        tabelaDadosClientes.getSelectionModel().clearSelection();
    }
    private void exibirAlerta (String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
