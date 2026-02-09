package com.example.ecommercesistema.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProdutosController {
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void voltarParaHome (ActionEvent event){
        mainController.abrirHome();
    }


}
