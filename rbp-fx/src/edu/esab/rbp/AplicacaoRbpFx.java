/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aplicacao de demonstracao de duas arquiteturas de redes GSN.
 *
 * @author Helio Rodrigues
 */
public class AplicacaoRbpFx extends Application {

    /**
     * Inicia a aplicacao.
     *
     * @param stage A janela principal.
     * @throws Exception Erro ao carregar interface.
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("ESAB - Escola Superior Aberta do Brasil");

        Parent root = FXMLLoader.load(getClass().getResource("ClassificadorNumerosView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Inicia a aplicacao.
     *
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
