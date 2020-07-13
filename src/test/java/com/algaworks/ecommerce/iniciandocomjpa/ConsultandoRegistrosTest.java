package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

public class ConsultandoRegistrosTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
//        Produto produto = entityManager.find(Produto.class, 1);
        Produto produto = entityManager.getReference(Produto.class, 1); // getReference() só vai ao banco de dados quando se busca uma propriedade

        Assert.assertNotNull(produto);
//        Assert.assertEquals("Kindle", produto.getNome()); // com getReference() a consulta só ocorre aqui
    }

    @Test
    public void atualizarAReferencia() {
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson");

        entityManager.refresh(produto); // O refresh irá retornar o objeto ao seu estado do banco de dados

        Assert.assertEquals("Kindle", produto.getNome());
    }
}
