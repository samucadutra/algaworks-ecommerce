package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import org.junit.Test;

public class EstadosECicloDeVidaTest extends EntityManagerTest {

    @Test
    public void analisarEstados() {
        Categoria categoriaNovo = new Categoria(); // transient
        categoriaNovo.setNome("Eletrônicos");

        Categoria categoriaGerenciadaMerge = entityManager.merge(categoriaNovo); // managed

        Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1); // managed

        entityManager.remove(categoriaGerenciada); // removed
        entityManager.persist(categoriaGerenciada); // managed

        entityManager.detach(categoriaGerenciada); // detached
    }
}
