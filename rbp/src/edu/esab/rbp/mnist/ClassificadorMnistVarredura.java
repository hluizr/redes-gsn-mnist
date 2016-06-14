/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

import edu.esab.rbp.Utilidades;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author helio.rodrigues
 */
public class ClassificadorMnistVarredura {

    private final ClassificadorImagemVarredura[] classificadorImagem = new ClassificadorImagemVarredura[10];

    /**
     *
     */
    public ClassificadorMnistVarredura() {
        for (int i = 0; i < classificadorImagem.length; i++) {
            classificadorImagem[i] = new ClassificadorImagemVarredura();
        }
    }

    /**
     * Treina a rede com as imagens do MNIST.
     *
     * @param numeroImagens A quantidade de imagens.
     * @throws IOException Erro ao ler arquivo.
     */
    public void treinar(int numeroImagens) throws IOException {
        System.out.println("\nTreinando classificador de imagens...");

        try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-images.idx3-ubyte");
                InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-labels.idx1-ubyte")) {

            DataInputStream imagemDataInput = new DataInputStream(imagemStream);
            DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

            rotuloDataInput.readInt(); // Numero magico.
            System.out.println("\nNumero de rotulos: " + rotuloDataInput.readInt());

            imagemDataInput.readInt(); // Numero magico.
            System.out.println("\nNumero de imagens: " + imagemDataInput.readInt());
            System.out.println("Largura das imagens: " + imagemDataInput.readInt());
            System.out.println("Altura das imagens: " + imagemDataInput.readInt());

            // Treina a rede.
            ImagemMnist imagemMnist = new ImagemMnist();

            for (int i = 0; i < numeroImagens; i++) {
                System.out.println("Imagem " + (i + 1) + " de " + numeroImagens + "...");
                imagemMnist.carregar(imagemStream, rotuloStream);
                
                for (int j = 0; j < classificadorImagem.length; j++) {
                    classificadorImagem[j].treinar(imagemMnist.getImagem(), j == imagemMnist.getRotulo() ? 1 : 0);
                }
            }

            System.out.println("\nTreinamento concluído!");
        }

        try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-images.idx3-ubyte");
                InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-labels.idx1-ubyte")) {

            DataInputStream imagemDataInput = new DataInputStream(imagemStream);
            DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

            rotuloDataInput.readInt(); // Numero magico.
            System.out.println("\nNumero de rotulos: " + rotuloDataInput.readInt());

            imagemDataInput.readInt(); // Numero magico.
            System.out.println("\nNumero de imagens: " + imagemDataInput.readInt());
            System.out.println("Largura das imagens: " + imagemDataInput.readInt());
            System.out.println("Altura das imagens: " + imagemDataInput.readInt());

            // Treina a rede.
            ImagemMnist imagemMnist = new ImagemMnist();

            for (int i = 0; i < numeroImagens; i++) {
                System.out.println("Numero " + (i + 1) + " de " + numeroImagens + "...");
                imagemMnist.carregar(imagemStream, rotuloStream);

                for (int j = 0; j < classificadorImagem.length; j++) {
                    classificadorImagem[j].calibrar(imagemMnist.getImagem(), j == imagemMnist.getRotulo() ? 1 : 0);
                }
            }

