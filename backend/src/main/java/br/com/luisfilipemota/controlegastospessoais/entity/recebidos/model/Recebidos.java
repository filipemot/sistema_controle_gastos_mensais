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
    private LocalDateTime dataRecebido;

    @Column(name = "mes_recebidos")
    private int mesRecebido;

    @Column(name = "ano_recebidos")
    private int anoRecebido;

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

    public LocalDateTime getDataRecebido() {
        return dataRecebido;
    }

    public void setDataRecebido(LocalDateTime dataRecebido) {
        this.dataRecebido = dataRecebido;
    }

    public int getMesRecebido() {
        return mesRecebido;
    }

    public void setMesRecebido(int mesRecebido) {
        this.mesRecebido = mesRecebido;
    }

    public int getAnoRecebido() {
        return anoRecebido;
    }

    public void setAnoRecebido(int anoRecebido) {
        this.anoRecebido = anoRecebido;
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
