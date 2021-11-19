package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.model;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="recebidos" )
public class Recebidos {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "data_recebidos")
    private LocalDateTime dataConta;

    @Column(name = "mes_recebidos")
    private int mesConta;

    @Column(name = "ano_recebidos")
    private int anoConta;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private Double valor;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataConta() {
        return dataConta;
    }

    public void setDataConta(LocalDateTime dataConta) {
        this.dataConta = dataConta;
    }

    public int getMesConta() {
        return mesConta;
    }

    public void setMesConta(int mesConta) {
        this.mesConta = mesConta;
    }

    public int getAnoConta() {
        return anoConta;
    }

    public void setAnoConta(int anoConta) {
        this.anoConta = anoConta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
