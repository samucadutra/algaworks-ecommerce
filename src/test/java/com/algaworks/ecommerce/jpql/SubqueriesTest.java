package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class SubqueriesTest extends EntityManagerTest {

    @Test
    public void pesquisarComAllExercicio() {

        // Todos os produtos que sempre foram vendidos pelo mesmo preço
        // Distinct, from itemPedido, join Produto
        String jpql = "select distinct p1 from ItemPedido ip join ip.produto p1 where " +
                " ip.precoProduto = ALL " +
//                "(select preco from Produto p2 where p2 = p1)"; // Errado!!
        " (select precoProduto from ItemPedido where produto = p1 and id <> ip.id)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny() {
        // Podemos usar o ANY e o SOME.

        // Todos os produtos que já foram vendidos por um preco diferente do atual
        String jpql = "select p from Produto p " +
                " where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";

        // Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preço atual.
//        String jpql = "select p from Produto p " +
//                " where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAll() {
        // Todos os produtos não foram vendidos mais depois que encareceram
        String jpql = "select p from Produto p where " +
                " p.preco > ALL (select precoProduto from ItemPedido where produto = p)";

        // Todos os produtos que sempre foram vendidos pelo preco atual.
//        String jpql = "select p from Produto p where " +
//                " p.preco = ALL (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    // Exercício: todos os produtos que não foram vendidos ainda pelo preço atual
    @Test
    public void pesquisarComExistsExercicio() {
        String jpql = "select p from Produto p where exists " +
                " (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p and p.preco <> ip2.precoProduto)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarClientesCom2OuMaisPedidos() {

        // Exercício: todos os clientes que fizeram 2 ou mais pedidos
        String jpql = "select c from Cliente c where " +
                " 2 <= (select count(p.id) from Pedido p where p.cliente = c)";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }

    @Test
    public void pesquisarComExists() {
        String jpql = "select p from Produto p where exists " +
                " (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComIN() {
//        String jpql = "select p from Pedido p where p.id in " +
//                " (select p2.id from ItemPedido i2 " +
//                "      join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

        // Exercício: todos os pedidos que contenham algum produto da categoria de id = 2
        String jpql = "select p from Pedido p where p.id in " +
                " (select p2.id from ItemPedido i2 " +
                "      join i2.pedido p2 join i2.produto pro2 join pro2.categorias cat where cat.id = 2)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarSubqueries() {
//         Bons clientes. Versão 2.
        String jpql = "select c from Cliente c where " +
                " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));

//         Bons clientes. Versão 1.
//        String jpql = "select c from Cliente c where " +
//                " 500 < (select sum(p.total) from c.pedidos p)";
//
//        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
//        List<Cliente> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());
//        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));


//         Todos os pedidos acima da média de vendas
//        String jpql = "select p from Pedido p where " +
//                " p.total > (select avg(total) from Pedido)";
//
//        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
//        List<Pedido> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());
//        lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));


//         O produto ou os produtos mais caros da base.
//        String jpql = "select p from Produto p where " +
//                " p.preco = (select max(preco) from Produto)";
//
//        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
//        List<Produto> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());
//        lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Preço: " + p.getPreco()));
//        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }
}
