package com.example.ecommercesistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;

public class MainController {
    @FXML
    private BorderPane areaCentral;

    @FXML
    public void abrirHome(){
        carregarTela("Home.fxml");
    }

    @FXML
    public void abrirProdutos(){ carregarTela("Produtos.fxml"); }

    @FXML
    public void abrirClientes(){
        carregarTela("Clientes.fxml");
    }

    @FXML
    public void abrirNovaVenda(){ carregarTela("Venda.fxml"); }

    @FXML
    public void abrirRelatorioVendas(){ carregarTela("RelatorioVendas.fxml"); }

    @FXML
    public void initialize(){
        carregarTela("Home.fxml");
    }

    public void abrirAjuda (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre o sistema");
        alert.setHeaderText("Informações do sistema");
        alert.setContentText(
                "Nome: Sistema de Vendas\n" +
                        "Versão: 1.0\n" +
                        "Desenvolvedor: Guilherme Caçuci" +
                        "Ano: 2026"
        );
        alert.showAndWait();
    }

    private void carregarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/" + fxml)
            );

            areaCentral.setCenter(loader.load());

            // se for a tela de clientes, injeta o controller principal
            Object controller = loader.getController();
            if (controller instanceof ClientesController clientesController) {
                clientesController.setMainController(this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