            System.out.println("\nTreinamento concluído!");
        }
    }

    /**
     * Treina a rede com as imagens do MNIST.
     *
     * @param imagemMnist A imagem a ser treinada.
     */
    public void treinar(ImagemMnist imagemMnist) {
        for (int j = 0; j < classificadorImagem.length; j++) {
            classificadorImagem[j].treinar(imagemMnist.getImagem(), j == imagemMnist.getRotulo() ? 1 : 0);
        }
    }

    /**
     *
     * @param imagemMnist
     */
    public void calibrar(ImagemMnist imagemMnist) {
        for (int j = 0; j < classificadorImagem.length; j++) {
            classificadorImagem[j].calibrar(imagemMnist.getImagem(), j == imagemMnist.getRotulo() ? 1 : 0);
        }
    }

    /**
     *
     * @param imagemMnist
     * @return
     */
    public int[] classificar(ImagemMnist imagemMnist) {
        int[] classificacao = new int[10];

        for (int j = 0; j < classificadorImagem.length; j++) {
            classificacao[j] = classificadorImagem[j].classificar(imagemMnist.getImagem(), true);
        }

        return classificacao;
    }

    /**
     * Classifica as imagens do MNIST.
     *
     * @param numeroImagens O numero de imagens a serem classificadas.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @throws IOException Erro ao abrir arquivo.
     */
    public void classificar(int numeroImagens, boolean adivinhar) throws IOException {
        int[] acertos = new int[10];
        int[] erros = new int[10];
        int[] qtde = new int[10];
        int[] reconhecidos = new int[10];
        int[] reconhecidosErr = new int[10];

        try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/t10k-images.idx3-ubyte");
                InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/t10k-labels.idx1-ubyte")) {

            DataInputStream imagemDataInput = new DataInputStream(imagemStream);
            DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

            rotuloDataInput.readInt(); // Numero magico.
            System.out.println("\nNumero de rotulos: " + rotuloDataInput.readInt());

            imagemDataInput.readInt(); // Numero magico.
            System.out.println("\nNumero de imagens: " + imagemDataInput.readInt());
            System.out.println("Largura das imagens: " + imagemDataInput.readInt());
            System.out.println("Altura das imagens: " + imagemDataInput.readInt());

            // Testa a rede.
            ImagemMnist imagemMnist = new ImagemMnist();

            for (int i = 0; i < numeroImagens; i++) {
                imagemMnist.carregar(imagemStream, rotuloStream);
                qtde[imagemMnist.getRotulo()]++;
//                System.out.println(imagemMnist);

                for (int j = 0; j < classificadorImagem.length; j++) {
                    int classificacao = classificadorImagem[j].classificar(imagemMnist.getImagem(), true);

                    if (classificacao == 1 && j == imagemMnist.getRotulo()) {
//                        System.out.println("Correto: " + j);
                        acertos[j]++;
                        reconhecidos[j]++;
                    } else if (classificacao == 1 && j != imagemMnist.getRotulo()) {
//                        System.out.println("Errado: " + j);
                        reconhecidosErr[j]++;
                        erros[j]++;
                    } else if (classificacao != 1 && j == imagemMnist.getRotulo()) {
                        erros[j]++;
                    } else if (classificacao != 1 && j != imagemMnist.getRotulo()) {
                        acertos[j]++;
                    }
                }
            }
        }

        int totalAcertos = 0;
        int totalErros = 0;

        for (int i = 0; i < acertos.length; i++) {
            totalAcertos += acertos[i];
            totalErros += erros[i];

            double taxaReconhecimento = (double) reconhecidos[i] / (double) qtde[i];
            taxaReconhecimento = 100.0 * taxaReconhecimento;

            double taxaReconhecimentoErr = (double) reconhecidosErr[i] / (double) (reconhecidos[i] + reconhecidosErr[i]);
            taxaReconhecimentoErr = 100.0 * taxaReconhecimentoErr;

            System.out.println("Classificador " + i + ": Acertos: " + acertos[i] + " Erros: " + erros[i] + ". Taxa de acerto: "
                    + (((double) acertos[i] / (double) (acertos[i] + erros[i]))) * 100 + "% Qtde: " + qtde[i] + ", Reconhecidos: "
                    + reconhecidos[i] + ", Taxa de reconhecimento: " + taxaReconhecimento + " Taxa de reconhecimento errado: " + taxaReconhecimentoErr);
        }

        System.out.println("Classificação concluída: Acertos: " + totalAcertos + " Erros: " + totalErros + ". Taxa de acerto: "
                + (((double) totalAcertos / (double) (totalAcertos + totalErros))) * 100 + "%");
    }

    /**
     * Classifica as imagens do MNIST.
     *
     * @param numeroImagens O numero de imagens a serem classificadas.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @throws IOException Erro ao abrir arquivo.
     */
    public void classificarSomatorio(int numeroImagens, boolean adivinhar) throws IOException {
        int acertos = 0;
        int erros = 0;
        int qtde = 0;
        int reconhecidos = 0;
        int reconhecidosErr = 0;

        try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/t10k-images.idx3-ubyte");
                InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/t10k-labels.idx1-ubyte")) {

            DataInputStream imagemDataInput = new DataInputStream(imagemStream);
            DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

            rotuloDataInput.readInt(); // Numero magico.
            System.out.println("\nNumero de rotulos: " + rotuloDataInput.readInt());

            imagemDataInput.readInt(); // Numero magico.
            System.out.println("\nNumero de imagens: " + imagemDataInput.readInt());
            System.out.println("Largura das imagens: " + imagemDataInput.readInt());
            System.out.println("Altura das imagens: " + imagemDataInput.readInt());

            // Testa a rede.
            ImagemMnist imagemMnist = new ImagemMnist();

            for (int i = 0; i < numeroImagens; i++) {
                imagemMnist.carregar(imagemStream, rotuloStream);
                qtde++;
//                System.out.println(imagemMnist);

                int rotulo = 0;
                int somatorio = 0;

                for (int j = 0; j < classificadorImagem.length; j++) {
                    int classificacao = classificadorImagem[j].classificarSomatorio(imagemMnist.getImagem(), true);

                    if (somatorio < classificacao) {
                        rotulo = j;
                        somatorio = classificacao;
                    }
                }

                if (rotulo == imagemMnist.getRotulo()) {
//                        System.out.println("Correto: " + j);
                    acertos++;
                    reconhecidos++;
                } else if (rotulo != imagemMnist.getRotulo()) {
//                        System.out.println("Errado: " + j);
                    reconhecidosErr++;
                    erros++;
                }
            }
        }

        int totalAcertos = 0;
        int totalErros = 0;

        totalAcertos += acertos;
        totalErros += erros;

        double taxaReconhecimento = (double) reconhecidos / (double) qtde;
        taxaReconhecimento = 100.0 * taxaReconhecimento;

        double taxaReconhecimentoErr = (double) reconhecidosErr / (double) (reconhecidos + reconhecidosErr);
        taxaReconhecimentoErr = 100.0 * taxaReconhecimentoErr;

        System.out.println("Acertos: " + acertos + " Erros: " + erros + ". Taxa de acerto: "
                + (((double) acertos / (double) (acertos + erros))) * 100 + "% Qtde: " + qtde + ", Reconhecidos: "
                + reconhecidos + ", Taxa de reconhecimento: " + taxaReconhecimento + " Taxa de reconhecimento errado: " + taxaReconhecimentoErr);

        System.out.println("Classificação concluída: Acertos: " + totalAcertos + " Erros: " + totalErros + ". Taxa de acerto: "
                + (((double) totalAcertos / (double) (totalAcertos + totalErros))) * 100 + "%");
    }
}
