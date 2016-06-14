/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

import edu.esab.rbp.Classificador16Bits;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author helio.rodrigues
 */
public class ClassificadorNumericoDecimal {

    private final Classificador16Bits[] classificador = new Classificador16Bits[10];

    /**
     * Constroi o classificador numerico.
     */
    public ClassificadorNumericoDecimal() {
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
        for (int i = 0; i < classificador.length; i++) {
            classificador[i].aprender(entrada, i == rotulo ? 1 : 0);
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
        List<Integer> classificacoes = new ArrayList<>();

        for (int i = 0; i < classificador.length; i++) {
            if (classificador[i].classificar(entrada, adivinhar) == 1) {
                classificacoes.add(i);
            }
        }

        if (classificacoes.isEmpty()) {
            return -1;
        } else if (adivinhar) {
            return classificacoes.get((int) (Math.random() * (double) classificacoes.size()));
        } else {
            return classificacoes.get(0);
        }
    }
}
