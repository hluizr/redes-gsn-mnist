/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

import edu.esab.rbp.mnist.ClassificadorMnist;
import edu.esab.rbp.mnist.ClassificadorMnist1024x128xSomador;
import edu.esab.rbp.mnist.ImagemMnist;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Executa o treinamento e a classificacao das imagens MNIST.
 *
 * @author Helio Rodrigues
 */
public class RedeBinariaProbabilistica {

    /**
     *
     * @param classificadorMnist
     * @param numeroImagensAprendizado
     * @param numeroImagensClassificacao
     * @throws IOException
     */
    public void testarArquiteturaSomador(ClassificadorMnist classificadorMnist, int numeroImagensAprendizado,
            int numeroImagensClassificacao) throws IOException {

        System.out.println("\nAprendendo...");

        try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-images.idx3-ubyte");
                InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-labels.idx1-ubyte")) {

            DataInputStream imagemDataInput = new DataInputStream(imagemStream);
            DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

            rotuloDataInput.readInt(); // Numero magico.
            System.out.println("\nNúmero de rotulos: " + rotuloDataInput.readInt());

            imagemDataInput.readInt(); // Numero magico.
            System.out.println("\nNúmero de imagens: " + imagemDataInput.readInt());
            System.out.println("Largura das imagens: " + imagemDataInput.readInt());
            System.out.println("Altura das imagens: " + imagemDataInput.readInt());

            // Treina a rede.
            int qtde0s = 0;
            int qtde1s = 0;
            ImagemMnist imagemMnist = new ImagemMnist();

            for (int i = 0; i < numeroImagensAprendizado; i++) {
                System.out.println("Aprendendo imagem " + (i + 1) + " de " + numeroImagensAprendizado + "...");
                imagemMnist.carregar(imagemStream, rotuloStream);

                if (imagemMnist.getRotulo() == 0 && qtde0s++ > 12) {
                    classificadorMnist.aprender(imagemMnist);
                } else if (imagemMnist.getRotulo() == 1 && qtde1s++ > 3) {
                    classificadorMnist.aprender(imagemMnist);
                } else if (imagemMnist.getRotulo() != 0 && imagemMnist.getRotulo() != 1) {
                    classificadorMnist.aprender(imagemMnist);
                }
            }
        }

        try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-images.idx3-ubyte");
                InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-labels.idx1-ubyte")) {

            DataInputStream imagemDataInput = new DataInputStream(imagemStream);
            DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

            rotuloDataInput.readInt(); // Numero magico.
            System.out.println("\nNúmero de rotulos: " + rotuloDataInput.readInt());

            imagemDataInput.readInt(); // Numero magico.
            System.out.println("\nNúmero de imagens: " + imagemDataInput.readInt());
            System.out.println("Largura das imagens: " + imagemDataInput.readInt());
            System.out.println("Altura das imagens: " + imagemDataInput.readInt());

            // Treina a rede.
            ImagemMnist imagemMnist = new ImagemMnist();

            for (int i = 0; i < numeroImagensAprendizado; i++) {
                System.out.println("Calibrando imagem " + (i + 1) + " de " + numeroImagensAprendizado + "...");
                imagemMnist.carregar(imagemStream, rotuloStream);
                classificadorMnist.calibrar(imagemMnist, true);
            }
        }

        System.out.println("\nAprendizado concluído!");
        System.out.println("\nClassificando...");
        int totalAcertos = 0;
        int[] acertos = new int[10];
        int[] qtde = new int[10];

        try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/t10k-images.idx3-ubyte");
                InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/t10k-labels.idx1-ubyte")) {

            DataInputStream imagemDataInput = new DataInputStream(imagemStream);
            DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

            rotuloDataInput.readInt(); // Numero magico.
            System.out.println("\nNúmero de rotulos: " + rotuloDataInput.readInt());

            imagemDataInput.readInt(); // Numero magico.
            System.out.println("\nNúmero de imagens: " + imagemDataInput.readInt());
            System.out.println("Largura das imagens: " + imagemDataInput.readInt());
            System.out.println("Altura das imagens: " + imagemDataInput.readInt());

            // Treina a rede.
            ImagemMnist imagemMnist = new ImagemMnist();

            for (int i = 0; i < numeroImagensClassificacao; i++) {
                System.out.println("Classificando imagem " + (i + 1) + " de " + numeroImagensClassificacao + "...");
                imagemMnist.carregar(imagemStream, rotuloStream);
                int rotulo = classificadorMnist.classificar(imagemMnist, true);
                qtde[imagemMnist.getRotulo()]++;

                if (rotulo == imagemMnist.getRotulo()) {
                    totalAcertos++;
                    acertos[imagemMnist.getRotulo()]++;
                }
            }
        }

        for (int i = 0; i < acertos.length; i++) {
            System.out.println("Rotulo " + i + ": Acertos: " + acertos[i] + ". Taxa de acerto: "
                    + (((double) acertos[i] / (double) (qtde[i]))) * 100 + "%");
        }

        System.out.println("Total: Acertos: " + totalAcertos + ". Taxa de acerto: "
                + (((double) totalAcertos / (double) (numeroImagensClassificacao))) * 100 + "%");
    }

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        RedeBinariaProbabilistica rbp = new RedeBinariaProbabilistica();
//        rbp.testarArquiteturaSomador(new ClassificadorMnist1024x4xSomador(), 60000, 10000); // 8
//        rbp.testarArquiteturaSomador(new ClassificadorMnist1024x8xSomador(), 60000, 10000); // 7
//        rbp.testarArquiteturaSomador(new ClassificadorMnist1024x16xSomador(), 60000, 10000); // 6
//        rbp.testarArquiteturaSomador(new ClassificadorMnist1024x32xSomador(), 60000, 10000); // 5
//        rbp.testarArquiteturaSomador(new ClassificadorMnist1024x64xSomador(), 60000, 10000); // 4
        rbp.testarArquiteturaSomador(new ClassificadorMnist1024x128xSomador(), 60000, 10000); // 3
//        rbp.testarArquiteturaSomador(new ClassificadorMnist1024x256xSomador(), 60000, 10000); // 2
//        rbp.testarArquiteturaSomador(new ClassificadorMnist1024x512xSomador(), 60000, 10000); // 1
    }
}
