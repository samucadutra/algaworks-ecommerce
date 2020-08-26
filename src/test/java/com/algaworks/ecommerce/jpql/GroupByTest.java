package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void agruparResultado(){
        // Quantidade de produtos por categoria.
        // Total de vendas por mês.
        // Total de vandas por categoria.

        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1]));
    }
}
