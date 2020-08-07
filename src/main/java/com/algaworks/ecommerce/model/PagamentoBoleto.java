package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("boleto")
//@Table(name = "pagamento_boleto") Ignorado por causa da herança com a classe Pagamento
public class PagamentoBoleto extends Pagamento{


    @Column(name = "codigo_barras")
    private String codigoBarras;
}
