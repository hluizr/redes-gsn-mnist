/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author helio.rodrigues
 */
public class Utilidades {

//    private static final long SEMENTE_ALEATORIA = 6857232179658374562L;
    private static final long SEMENTE_ALEATORIA = System.currentTimeMillis();

    /**
     *
     * @param vetor
     * @return
     */
    public static int[] embaralharVetor(int[] vetor) {
        List<Integer> temp = new ArrayList<>(vetor.length);

        for (int i = 0; i < vetor.length; i++) {
            temp.add(i);
        }

        Collections.shuffle(temp, new Random(SEMENTE_ALEATORIA));
        int[] vetorEmbaralhado = new int[vetor.length];

        for (int i = 0; i < vetor.length; i++) {
            vetorEmbaralhado[i] = vetor[temp.get(i)];
        }

        return vetorEmbaralhado;
    }

    /**
     *
     * @param vetor
     * @param tamanho
     * @return
     */
    public static int[] redimensionarVetor(int[] vetor, int tamanho) {
        int[] novoVetor = new int[tamanho];

        for (int i = 0; i < vetor.length; i++) {
            novoVetor[i] = vetor[i];
        }

        return novoVetor;
    }

    public static void imprimirImagem(int[][] imagem) {
        for (int i = 0; i < imagem.length; i++) {
            for (int j = 0; j < imagem[i].length; j++) {
                System.out.print(imagem[i][j] == 1 ? "@" : ".");
            }

            System.out.println();
        }

        System.out.println();
    }

    /**
     *
     * @param vetor
     */
    public static void imprimirVetor(int[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i]);
        }

        System.out.println();
    }

    /**
     *
     * @param imagemMnist
     * @return
     */
    public static int[] converterImagemEntrada(int[][] imagemMnist) {
        int[] imagem = new int[1024];
        int im = 0;

        for (int i = 0; i < imagemMnist.length; i++) {
            for (int j = 0; j < imagemMnist[i].length; j++) {
                imagem[im++] = imagemMnist[i][j];
            }
        }

        return embaralharVetor(imagem);
    }

    /**
     *
     * @param tamanho
     * @return
     */
    public static int[] criarVetorAleatorio(int tamanho) {
        int[] vetor = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            vetor[i] = (int) (Math.random() * 2.0);
        }

        return vetor;
    }

    /**
     *
     * @param vetor
     * @return
     */
    public static int[] espelharVetor(int[] vetor) {
        int[] espelho = new int[vetor.length];

        for (int i = 0; i < vetor.length; i++) {
            espelho[i] = vetor[i] == 1 ? 0 : 1;
        }

        return espelho;
    }

    /**
     *
     * @param saida
     * @param classe
     * @return
     */
    public static int somarSemelhancas(int[] saida, int[] classe) {
        int somatorio = 0;

        for (int j = 0; j < saida.length; j++) {
            if (saida[j] == classe[j]) {
                somatorio++;
            }
        }

        return somatorio;
    }
    
    /**
     * Converte um numero para um vertor de bytes.
     *
     * @param numero O numero a ser convertido.
     * @return O vetor correspondente.
     */
    public static int[] converterNumero(int numero) {
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
