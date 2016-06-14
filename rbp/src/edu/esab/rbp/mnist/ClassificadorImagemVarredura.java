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
 * @author Helio Rodrigues.
 */
public class ClassificadorImagemVarredura {

    public static final int QTDE_IMAGENS_INICIAL = 12;
    private final Classificador32Bits[] classificadorLinha = new Classificador32Bits[28];
    private final Classificador32Bits[] classificadorColuna = new Classificador32Bits[28];
    private int totalImagensTreinadas = 0;
    private int totalSomatorioLinhas = 0;
    private int totalSomatorioColunas = 0;
    private int totalSomatorioImagens = 0;
    private int somatorioMedioLinhas = 28;
    private int somatorioMedioColunas = 28;

    /**
     *
     */
    public ClassificadorImagemVarredura() {
        for (int i = 0; i < classificadorLinha.length; i++) {
            classificadorLinha[i] = new Classificador32Bits();
            classificadorColuna[i] = new Classificador32Bits();
        }
    }

    /**
     * Treina a rede para uma determinada entrada e saida.
     *
     * @param imagem A imagem a ser treinada.
     * @param saida O rotulo da imagem.
     */
    public void treinar(int[][] imagem, int saida) {
        if (saida == 1 || totalImagensTreinadas > QTDE_IMAGENS_INICIAL) {
            for (int linha = 0; linha < imagem.length; linha++) {
                int[] entrada = Utilidades.redimensionarVetor(imagem[linha], 32);
                classificadorLinha[linha].aprender(entrada, saida);
                classificadorColuna[linha].aprender(entrada, saida);
            }

            totalImagensTreinadas++;
        }
    }

    /**
     * Treina a rede para uma determinada entrada e saida.
     *
     * @param imagem A imagem a ser treinada.
     * @param saida O rotulo da imagem.
     */
    public void calibrar(int[][] imagem, int saida) {
        if (saida == 1) {
            totalSomatorioLinhas += calcularSomatorioLinha(imagem, true);
            totalSomatorioColunas += calcularSomatorioColuna(imagem, true);
            totalSomatorioImagens++;

            somatorioMedioLinhas = (int) ((double) totalSomatorioLinhas / (double) totalSomatorioImagens);
            somatorioMedioColunas = (int) ((double) totalSomatorioColunas / (double) totalSomatorioImagens);
        }
    }

    /**
     * Classifica uma entrada.
     *
     * @param imagem A entrada a ser classificada.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @return A classificacao da entrada.
     */
    public int classificar(int[][] imagem, boolean adivinhar) {
        int contadorLinha = 0;
        int contadorColuna = 0;

        for (int linha = 0; linha < imagem.length; linha++) {
            int[] entrada = Utilidades.redimensionarVetor(imagem[linha], 32);
            contadorLinha += classificadorLinha[linha].classificar(entrada, adivinhar);
            contadorColuna += classificadorColuna[linha].classificar(entrada, adivinhar);
        }

        if (contadorLinha >= somatorioMedioLinhas && contadorColuna >= somatorioMedioColunas) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Classifica uma entrada.
     *
     * @param imagem A entrada a ser classificada.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @return A classificacao da entrada.
     */
    public int classificarSomatorio(int[][] imagem, boolean adivinhar) {
        int contador = 0;

        for (int linha = 0; linha < imagem.length; linha++) {
            int[] entrada = Utilidades.redimensionarVetor(imagem[linha], 32);
            contador += classificadorLinha[linha].classificar(entrada, adivinhar);
            contador += classificadorColuna[linha].classificar(entrada, adivinhar);
        }

        contador -= somatorioMedioLinhas;
        contador -= somatorioMedioColunas;
        return contador;
    }

    /**
     * Classifica uma entrada.
     *
     * @param imagem A entrada a ser classificada.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @return A classificacao da entrada.
     */
    public int calcularSomatorioLinha(int[][] imagem, boolean adivinhar) {
        int contador = 0;

        for (int linha = 0; linha < imagem.length; linha++) {
            int[] entrada = Utilidades.redimensionarVetor(imagem[linha], 32);
            contador += classificadorLinha[linha].classificar(entrada, adivinhar);
        }

        return contador;
    }

    /**
     * Classifica uma entrada.
     *
     * @param imagem A entrada a ser classificada.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @return A classificacao da entrada.
     */
    public int calcularSomatorioColuna(int[][] imagem, boolean adivinhar) {
        int contador = 0;

        for (int linha = 0; linha < imagem.length; linha++) {
            int[] entrada = Utilidades.redimensionarVetor(imagem[linha], 32);
            contador += classificadorColuna[linha].classificar(entrada, adivinhar);
        }

        return contador;
    }
}
