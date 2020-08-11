package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("boleto")
//@Table(name = "pagamento_boleto") // Ignorado no caso de estratégia SINGLE_TABLE
public class PagamentoBoleto extends Pagamento{


    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;
}
