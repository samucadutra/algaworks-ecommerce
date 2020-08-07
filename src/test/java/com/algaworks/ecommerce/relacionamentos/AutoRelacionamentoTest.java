package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AutoRelacionamentoTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento(){
        Categoria categoriaPai = new Categoria();
        categoriaPai.setNome("Eletrônicos");

        Categoria categoria = new Categoria();
        categoria.setNome("Celulares");
        categoria.setCategoriaPai(categoriaPai);

        entityManager.getTransaction().begin();
        entityManager.persist(categoriaPai);
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificiacao = entityManager.find(Categoria.class, categoria.getId());

        Assert.assertNotNull(categoriaVerificiacao.getCategoriaPai());

        Categoria categoriaPaiVerificiacao = entityManager.find(Categoria.class, categoriaPai.getId());

        Assert.assertFalse(categoriaPaiVerificiacao.getCategorias().isEmpty());
    }
}
