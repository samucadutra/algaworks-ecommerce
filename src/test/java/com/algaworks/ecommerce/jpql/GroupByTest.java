package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void usarExpressaoIN() {
        Cliente cliente1 = new Cliente(); // entityManager.find(Cliente.class, 1);
        cliente1.setId(1);
        // O objeto cliente não precisa estar totalmente preenchido, basta o id

        Cliente cliente2 = new Cliente(); // entityManager.find(Cliente.class, 2);
        cliente2.setId(2);

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        // Com a expressão IN a lista não pode ser vazia

        String jpql = "select p from Pedido p where p.cliente in (:clientes)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("clientes", clientes);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void condicionarAgrupamentoComHaving() {
//         Total de vendas dentre as categorias que mais vendem.
        String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.produto pro join pro.categorias cat " +
                " group by cat.id " +
                " having avg(ip.precoProduto) > 1500 ";

        // No JPQL a cláusula 'having' só pode ser usada com funções de agregação ou com o campo que está sendo usado no group by

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void agruparEFiltrarResultado() {
        // Quantidade de produtos por categoria.
//        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        // Total de vendas por mês.
//        String jpql = "select concat(year(p.dataCriacao), '/',function('monthname', p.dataCriacao)), sum(p.total) from Pedido p " +
//                "where year(p.dataCriacao) = year(current_date) " +
//                "group by year(p.dataCriacao), month(p.dataCriacao)";

        // Total de vendas por categoria.
//        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip join ip.produto pro join pro.categorias c " +
//                "join ip.pedido p " +
//                "where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) = month(current_date) " +
//                "group by c.id";


        // Total de vendas por cliente
        // Solução professor
        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.pedido p join p.cliente c " +
                " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) >= (month(current_date) - 3)  " +
                " group by c.id";

        // Total de vendas por dia e por categoria
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

    @Test
    public void agruparResultado() {
        // Quantidade de produtos por categoria.
//        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        // Total de vendas por mês.
//        String jpql = "select concat(year(p.dataCriacao), '/',function('monthname', p.dataCriacao)), sum(p.total) from Pedido p " +
//                "group by year(p.dataCriacao), month(p.dataCriacao)";

        // Total de vendas por categoria.
//        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip join ip.produto pro join pro.categorias c group by c.id";

        // Total de vendas por cliente

        // Minha solução
//        String jpql = "select cli.nome, sum(i.precoProduto) from Cliente cli join cli.pedidos ped join ped.itens i group by cli.id";

        // Solução professor
//        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
//                " join ip.pedido p join p.cliente c " +
//                " group by c.id";


        // Total de vendas por dia e por categoria

        // Minha solução
        String jpql = "select concat(cat.nome, ' ', date(ped.dataCriacao)), sum(ip.precoProduto) from Pedido ped join ped.itens ip join ip.produto pro join pro.categorias cat group by cat.id, date(ped.dataCriacao)";

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
