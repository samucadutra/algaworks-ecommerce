package com.algaworks.ecommerce.detalhesimportantes;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityGraph;
import java.time.LocalDateTime;
import java.util.List;

public class ProblemaN1Test extends EntityManagerTest {

    @Test
    public void resolverComEntityGraph() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(Pedido_.cliente, Pedido_.notaFiscal, Pedido_.pagamento);

        List<Pedido> lista = entityManager.createQuery("select p from Pedido p ", Pedido.class).setHint("javax.persistence.loadgraph", entityGraph).getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void resolverComFetch() {
        List<Pedido> lista = entityManager.createQuery("select p from Pedido p join fetch p.cliente c join fetch p.pagamento pag join fetch p.notaFiscal nf", Pedido.class).getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
}