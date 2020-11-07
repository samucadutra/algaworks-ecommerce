package com.algaworks.ecommerce.detalhesimportantes;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityGraphTest extends EntityManagerTest {

    @Test
    public void buscarAtributosEssenciaisComNamedEnityGraph() {
        EntityGraph<?> entityGraph = entityManager.createEntityGraph("Pedido.dadosEssenciais");

        entityGraph.addAttributeNodes("pagamento");
//        entityGraph.addSubgraph("pagamento").addAttributeNodes("status");

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void buscarAtributosEssenciaisDoPedido03() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(
                Pedido_.dataCriacao, Pedido_.status, Pedido_.total);


        Subgraph<Cliente> clienteSubgraph = entityGraph.addSubgraph(Pedido_.cliente);
        clienteSubgraph.addAttributeNodes(Cliente_.nome, Cliente_.cpf);

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void buscarAtributosEssenciaisDoPedido02() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(
                "dataCriacao", "status", "total");


        Subgraph<Cliente> clienteSubgraph = entityGraph.addSubgraph("cliente", Cliente.class);
        clienteSubgraph.addAttributeNodes("nome", "cpf");

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void buscarAtributosEssenciaisDoPedido() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(
                "dataCriacao", "status", "total", "notaFiscal");

        Map<String, Object> properties = new HashMap<>();
//        properties.put("javax.persistence.fetchgraph", entityGraph); // Vai buscar todos os atributos da lista e o restante vai considerar LAZY
        properties.put("javax.persistence.loadgraph", entityGraph); // Vai buscar todos os atributos da lista e o restante vai respeitar a configuração da entidade
        Pedido pedido = entityManager.find(Pedido.class, 1, properties);
        Assert.assertNotNull(pedido);


//        TypedQuery<Pedido> typedQuery = entityManager
//                .createQuery("select p from Pedido p", Pedido.class);
//        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
//        List<Pedido> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());
    }
}