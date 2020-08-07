package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

public class CachePrimeiroNivelTest extends EntityManagerTest {

    @Test
    public void analisarEstados() {
        Produto produto = entityManager.find(Produto.class,1);
        System.out.println(produto.getNome());

        System.out.println("---------------");

        // Aqui não será feita outra consulta no banco de dados pois o objeto ainda está no cache de primeiro nível
        Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
        System.out.println(produtoResgatado.getNome());
    }
}
