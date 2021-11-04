package br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model;

import javax.persistence.*;

@Entity
@Table(name="tipo_conta" )
public class TipoConta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
