/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

import edu.esab.rbp.Classificador16Bits;

/**
 * Classifica as saidas dos classificadores de imagens em numeros.
 *
 * @author Helio Rodrigues
 */
public class ClassificadorNumericoBinario {

    private final Classificador16Bits[] classificador = new Classificador16Bits[4];

    /**
     * Constroi o classificador numerico.
     */
    public ClassificadorNumericoBinario() {
        for (int i = 0; i < classificador.length; i++) {
            classificador[i] = new Classificador16Bits();
        }
    }

    /**
     * Treina a rede para uma determinada entrada e saida.
     *
     * @param entrada A entrada a ser treinada.
     * @param rotulo A saida esperada.
     */
    public void treinar(int[] entrada, int rotulo) {
        int[] saida = converterNumero(rotulo);

        for (int i = 0; i < saida.length; i++) {
            classificador[i].aprender(entrada, saida[i]);
        }
    }

    /**
     * Classifica uma entrada.
     *
     * @param entrada A entrada a ser classificada.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @return A classificacao da entrada.
     */
    public int classificar(int[] entrada, boolean adivinhar) {
        int[] saida = new int[classificador.length];

        for (int i = 0; i < classificador.length; i++) {
            saida[i] = classificador[i].classificar(entrada, adivinhar);
        }

        return converterNumero(saida);
    }

    /**
     * Converte um vetor de bytes em inteiro.
     *
     * @param bytes Os bytes a serem convertidos.
     * @return O numero correspondente.
     */
    public int converterNumero(int[] bytes) {
        int numero = 0;
        int contador = 0;

        for (int i = bytes.length - 1; i >= 0; i--) {
            numero += bytes[i] * Math.pow(2, contador++);
        }

        return numero;
    }

    /**
     * Converte um numero para um vertor de bytes.
     *
     * @param numero O numero a ser convertido.
     * @return O vetor correspondente.
     */
    public int[] converterNumero(int numero) {
        switch (numero) {
            case 0:
                return new int[]{0, 0, 0, 0};
            case 1:
                return new int[]{0, 0, 0, 1};
            case 2:
                return new int[]{0, 0, 1, 0};
            case 3:
                return new int[]{0, 0, 1, 1};
            case 4:
                return new int[]{0, 1, 0, 0};
            case 5:
                return new int[]{0, 1, 0, 1};
            case 6:
                return new int[]{0, 1, 1, 0};
            case 7:
                return new int[]{0, 1, 1, 1};
            case 8:
                return new int[]{1, 0, 0, 0};
            case 9:
                return new int[]{1, 0, 0, 1};
            case 10:
                return new int[]{1, 0, 1, 0};
            case 11:
                return new int[]{1, 0, 1, 1};
            case 12:
                return new int[]{1, 1, 0, 0};
            case 13:
                return new int[]{1, 1, 0, 1};
            case 14:
                return new int[]{1, 1, 1, 0};
            case 15:
                return new int[]{1, 1, 1, 1};
        }

        return null;
    }
}
