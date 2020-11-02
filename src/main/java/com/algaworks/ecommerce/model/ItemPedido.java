package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
//@IdClass(ItemPedidoId.class)
public class ItemPedido {

    @EmbeddedId
    private ItemPedidoId id;

    @NotNull
    @MapsId("pedidoId")
    @ManyToOne(optional = false)//, cascade = CascadeType.REMOVE)//, cascade = CascadeType.MERGE)
    @JoinColumn(name = "pedido_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_pedido"))
    private Pedido pedido;

    @NotNull
    @MapsId("produtoId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_produto"))
    private Produto produto;

    @Positive
    @NotNull
    @Column(name = "preco_produto", precision = 19, scale = 2, nullable = false) // precision e scale estão com os valores padrão
    private BigDecimal precoProduto;

    @Positive
    @NotNull
    @Column(nullable = false)
    private Integer quantidade;
}
