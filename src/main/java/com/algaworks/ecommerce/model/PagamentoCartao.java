package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("cartao")
//@Table(name = "pagamento_cartao") Ignorado por causa da herança com a classe Pagamento
public class PagamentoCartao extends Pagamento{

    @Column(name = "numero_cartao")
    private String numeroCartao;

}
