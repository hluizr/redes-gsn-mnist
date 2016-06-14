/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

/**
 *
 * @author helio.rodrigues
 */
public interface ClassificadorMnist {

    /**
     * Aprende a imagem de acordo com o rotulo.
     *
     * @param imagemMnist A imagem do MNIST a ser aprendida.
     */
    public void aprender(ImagemMnist imagemMnist);

    /**
     * Classifica uma imagem.
     *
     * @param imagemMnist A imagem a ser classificada.
     * @param adivinhar Se deve-se adivinhar valores indefinidos.
     */
    public void calibrar(ImagemMnist imagemMnist, boolean adivinhar);

    /**
     * Classifica uma imagem.
     *
     * @param imagemMnist A imagem a ser classificada.
     * @param adivinhar Se deve-se adivinhar valores indefinidos.
     * @return A classe da imagem.
     */
    public int classificar(ImagemMnist imagemMnist, boolean adivinhar);
}
