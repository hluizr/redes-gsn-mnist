/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

/**
 * Interface para uma unidade logica.
 *
 * @author Helio Rodrigues
 */
public interface Classificador {

    public static final int FALSO = 0;
    public static final int INDEFINIDO = -1;
    public static final int VERDADEIRO = 1;

    /**
     * Classifica uma entrada.
     *
     * @param entrada A entrada a ser classificada.
     * @param adivinhar Atribui valor aleatorio caso esteja indefinido.
     * @return A classificacao.
     */
    public int classificar(int[] entrada, boolean adivinhar);

    /**
     * Associa uma entrada com uma saida.
     *
     * @param entrada A entrada a ser aprendida.
     * @param saida A saida a ser aprendida.
     * @return A entrada associada.
     */
    public int[] aprender(int[] entrada, int saida);
}
