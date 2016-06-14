/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

import edu.esab.rbp.Classificador32Bits;
import edu.esab.rbp.Utilidades;

/**
 *
 * @author helio.rodrigues
 */
public class ClassificadorMnist1024x32xSomador implements ClassificadorMnist {

    private final Classificador32Bits[][] classificadores = new Classificador32Bits[5][32];
    private final int[][] classes = new int[10][32];
    private final int[] limiar = new int[10];
    private boolean limiarAtualizado = false;

    /**
     * Constroi a rede.
     */
    public ClassificadorMnist1024x32xSomador() {
        // Piramide de classificacao.
        for (int i = 0; i < classificadores.length; i++) {
            for (int j = 0; j < classificadores[i].length; j++) {
                classificadores[i][j] = new Classificador32Bits();
            }
        }

        // Vetores de classes.
        for (int i = 0; i < 5; i++) {
            classes[i] = Utilidades.criarVetorAleatorio(32);
        }

        for (int i = 5; i < 10; i++) {
            classes[i] = Utilidades.espelharVetor(classes[9 - i]);
        }
    }

    /**
     * Aprende a imagem de acordo com o rotulo.
     *
     * @param imagemMnist A imagem do MNIST a ser aprendida.
     */
    public void aprender(ImagemMnist imagemMnist) {
        int[] imagem = Utilidades.converterImagemEntrada(imagemMnist.getImagem());
        int classificador = imagemMnist.getRotulo();
        int im = 0;

        if (classificador > 4) {
            classificador = 9 - classificador;
        }

        for (int i = 0; i < classificadores[classificador].length; i++) {
            int[] entrada = new int[32];

            for (int j = 0; j < entrada.length; j++) {
                entrada[j] = imagem[im++];
            }

            classificadores[classificador][i].aprender(entrada, classes[imagemMnist.getRotulo()][i]);
        }
    }

    /**
     * Classifica uma imagem.
     *
     * @param imagemMnist A imagem a ser classificada.
     * @param adivinhar Se deve-se adivinhar valores indefinidos.
     * @return A classe da imagem.
     */
    public void calibrar(ImagemMnist imagemMnist, boolean adivinhar) {
        int[] imagem = Utilidades.converterImagemEntrada(imagemMnist.getImagem());
        int[][] classificacao = new int[5][32];

        // Obtem os vetores de classificacao.
        for (int c = 0; c < classificacao.length; c++) {
            int im = 0;

            for (int i = 0; i < classificadores[c].length; i++) {
                int[] entrada = new int[32];

                for (int j = 0; j < entrada.length; j++) {
                    entrada[j] = imagem[im++];
                }

                classificacao[c][i] = classificadores[c][i].classificar(entrada, true);
            }
        }

        // Verifica qual classificacao esta mais correta.
        for (int c = 0; c < classificacao.length; c++) {
            limiar[c] += Utilidades.somarSemelhancas(classificacao[c], classes[c]);
            limiar[9 - c] += Utilidades.somarSemelhancas(classificacao[c], classes[9 - c]);
        }
    }

    /**
     * Classifica uma imagem.
     *
     * @param imagemMnist A imagem a ser classificada.
     * @param adivinhar Se deve-se adivinhar valores indefinidos.
     * @return A classe da imagem.
     */
    public int classificar(ImagemMnist imagemMnist, boolean adivinhar) {
        if (!limiarAtualizado) {
            for (int c = 0; c < limiar.length; c++) {
                limiar[c] = limiar[c] / 60000;
            }

            limiarAtualizado = true;
        }

        int[] imagem = Utilidades.converterImagemEntrada(imagemMnist.getImagem());
        int[][] classificacao = new int[5][32];

        // Obtem os vetores de classificacao.
        for (int c = 0; c < classificacao.length; c++) {
            int im = 0;

            for (int i = 0; i < classificadores[c].length; i++) {
                int[] entrada = new int[32];

                for (int j = 0; j < entrada.length; j++) {
                    entrada[j] = imagem[im++];
                }

                classificacao[c][i] = classificadores[c][i].classificar(entrada, true);
            }
        }

        // Verifica qual classificacao esta mais correta.
        int classe = 0;
        int somatorioClasse = 0;

        for (int c = 0; c < classificacao.length; c++) {
            int somatorio = Utilidades.somarSemelhancas(classificacao[c], classes[c]);
            somatorio -= limiar[c];

            if (somatorio > somatorioClasse) {
                somatorioClasse = somatorio;
                classe = c;
            }

            somatorio = Utilidades.somarSemelhancas(classificacao[c], classes[9 - c]);
            somatorio -= limiar[9 - c];

            if (somatorio > somatorioClasse) {
                somatorioClasse = somatorio;
                classe = 9 - c;
            }
        }

        return classe;
    }
}
