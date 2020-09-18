package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class OperadoresLogicosCriteriaTest extends EntityManagerTest {

    @Test
    public void usaOperadoresLogicos(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        // AND forma implícita
//        criteriaQuery.where(
//                criteriaBuilder.greaterThan(
//                        root.get(Pedido_.total), new BigDecimal(499)),
//                criteriaBuilder.equal(
//                        root.get(Pedido_.status), StatusPedido.PAGO));

        // AND forma explícita
//        criteriaQuery.where(
//                criteriaBuilder.and(
//                    criteriaBuilder.greaterThan(
//                            root.get(Pedido_.total), new BigDecimal(499)),
//                    criteriaBuilder.equal(
//                            root.get(Pedido_.status), StatusPedido.PAGO)));

        // Usando AND das duas maneiras
//        criteriaQuery.where(
//                criteriaBuilder.or(
//                    criteriaBuilder.greaterThan(
//                            root.get(Pedido_.total), new BigDecimal(499)),
//                    criteriaBuilder.equal(
//                            root.get(Pedido_.status), StatusPedido.PAGO)
//                ),
//                criteriaBuilder.greaterThan(
//                        root.get(Pedido_.dataCriacao), LocalDateTime.now().minusDays(5))
//        );

        // OR
        criteriaQuery.where(
                criteriaBuilder.or(
                        criteriaBuilder.greaterThan(
                                root.get(Pedido_.status), StatusPedido.AGUARDANDO),
                        criteriaBuilder.equal(
                                root.get(Pedido_.status), StatusPedido.PAGO)
                ),
                criteriaBuilder.greaterThan(
                        root.get(Pedido_.total), new BigDecimal(499))
        );


        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(p ->
                System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));
    }
}
