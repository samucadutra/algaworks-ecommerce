package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OperacoesComTransacaoTest extends EntityManagerTest {

    @Test
    public void impedirOperacaoComBancoDeDados() {
        Produto produto = entityManager.find(Produto.class, 1);
        entityManager.detach(produto); // Faz com que o Entity Manager deixe de gerenciar o objeto não causando alteração no registro do Banco de Dados

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paperwhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

//        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
//        Assert.assertEquals("Kindle Paperwhite 2ª Geração", produtoVerificacao.getNome());
    }

    @Test
    public void mostrarDiferencaPersistMerge() {
        Produto produtoPersist = new Produto();
//        produtoPersist.setId(5);
        produtoPersist.setNome("Smartphone One Plus");
        produtoPersist.setDescricao("O processador mais rápido");
        produtoPersist.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        entityManager.persist(produtoPersist);
        produtoPersist.setNome("Smartphone Two Plus"); // Aqui ele irá executar um insert e um update
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produtoPersist.getId());
        Assert.assertNotNull(produtoVerificacao);
        
        Produto produtoMerge = new Produto();
//        produtoMerge.setId(6);
        produtoMerge.setNome("Notebook Dell");
        produtoMerge.setDescricao("O melhor da categoria");
        produtoMerge.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();

        // Caso 1
        // Aqui ele não vai fazer nada no BD porque no merge ele salva uma cópia do objeto no banco de dados.
        // Então quando há o método setNome ele não modifica o estado no banco de dados
//        entityManager.merge(produtoMerge);
//        produtoMerge.setNome("Notebook Dell 2");

        // Caso 2
        // Aqui ele modifica o registro do objeto no banco de dados também porque o objeto recebe de volta
        // a cópia, que foi o objeto utilizado para realizar a persistência
        produtoMerge = entityManager.merge(produtoMerge);
        produtoMerge.setNome("Notebook Dell 2");

        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacaoMerge = entityManager.find(Produto.class, produtoMerge.getId());
        Assert.assertNotNull(produtoVerificacaoMerge);
    }

    @Test
    public void inserirObjetoComMerge() {
        Produto produto = new Produto();
//        produto.setId(4);
        produto.setNome("Microfone Rode Videmic");
        produto.setDescricao("A melhor qualidade de som");
        produto.setPreco(new BigDecimal(1000));

        entityManager.getTransaction().begin();
        Produto produtoSalvo = entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produtoSalvo.getId());
        Assert.assertNotNull(produtoVerificacao);
    }

    @Test
    public void atualizarObjetoGerenciado() {
        Produto produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paperwhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals("Kindle Paperwhite 2ª Geração", produtoVerificacao.getNome());
    }

    @Test
    public void atualizarObjeto() {
        Produto produto = new Produto();

        produto.setId(1);
        produto.setNome("Kindle Paperwhite");
        produto.setDescricao("Conheça o novo Kindle.");
        produto.setPreco(new BigDecimal(599));

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificacao);
        Assert.assertEquals("Kindle Paperwhite", produtoVerificacao.getNome());
    }

    @Test
    public void removerObjeto() {

        Produto produto = entityManager.find(Produto.class, 3);

        entityManager.getTransaction().begin();

        // o método remove não precisa necessariamente estar entre o begin e o commit. se ele estiver antes, vai esperar abrir uma transação para executar a inserção no BD
        entityManager.remove(produto);

        entityManager.getTransaction().commit();

//        entityManager.clear(); // Não é necessário para a asserção no caso de remoção

        Produto produtoVerificacao = entityManager.find(Produto.class, 3);

        Assert.assertNull(produtoVerificacao);
    }

    @Test
    public void inserirPrimeiroObjeto() {
        Produto produto = new Produto();
//        produto.setId(2);
        produto.setNome("Câmera Canon");
        produto.setDescricao("A melhor definição para suas fotos");
        produto.setPreco(new BigDecimal(5000));

        entityManager.getTransaction().begin();

        // o método persist não precisa necessariamente estar entre o begin e o commit. se ele estiver antes, vai esperar abrir uma transação para executar a inserção no BD
        entityManager.persist(produto);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificacao);
    }

    @Test
    public void abrirEFecharTransacao(){
        Produto produto = new Produto();

        entityManager.getTransaction().begin(); //Início de uma transação

        // Métodos que precisam ter a transação aberta
//        entityManager.persist(produto);
//        entityManager.merge(produto);
//        entityManager.remove(produto);

        entityManager.getTransaction().commit(); //Fim de uma transação
    }
}
