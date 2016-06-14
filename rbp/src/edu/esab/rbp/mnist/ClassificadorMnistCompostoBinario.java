/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp.mnist;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classificador de imagens em numeros finais.
 *
 * @author Helio Rodrigues
 */
public class ClassificadorMnistCompostoBinario {

    private final ClassificadorMnist1024Bits classificadorImagem = new ClassificadorMnist1024Bits();
    private final ClassificadorNumericoBinario classificadorNumerico = new ClassificadorNumericoBinario();

    /**
     * Treina a rede com as imagens do MNIST.
     *
     * @param numeroImagens A quantidade de imagens.
     * @param adivinhar Se deve adivinhar a imagem no treinamento de numeros.
     * @throws IOException Erro ao ler arquivo.
     */
    public void treinar(int numeroImagens, boolean adivinhar) throws IOException {
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
                classificadorImagem.treinar(imagemMnist.getImagem(), imagemMnist.getRotulo());
            }

            System.out.println("\nTreinamento concluído!");
        }

        System.out.println("\nTreinando classificador de numeros...");

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
                System.out.println("Número " + (i + 1) + " de " + numeroImagens + "...");
                imagemMnist.carregar(imagemStream, rotuloStream);

                classificadorNumerico.treinar(classificadorImagem.classificar(imagemMnist.getImagem(), adivinhar),
                        imagemMnist.getRotulo());
            }

            System.out.println("\nTreinamento concluído!");
        }
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

                int classificacao = classificadorNumerico.classificar(classificadorImagem.classificar(
                        imagemMnist.getImagem(), adivinhar), adivinhar);

//                System.out.println("Imagem " + i + ": Rótulo: " + imagemMnist.getRotulo() + ", Classificação:"
//                        + classificacao + " " + (classificacao == imagemMnist.getRotulo() ? "[  OK  ]" : "[FALHOU]"));

                if (classificacao == imagemMnist.getRotulo()) {
                    acertos++;
                } else {
                    erros++;
                }
            }
        }

        System.out.println("Classificação concluída: Acertos: " + acertos + " Erros: " + erros + ". Taxa de acerto: "
                + (((double) acertos / (double) (acertos + erros))) * 100 + "%");
    }
}
