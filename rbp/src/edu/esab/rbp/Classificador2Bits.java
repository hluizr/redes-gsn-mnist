/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

/**
 * Unidade logica da rede.
 *
 * @author Helio Rodrigues
 */
public class Classificador2Bits implements Classificador {

    public static final int TAMANHO_ENTRADA = 2;
    private final int[][] memoria = new int[2][2];

    /**
     * Inicializa a unidade logica.
     */
    public Classificador2Bits() {
        for (int i = 0; i < memoria.length; i++) {
            for (int j = 0; j < memoria[i].length; j++) {
                memoria[i][j] = INDEFINIDO;
            }
        }
    }

    /**
     * Classifica uma entrada.
     *
     * @param entrada A entrada a ser classificada.
     * @param adivinhar Atribui valor aleatorio caso esteja indefinido.
     * @return O resultado da classificacao.
     */
    @Override
    public int classificar(int[] entrada, boolean adivinhar) {
        if (entrada[0] == INDEFINIDO || entrada[1] == INDEFINIDO || memoria[entrada[0]][entrada[1]] == INDEFINIDO) {
            return adivinhar ? (int) (Math.random() * 2.0) : INDEFINIDO;
        } else {
            return memoria[entrada[0]][entrada[1]];
        }
    }

    /**
     * Aprende uma entrada.
     *
     * @param entrada A entrada a ser aprendida.
     * @param saida A saida a ser aprendida.
     */
    @Override
    public int[] aprender(int[] entrada, int saida) {
        if (entrada[0] != INDEFINIDO && entrada[1] != INDEFINIDO) {
            if (memoria[entrada[0]][entrada[1]] == saida) {
                return entrada;
            } else if (memoria[entrada[0]][entrada[1]] == INDEFINIDO) {
                memoria[entrada[0]][entrada[1]] = saida;
                return entrada;
            }
        } else if (entrada[0] == INDEFINIDO && entrada[1] != INDEFINIDO) {
            for (int i = 0; i < memoria.length; i++) {
                if (memoria[i][entrada[1]] == saida) {
                    entrada[0] = i;
                    return entrada;
                }
            }

            for (int i = 0; i < memoria.length; i++) {
                if (memoria[i][entrada[1]] == INDEFINIDO) {
                    memoria[i][entrada[1]] = saida;
                    entrada[0] = i;
                    return entrada;
                }
            }
        } else if (entrada[0] != INDEFINIDO && entrada[1] == INDEFINIDO) {
            for (int j = 0; j < memoria[entrada[0]].length; j++) {
                if (memoria[entrada[0]][j] == saida) {
                    entrada[1] = j;
                    return entrada;
                }
            }

            for (int j = 0; j < memoria[entrada[0]].length; j++) {
                if (memoria[entrada[0]][j] == INDEFINIDO) {
                    memoria[entrada[0]][j] = saida;
                    entrada[1] = j;
                    return entrada;
                }
            }
        } else if (entrada[0] == INDEFINIDO && entrada[1] == INDEFINIDO) {
            for (int i = 0; i < memoria.length; i++) {
                for (int j = 0; j < memoria[i].length; j++) {
                    if (memoria[i][j] == saida) {
                        entrada[0] = i;
                        entrada[1] = j;
                        return entrada;
                    }
                }
            }

            for (int i = 0; i < memoria.length; i++) {
                for (int j = 0; j < memoria[i].length; j++) {
                    if (memoria[i][j] == INDEFINIDO) {
                        memoria[i][j] = saida;
                        entrada[0] = i;
                        entrada[1] = j;
                        return entrada;
                    }
                }
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

        for (int i = 0; i < memoria.length; i++) {
            for (int j = 0; j < memoria[i].length; j++) {
                string += "[" + i + "" + j + "] -> " + memoria[i][j] + " ";
            }
        }

        return string;
    }

    /**
     * @return the memoria
     */
    public int[][] getMemoria() {
        return memoria;
    }
}
