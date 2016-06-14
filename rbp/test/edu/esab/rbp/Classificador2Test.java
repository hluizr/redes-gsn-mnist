/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Teste para o classificador de 2 entradas.
 * 
 * @author Helio Rodrigues
 */
public class Classificador2Test {
    
    private final Classificador2Bits ul = new Classificador2Bits();
    
    /**
     * Testa o treinamento da unidade.
     */
    @Test
    public void testTreinar() {
        // Aprender valor.
        Assert.assertEquals(ul.classificar(new int[]{0, 1}, false), Classificador.INDEFINIDO);
        ul.aprender(new int[]{0, 1}, Classificador.VERDADEIRO);
        Assert.assertEquals(ul.classificar(new int[]{0, 1}, false), Classificador.VERDADEIRO);
        
        // Nao alterar valor aprendido.
        ul.aprender(new int[]{0, 1}, Classificador.FALSO);
        Assert.assertEquals(ul.classificar(new int[]{0, 1}, false), Classificador.VERDADEIRO);

        // Aprender novo valor.
        Assert.assertEquals(ul.classificar(new int[]{1, 1}, false), Classificador.INDEFINIDO);
        ul.aprender(new int[]{1, 1}, Classificador.FALSO);
        Assert.assertEquals(ul.classificar(new int[]{1, 1}, false), Classificador.FALSO);

        // Associa nova entrada.
        Assert.assertEquals(ul.classificar(new int[]{1, 0}, false), Classificador.INDEFINIDO);
        int[] entrada = ul.aprender(new int[]{1, Classificador.INDEFINIDO}, Classificador.VERDADEIRO);
        Assert.assertEquals(entrada[1], 0);
        Assert.assertEquals(ul.classificar(new int[]{1, 0}, false), Classificador.VERDADEIRO);

        // Aproveita entrada existente.
        Assert.assertEquals(ul.classificar(new int[]{0, 0}, false), Classificador.INDEFINIDO);
        entrada = ul.aprender(new int[]{Classificador.INDEFINIDO, Classificador.INDEFINIDO}, Classificador.VERDADEIRO);
        Assert.assertEquals(entrada[0], 0);
        Assert.assertEquals(entrada[1], 1);
        Assert.assertEquals(ul.classificar(new int[]{0, 0}, false), Classificador.INDEFINIDO);

        Assert.assertEquals(ul.classificar(new int[]{0, 0}, false), Classificador.INDEFINIDO);
        entrada = ul.aprender(new int[]{Classificador.INDEFINIDO, 1}, Classificador.FALSO);
        Assert.assertEquals(entrada[0], 1);
        Assert.assertEquals(ul.classificar(new int[]{0, 0}, false), Classificador.INDEFINIDO);

        // Associa nova entrada.
        Assert.assertEquals(ul.classificar(new int[]{0, 0}, false), Classificador.INDEFINIDO);
        entrada = ul.aprender(new int[]{0, 0}, Classificador.VERDADEIRO);
        Assert.assertEquals(entrada[0], 0);
        Assert.assertEquals(entrada[1], 0);
        Assert.assertEquals(ul.classificar(new int[]{0, 0}, false), Classificador.VERDADEIRO);
    }
    
    /**
     * Testa a classificacao.
     */
    public void testClassificar() {
        Assert.assertEquals(ul.classificar(new int[]{0, 0}, false), Classificador.INDEFINIDO);
        Assert.assertEquals(ul.classificar(new int[]{0, 1}, false), Classificador.VERDADEIRO);
        Assert.assertEquals(ul.classificar(new int[]{1, 1}, false), Classificador.FALSO);
    }
}
