package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

public class PrimeiroCrudTest extends EntityManagerTest {

    @Test
    public void inserirRegistro() {
        Cliente cliente = new Cliente();
        cliente.setId(3);
        cliente.setNome("Samuel Dutra");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificacao);
    }

    @Test
    public void atualizarRegistro(){
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setNome("Rita Medeiro");

        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertEquals("Rita Medeiro", clienteVerificacao.getNome());
    }

    @Test
    public void buscaPorIdentificador(){
        Cliente cliente = entityManager.find(Cliente.class, 1);
        Assert.assertNotNull(cliente);
        Assert.assertEquals("Fernando Medeiros", cliente.getNome());
    }

    @Test
    public void removerRegistro(){
        Cliente cliente = entityManager.find(Cliente.class, 2);

        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNull(clienteVerificacao);
    }

}
