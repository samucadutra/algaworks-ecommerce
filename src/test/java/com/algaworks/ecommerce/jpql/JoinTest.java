package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class JoinTest extends EntityManagerTest {

    @Test
    public void usarJoinFetch() {
        String jpql = "select p from Pedido p " +
                "left join fetch p.pagamento " +
                "join fetch p.cliente " +
                "left Join fetch p.notaFiscal";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void fazerLeftJoin() {
        String jpql = "select p from Pedido p left join p.pagamento pag";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void fazerJoin() {
//        String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";
        String jpql = "select p from Pedido p join p.pagamento pag";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
}
