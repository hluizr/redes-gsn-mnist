/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

import edu.esab.rbp.mnist.ImagemMnist;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author helio.rodrigues
 */
public class ClassificadorMnistTest {

    public ClassificadorMnistTest() {
    }

    /**
     * Testa os classificadores em ordem crescente de tamanho.
     *
     * @throws IOException Erro ao abrir arquivo.
     */
    @Test
    public void test() throws IOException {
        try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-images.idx3-ubyte");
                InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-labels.idx1-ubyte")) {

            DataInputStream imagemDataInput = new DataInputStream(imagemStream);
            DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

            rotuloDataInput.readInt(); // Numero magico.
            rotuloDataInput.readInt(); // Numero de rotulos.

            imagemDataInput.readInt(); // Numero magico.
            imagemDataInput.readInt(); // Numero de imagens.
            imagemDataInput.readInt(); // Largura das imagens.
            imagemDataInput.readInt(); // Altura das imagens.

            // Imagem 1.
            ImagemMnist imagem1Mnist = new ImagemMnist();
            imagem1Mnist.carregar(imagemStream, rotuloStream);
            int[] imagem1Entrada = new int[1024];
            int contador = 0;
            int[][] imagem = imagem1Mnist.getImagem();

            for (int j = 0; j < imagem1Entrada.length; j++) {
                imagem1Entrada[j] = 0;
            }

            for (int j = 0; j < 28; j++) {
                for (int k = 0; k < 28; k++) {
                    imagem1Entrada[contador++] = imagem[j][k];
                }
            }

            // Imagem 2.
            ImagemMnist imagem2Mnist = new ImagemMnist();
            imagem2Mnist.carregar(imagemStream, rotuloStream);
            int[] imagem2Entrada = new int[1024];
            contador = 0;
            imagem = imagem2Mnist.getImagem();

            for (int j = 0; j < imagem2Entrada.length; j++) {
                imagem2Entrada[j] = 0;
            }

            for (int j = 0; j < 28; j++) {
                for (int k = 0; k < 28; k++) {
                    imagem2Entrada[contador++] = imagem[j][k];
                }
            }

            // Testa classificador.
            Classificador1024Bits classificador = new Classificador1024Bits();
            classificador.aprender(imagem1Entrada, 0);
            classificador.aprender(imagem2Entrada, 1);

            Assert.assertEquals(classificador.classificar(imagem1Entrada, false), 0);
            Assert.assertEquals(classificador.classificar(imagem2Entrada, false), 1);
        }
    }

    @Test
    public void testAprendizado32() {
        int[] entrada0 = criarEntradaAleatoria(32);
        int[] entrada1 = criarEntradaAleatoria(32);
        int[] entrada2 = criarEntradaAleatoria(32);
        int[] entrada3 = criarEntradaAleatoria(32);

        Classificador32Bits classificador = new Classificador32Bits();

        classificador.aprender(entrada0, 0);
        classificador.aprender(entrada1, 1);
        classificador.aprender(entrada2, 0);
        classificador.aprender(entrada3, 1);

        Assert.assertEquals(classificador.classificar(entrada0, false), 0);
        Assert.assertEquals(classificador.classificar(entrada1, false), 1);
        Assert.assertEquals(classificador.classificar(entrada2, false), 0);
        Assert.assertEquals(classificador.classificar(entrada3, false), 1);
    }

    @Test
    public void testAprendizado64() {
        int[] entrada0 = criarEntradaAleatoria(64);
        int[] entrada1 = criarEntradaAleatoria(64);
        int[] entrada2 = criarEntradaAleatoria(64);
        int[] entrada3 = criarEntradaAleatoria(64);

        Classificador64Bits classificador = new Classificador64Bits();

        classificador.aprender(entrada0, 0);
        classificador.aprender(entrada1, 1);
        classificador.aprender(entrada2, 0);
        classificador.aprender(entrada3, 1);

        Assert.assertEquals(classificador.classificar(entrada0, false), 0);
        Assert.assertEquals(classificador.classificar(entrada1, false), 1);
        Assert.assertEquals(classificador.classificar(entrada2, false), 0);
        Assert.assertEquals(classificador.classificar(entrada3, false), 1);
    }

    @Test
    public void testAprendizado128() {
        int[] entrada0 = criarEntradaAleatoria(128);
        int[] entrada1 = criarEntradaAleatoria(128);
        int[] entrada2 = criarEntradaAleatoria(128);
        int[] entrada3 = criarEntradaAleatoria(128);

        Classificador128Bits classificador = new Classificador128Bits();

        classificador.aprender(entrada0, 0);
        classificador.aprender(entrada1, 1);
        classificador.aprender(entrada2, 0);
        classificador.aprender(entrada3, 1);

        Assert.assertEquals(classificador.classificar(entrada0, false), 0);
        Assert.assertEquals(classificador.classificar(entrada1, false), 1);
        Assert.assertEquals(classificador.classificar(entrada2, false), 0);
        Assert.assertEquals(classificador.classificar(entrada3, false), 1);
    }

    @Test
    public void testAprendizado256() {
        int[] entrada0 = criarEntradaAleatoria(256);
        int[] entrada1 = criarEntradaAleatoria(256);
        int[] entrada2 = criarEntradaAleatoria(256);
        int[] entrada3 = criarEntradaAleatoria(256);

        Classificador256Bits classificador = new Classificador256Bits();

        classificador.aprender(entrada0, 0);
        classificador.aprender(entrada1, 1);
        classificador.aprender(entrada2, 0);
        classificador.aprender(entrada3, 1);

        Assert.assertEquals(classificador.classificar(entrada0, false), 0);
        Assert.assertEquals(classificador.classificar(entrada1, false), 1);
        Assert.assertEquals(classificador.classificar(entrada2, false), 0);
        Assert.assertEquals(classificador.classificar(entrada3, false), 1);
    }

    @Test
    public void testAprendizado512() {
        int[] entrada0 = criarEntradaAleatoria(512);
        int[] entrada1 = criarEntradaAleatoria(512);
        int[] entrada2 = criarEntradaAleatoria(512);
        int[] entrada3 = criarEntradaAleatoria(512);

        Classificador512Bits classificador = new Classificador512Bits();

        classificador.aprender(entrada0, 0);
        classificador.aprender(entrada1, 1);
        classificador.aprender(entrada2, 0);
        classificador.aprender(entrada3, 1);

        Assert.assertEquals(classificador.classificar(entrada0, false), 0);
        Assert.assertEquals(classificador.classificar(entrada1, false), 1);
        Assert.assertEquals(classificador.classificar(entrada2, false), 0);
        Assert.assertEquals(classificador.classificar(entrada3, false), 1);
    }

    @Test
    public void testAprendizado1024() {
        int[][] entrada = new int[16][1024];

        for (int i = 0; i < entrada.length; i++) {
            entrada[i] = criarEntradaAleatoria(1024);
        }

        int contador = 0;
        Classificador1024Bits classificador = new Classificador1024Bits();

        for (int i = 0; i < entrada.length; i++) {
            classificador.aprender(entrada[i], contador);
            contador = contador == 0 ? 1 : 0;
        }

        contador = 0;

        for (int i = 0; i < entrada.length; i++) {
            Assert.assertEquals(classificador.classificar(entrada[i], false), contador);
            contador = contador == 0 ? 1 : 0;
        }
    }

    private int[] criarEntradaAleatoria(int tamanho) {
        int[] entrada = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            entrada[i] = (int) (Math.random() * 2.0);
        }

        return entrada;
    }
}
