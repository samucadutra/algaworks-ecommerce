package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class FuncoesTest extends EntityManagerTest {

    @Test
    public void aplicarFuncaoAgregacao() {
        // avg, count, min, max, sum

//        String jpql = "select avg(p.total) from Pedido p";
//        String jpql = "select avg(p.total) from Pedido p where p.dataCriacao >= current_date";
//        String jpql = "select count(p.dataCriacao) from Pedido p";
//        String jpql = "select min(p.total) from Pedido p";
//        String jpql = "select max(p.total) from Pedido p";
        String jpql = "select sum(p.total) from Pedido p";

        // Utilizando Number pois os tipos numéricos do Java extendem Number, assim atende a qualquer resultados
        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);

        List<Number> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(obj));
    }

    @Test
    public void aplicarFuncaoNativas() {

// Teste 1
        String jpql = "select function('dayname', p.dataCriacao) from Pedido p " +
                " where function('acima_media_faturamento', p.total) = 1";
        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> lista = typedQuery.getResultList();

// Teste 2
//        String jpql = "select p from Pedido p where function('acima_media_faturamento', p.total) = 1";
//        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
//        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(obj));
    }

    @Test
    public void aplicarFuncaoColecao() {
        String jpql = "select size(p.itens) from Pedido p where size(p.itens) > 1";

        TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);

        List<Integer> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(size -> System.out.println(size));
    }

    @Test
    public void aplicarFuncaoNumero(){

//        String jpql = "select abs(-10), mod(3,2), sqrt(9) from Pedido";
//        String jpql = "select abs(p.total), mod(p.id,2), sqrt(p.total) from Pedido p";
        String jpql = "select abs(p.total), mod(p.id,2), sqrt(p.total) from Pedido p" +
                " where abs(p.total) > 1000";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

    @Test
    public void aplicarFuncaoData() {
         TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        // current_date, current_time, current_timestamp
        // year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao)

//        String jpql = "select current_date, current_time, current_timestamp from Pedido p";
//        String jpql = "select current_date, current_time, current_timestamp from Pedido p " +
//                "where p.dataCriacao < current_date";
//        String jpql = "select year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao) from Pedido p";
        String jpql = "select hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao) " +
                " from Pedido p where hour(p.dataCriacao) > 18";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

    @Test
    public void aplicarFuncaoString() {
        // concat, length, locate, substring, lower, upper, trim

//        String jpql = "select c.nome, concat('Categoria: ', c.nome) from Categoria c";
//        String jpql = "select c.nome, length(c.nome) from Categoria c";
//        String jpql = "select c.nome, locate('a', c.nome) from Categoria c"; // Mostra o índice da string que está no primeiro parâmetro
//        String jpql = "select c.nome, substring(c.nome, 1, 2) from Categoria c";
//        String jpql = "select c.nome, lower(c.nome) from Categoria c";
//        String jpql = "select c.nome, upper(c.nome) from Categoria c";
//        String jpql = "select c.nome, trim(c.nome) from Categoria c";
        String jpql = "select c.nome, length(c.nome) from Categoria c where length(c.nome) > 10";

//        String jpql = "select c.nome, length(c.nome) from Categoria c " +
//                " where substring(c.nome, 1, 1) = 'N'";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }
}
