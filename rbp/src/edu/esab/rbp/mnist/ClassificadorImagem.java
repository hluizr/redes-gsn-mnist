/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

import edu.esab.rbp.Classificador1024Bits;

/**
 * Classificador para as imagens do MNIST.
 *
 * @author Helio Rodrigues
 */
public class ClassificadorImagem {

    public static final int QTDE_VERDADEIRO_INICIAL = 128;
    private final Classificador1024Bits[] classificador = new Classificador1024Bits[10];
    private int totalImagensTreinadas = 0;

    /**
     * Constroi o classificador de imagens MNIST.
     */
    public ClassificadorImagem() {
        for (int i = 0; i < classificador.length; i++) {
            classificador[i] = new Classificador1024Bits();
        }
    }

    /**
     * Treina a rede para uma determinada entrada e saida.
     *
     * @param imagem A imagem a ser treinada.
     * @param rotulo O rotulo da imagem.
     */
    public void treinar(int[][] imagem, int rotulo) {
        int[] imagemEntrada = converterImagem(imagem);

        for (int i = 0; i < classificador.length; i++) {
            if (i == rotulo) {
                classificador[i].aprender(imagemEntrada, 1);
            } else if (totalImagensTreinadas > QTDE_VERDADEIRO_INICIAL) {
                classificador[i].aprender(imagemEntrada, 0);
            }
        }

        totalImagensTreinadas++;
    }

    /**
     * Classifica uma entrada.
     *
     * @param imagem A entrada a ser classificada.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @return A classificacao da entrada.
     */
    public int[] classificar(int[][] imagem, boolean adivinhar) {
        int[] imagemEntrada = converterImagem(imagem);
        int[] saida = new int[16];

        for (int i = 0; i < classificador.length; i++) {
            saida[i] = classificador[i].classificar(imagemEntrada, adivinhar);
        }

        return saida;
    }

    /**
     * Converte uma imagem do MNIST em vetor de bytes.
     *
     * @param imagemMnist A imagem a ser convertida.
     * @return O vetor de bytes.
     */
    public int[] converterImagem(int[][] imagemMnist) {
        int[] imagem = new int[1024];
        int contador = 0;

        for (int j = 0; j < 28; j++) {
            for (int k = 0; k < 28; k++) {
                imagem[contador++] = imagemMnist[j][k];
            }
        }

        return imagem;
    }

    /**
     * @return the totalImagensTreinadas
     */
    public int getTotalImagensTreinadas() {
        return totalImagensTreinadas;
    }
}
