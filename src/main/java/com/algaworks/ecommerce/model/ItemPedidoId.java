package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ItemPedidoId implements Serializable {

//    @Id
    @EqualsAndHashCode.Include
    @Column(name = "pedido_id")
    private Integer pedidoId;

//    @Id
    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Integer produtoId;


}
