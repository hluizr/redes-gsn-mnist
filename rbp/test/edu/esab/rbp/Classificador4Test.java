/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Teste para o classificador de 4 entradas.
 * 
 * @author Helio Rodrigues
 */
public class Classificador4Test {

    private final Classificador4Bits ul = new Classificador4Bits();

    /**
     * Testa o treinamento da unidade.
     */
    @Test
    public void testTreinar() {
        // Aprender valor.
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 1}, false), -1);
        ul.aprender(new int[]{1, 1, 0, 1}, 1);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 1}, false), 1);

        // Nao alterar valor aprendido.
        ul.aprender(new int[]{1, 1, 0, 1}, 0);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 1}, false), 1);

        // Aprender novo valor.
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 1, 1}, false), -1);
        ul.aprender(new int[]{1, 1, 1, 1}, 0);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 1, 1}, false), 0);

        // Associa nova entrada.
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 1, 0}, false), -1);
        int[] entrada = ul.aprender(new int[]{1, 1, 1, -1}, 1);
        Assert.assertEquals(entrada[3], 0);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 1, 0}, false), 1);

        // Aproveita entrada existente.
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 0}, false), -1);
        entrada = ul.aprender(new int[]{1, 1, -1, -1}, 1);
        Assert.assertEquals(entrada[2], 0);
        Assert.assertEquals(entrada[3], 1);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 0}, false), -1);

        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 0}, false), -1);
        entrada = ul.aprender(new int[]{1, 1, -1, 1}, 0);
        Assert.assertEquals(entrada[2], 1);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 0}, false), -1);

        // Associa nova entrada.
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 0}, false), -1);
        entrada = ul.aprender(new int[]{1, 1, 0, 0}, 1);
        Assert.assertEquals(entrada[2], 0);
        Assert.assertEquals(entrada[3], 0);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 0}, false), 1);
    }

    /**
     * Testa a classificacao.
     */
    public void testClassificar() {
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 0}, false), -1);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 0, 1}, false), 1);
        Assert.assertEquals(ul.classificar(new int[]{1, 1, 1, 1}, false), 0);
    }
}
