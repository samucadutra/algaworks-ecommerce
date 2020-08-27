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
//        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        // Total de vendas por mês.
//        String jpql = "select concat(year(p.dataCriacao), '/',function('monthname', p.dataCriacao)), sum(p.total) from Pedido p " +
//                "group by year(p.dataCriacao), month(p.dataCriacao)";

        // Total de vandas por categoria.
//        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip join ip.produto pro join pro.categorias c group by c.id";

        // Total de vendas por cliente
//        String jpql = "select cli.nome, sum(i.precoProduto) from Cliente cli join cli.pedidos ped join ped.itens i group by cli.id"; // Minha solução

        // Solução professor
//        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
//                " join ip.pedido p join p.cliente c " +
//                " group by c.id";


        // Total de vendas por dia e por categoria
        String jpql = "select concat(cat.nome, ' ', date(ped.dataCriacao)), sum(ip.precoProduto) from Pedido ped join ped.itens ip join ip.produto pro join pro.categorias cat group by cat.id, date(ped.dataCriacao)"; // Minha solução

        // Solução professor
//        String jpql = "select " +
//                " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), " +
//                " concat(c.nome, ': ', sum(ip.precoProduto)) " +
//                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
//                " group by year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao), c.id " +
//                " order by p.dataCriacao, c.nome ";



        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1]));
    }
}
