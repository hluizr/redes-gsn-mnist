/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

import edu.esab.rbp.Classificador1024Bits;
import edu.esab.rbp.Utilidades;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classificador que mapeia diretamente imagens MNIST em numeros.
 *
 * @author Helio Rodrigues
 */
public class ClassificadorMnistNumericoBinario {

    private final Classificador1024Bits[] classificador = new Classificador1024Bits[4];

    /**
     * Constroi o classificador de imagens MNIST.
     */
    public ClassificadorMnistNumericoBinario() {
        for (int i = 0; i < classificador.length; i++) {
            classificador[i] = new Classificador1024Bits();
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

                int[] imagem = converterImagem(imagemMnist.getImagem());
                int[] rotulo = converterNumero(imagemMnist.getRotulo());

                for (int j = 0; j < classificador.length; j++) {
                    classificador[j].aprender(imagem, rotulo[j]);
                }
            }
        }

        System.out.println("\nTreinamento concluído!");
    }

    /**
     * Classifica as imagens do MNIST.
     *
     * @param numeroImagens O numero de imagens a serem classificadas.
     * @param adivinhar Se deve adivinhar em caso de valor indefinido.
     * @throws IOException Erro ao abrir arquivo.
     */
    public void classificar(int numeroImagens, boolean adivinhar) throws IOException {
        int acertos = 0;
        int erros = 0;
            
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

            // Treina a rede.
            ImagemMnist imagemMnist = new ImagemMnist();

            for (int i = 0; i < numeroImagens; i++) {
                imagemMnist.carregar(imagemStream, rotuloStream);

                int[] imagem = converterImagem(imagemMnist.getImagem());
                int[] tempRotulo = new int[4];

                for (int j = 0; j < classificador.length; j++) {
                    tempRotulo[j] = classificador[j].classificar(imagem, true);
                }

                int rotulo = converterNumero(tempRotulo);

                System.out.println("Imagem " + i + ": Rótulo: " + imagemMnist.getRotulo() + ", Classificação:"
                        + rotulo + " " + (rotulo == imagemMnist.getRotulo() ? "[  OK  ]" : "[FALHOU]"));

                if (rotulo == imagemMnist.getRotulo()) {
                    acertos++;
                } else {
                    erros++;
                }
            }
        }

        System.out.println("Classificação concluída: Acertos: " + acertos + " Erros: " + erros + ". Taxa de acerto: "
                + (((double) acertos / (double) (acertos + erros))) * 100 + "%");
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

        imagem = Utilidades.embaralharVetor(imagem);
        return imagem;
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
