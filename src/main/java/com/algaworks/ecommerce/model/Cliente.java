package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SecondaryTable(name = "cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"))
@Entity
@Table(name = "cliente",
        uniqueConstraints = { @UniqueConstraint(name = "unq_cpf", columnNames = { "cpf" }) },
        indexes = { @Index(name = "idx_nome", columnList = "nome") })
public class Cliente extends EntidadeBaseInteger{


    private String nome;

    private String cpf;

    @ElementCollection
    @CollectionTable(name = "cliente_contato",
            joinColumns = @JoinColumn(name = "cliente_id"))
    @MapKeyColumn(name = "tipo")
    @Column(name = "descricao")
    private Map<String, String> contatos;

    @Transient
    private String primeiroNome;

    @Column(table = "cliente_detalhe")
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;

    @Column(table = "cliente_detalhe", name = "data_nascimento")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @PostLoad
    public void configurarPrimeiroNome(){
        if(nome != null && !nome.isBlank()){
            int index = nome.indexOf(" ");
            if (index > -1){
                primeiroNome = nome.substring(0, index);
            }
        }
    }
}
