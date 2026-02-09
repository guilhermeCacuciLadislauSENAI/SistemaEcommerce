package com.example.ecommercesistema.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;


public class ClientesController {
    private MainController mainController;

    public void setMainController (MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    public void voltarParaHome(ActionEvent event) {
        mainController.abrirHome();
    }

    @FXML private TextField nomeCliente;
    @FXML private TextField cpfCliente;
    @FXML private TextField telefoneCliente;
    @FXML private TextField emailCliente;
    @FXML private TextField enderecoCliente;

}
