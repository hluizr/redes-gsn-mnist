/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

/**
 * Classificador para padroes de 128 entradas.
 *
 * @author Helio Rodrigues
 */
public class Classificador128Bits implements Classificador {

    public static final int TAMANHO_ENTRADA = 128;

    private final Classificador64Bits[] unidadeEntrada = new Classificador64Bits[2];
    private final Classificador2Bits unidadeSaida;

    /**
     * Inicializa a unidade logica.
     */
    public Classificador128Bits() {
        for (int i = 0; i < unidadeEntrada.length; i++) {
            unidadeEntrada[i] = new Classificador64Bits();
        }

        unidadeSaida = new Classificador2Bits();
    }

    /**
     * Classifica uma entrada.
     *
     * @param entrada A entrada a ser classificada.
     * @param adivinhar Atribui valor aleatorio caso esteja indefinido.
     * @return A classificacao.
     */
    @Override
    public int classificar(int[] entrada, boolean adivinhar) {
        // Quebra a entrada em dois.
        int contEntrada = 0;
        int[][] tempEntrada = new int[2][TAMANHO_ENTRADA / 2];

        for (int i = 0; i < tempEntrada.length; i++) {
            for (int j = 0; j < tempEntrada[i].length; j++) {
                tempEntrada[i][j] = entrada[contEntrada++];
            }
        }

        // Obtem da saida as entradas intermediarias.
        return unidadeSaida.classificar(new int[]{unidadeEntrada[0].classificar(tempEntrada[0], adivinhar),
            unidadeEntrada[1].classificar(tempEntrada[1], adivinhar)}, adivinhar);
    }

    /**
     * Associa uma entrada com uma saida.
     *
     * @param entrada A entrada a ser aprendida.
     * @param saida A saida a ser aprendida.
     * @return A entrada associada.
     */
    @Override
    public int[] aprender(int[] entrada, int saida) {
        // Quebra a entrada em dois.
        int contEntrada = 0;
        int[][] tempEntrada = new int[2][TAMANHO_ENTRADA / 2];

        for (int i = 0; i < tempEntrada.length; i++) {
            for (int j = 0; j < tempEntrada[i].length; j++) {
                tempEntrada[i][j] = entrada[contEntrada++];
            }
        }

        // Obtem da saida as entradas intermediarias.
        int[] saidaIntermediaria = unidadeSaida.aprender(new int[]{unidadeEntrada[0].classificar(tempEntrada[0], false),
            unidadeEntrada[1].classificar(tempEntrada[1], false)}, saida);

        // Associa as entradas intermediarias com as entradas.
        tempEntrada[0] = unidadeEntrada[0].aprender(tempEntrada[0], saidaIntermediaria[0]);
        tempEntrada[1] = unidadeEntrada[1].aprender(tempEntrada[1], saidaIntermediaria[1]);

        // Retorna a nova configuracao de entradas.
        entrada = new int[TAMANHO_ENTRADA];
        contEntrada = 0;

        for (int i = 0; i < tempEntrada.length; i++) {
            for (int j = 0; j < tempEntrada[i].length; j++) {
                entrada[contEntrada++] = tempEntrada[i][j];
            }
        }

        return entrada;
    }

    /**
     * Reprensentacao textual da unidade.
     *
     * @return A reprensentacao textual da unidade.
     */
    @Override
    public String toString() {
        String string = "(" + TAMANHO_ENTRADA + ") ";

        for (int i = 0; i < unidadeEntrada.length; i++) {
            string += unidadeEntrada[i];
        }

        string += "\n" + unidadeSaida;
        return string + "\n";
    }
}
