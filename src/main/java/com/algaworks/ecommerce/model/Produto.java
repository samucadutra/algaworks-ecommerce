package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscalListener;
import com.algaworks.ecommerce.model.converter.BooleanToSimNaoConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NamedNativeQueries({
        @NamedNativeQuery(name = "produto_loja.listar", query = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto from produto_loja", resultClass = Produto.class),
        @NamedNativeQuery(name = "ecm_produto.listar", query = "select * from ecm_produto", resultSetMapping = "ecm_produto.Produto")
})

@SqlResultSetMappings({
        @SqlResultSetMapping(name = "produto_loja.Produto",
                entities = {@EntityResult(entityClass = Produto.class)}),
        @SqlResultSetMapping(name = "item_pedido-produto.ItemPedido-Produto",
                entities = {@EntityResult(entityClass = ItemPedido.class),
                        @EntityResult(entityClass = Produto.class)}),
        @SqlResultSetMapping(name = "ecm_produto.Produto",
        entities = { @EntityResult(entityClass = Produto.class,
        fields = {
                @FieldResult(name = "id", column = "prd_id"),
                @FieldResult(name = "nome", column = "prd_nome"),
                @FieldResult(name = "descricao", column = "prd_descricao"),
                @FieldResult(name = "preco", column = "prd_preco"),
                @FieldResult(name = "foto", column = "prd_foto"),
                @FieldResult(name = "dataCriacao", column = "prd_data_criacao"),
                @FieldResult(name = "dataUltimaAtualizacao",
                        column = "prd_data_ultima_atualizacao")
        }) }),
        @SqlResultSetMapping(name = "ecm_produto.ProdutoDTO",
        classes = {
                @ConstructorResult(targetClass = ProdutoDTO.class,
                        columns = {
                                @ColumnResult(name = "prd_id", type = Integer.class),
                                @ColumnResult(name = "prd_nome", type = String.class)
                        })
        })
})
@NamedQueries({
        @NamedQuery(name = "Produto.listar", query = "select p from Produto p"),
        @NamedQuery(name = "Produto.listarPorCategoria", query = "select p from Produto p where exists (select 1 from Categoria c2 join c2.produtos p2 where p2 = p and c2.id = :categoria)")
})
@EntityListeners({ GenericoListener.class})
@Entity
@Table(name = "produto",
        uniqueConstraints = { @UniqueConstraint(name = "unq_nome", columnNames = { "nome" }) },
        indexes = { @Index(name = "idx_nome", columnList = "nome") })
public class Produto extends EntidadeBaseInteger{


    @PastOrPresent
    @NotNull
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @PastOrPresent
    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

//    @Column(columnDefinition = "varchar(275) default 'descricao'")
    @Lob
    private String descricao;

//    @Column(precision = 10, scale = 2) // preco decimal(10,2)
    @Positive
    private BigDecimal preco;

    @ManyToMany//(cascade = CascadeType.MERGE) //(cascade = CascadeType.PERSIST)
    @JoinTable(name = "produto_categoria",
            joinColumns = @JoinColumn(name = "produto_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_produto_categoria_produto")),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_produto_categoria_categoria")))
    private List<Categoria> categorias;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @ElementCollection
    @CollectionTable(name = "produto_tag",
            joinColumns = @JoinColumn(name = "produto_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_produto_tag_produto")))
    @Column(name = "tag", length = 50, nullable = false)
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "produto_atributo",
            joinColumns = @JoinColumn(name = "produto_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_produto_atributo_produto")))
    private List<Atributo> atributos;

    @Lob
    private byte[] foto;

    @Convert(converter = BooleanToSimNaoConverter.class)
    @NotNull
    @Column(length = 3, nullable = false)
    private Boolean ativo = Boolean.FALSE;

}
