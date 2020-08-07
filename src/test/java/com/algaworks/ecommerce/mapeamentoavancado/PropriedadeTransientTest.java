package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class PropriedadeTransientTest extends EntityManagerTest {

    @Test
    public void validarPrimeiroNome() {

        Cliente cliente = entityManager.find(Cliente.class, 1);

        Assert.assertEquals("Fernando", cliente.getPrimeiroNome());
    }

}
