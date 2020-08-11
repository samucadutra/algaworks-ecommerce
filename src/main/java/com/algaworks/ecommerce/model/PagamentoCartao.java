package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("cartao")
//@Table(name = "pagamento_cartao") // Ignorado no caso de estratégia SINGLE_TABLE
public class PagamentoCartao extends Pagamento{

    @Column(name = "numero_cartao", length = 50)
    private String numeroCartao;

}
