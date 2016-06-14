/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe que representa uma imagem do MNIST.
 *
 * @author Helio Rodrigues
 */
public class ImagemMnist {

    private int[][] imagem;
    private int rotulo;

    /**
     * Carrega a imagem e o rotulo correspondente.
     *
     * @param imagemStream A stream da imagem.
     * @param rotuloStream A stream do rotulo.
     * @throws IOException Erro ao ler arquivo.
     */
    public void carregar(InputStream imagemStream, InputStream rotuloStream) throws IOException {
        imagem = new int[28][28];

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                int byteLido = imagemStream.read();
                imagem[i][j] = (byteLido > 1) ? 1 : 0;
            }
        }

        rotulo = rotuloStream.read();
    }

    /**
     * Representacao textual da imagem.
     *
     * @return A representacao textual da imagem.
     */
    @Override
    public String toString() {
        String string = "Rotulo: " + rotulo;
        string += "\n\nImagem:\n";

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                string += (imagem[i][j] == 1) ? " #" : " .";
            }
            string += "\n";
        }

        return string;
    }

    /**
     * @return the imagem
     */
    public int[][] getImagem() {
        return imagem;
    }

    /**
     * @param imagem the imagem to set
     */
    public void setImagem(int[][] imagem) {
        this.imagem = imagem;
    }

    /**
     * @return the rotulo
     */
    public int getRotulo() {
        return rotulo;
    }

    /**
     * @param rotulo the rotulo to set
     */
    public void setRotulo(int rotulo) {
        this.rotulo = rotulo;
    }
}
