package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void buscarPedidosComProdutoEspecifico(){
        String jpql = "select p from Pedido p join fetch p.itens itens where itens.produto.id = 1";

//        String jpql = "select p from Pedido p join p.itens i where i.id.produtoId = 1";
//        String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        lista.forEach(pedido -> Assert.assertFalse(pedido.getItens().stream()
                .filter(item -> item.getProduto().getId() == 1)
                .collect(Collectors.toList()).isEmpty()));

//        Assert.assertTrue(lista.size() == 2);
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarPathExpression() {
        String jpql = "select p.cliente.nome from Pedido p ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
}
