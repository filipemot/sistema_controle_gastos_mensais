package br.com.luisfilipemota.controlegastospessoais.entity.conta.model;

import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.model.TipoConta;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.model.Usuario;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="contas" )
public class Conta {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_conta")
    private TipoConta tipoConta;

    @Column(name = "data_conta")
    private LocalDateTime dataConta;

    @Column(name = "mes_conta")
    private int mesConta;

    @Column(name = "ano_conta")
    private int anoConta;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "numero_parcela")
    private int numeroParcela;

    @Column(name = "total_parcelas")
    private int totalParcelas;

    @Column(name = "recorrente")
    private Boolean recorrente;

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

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
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

    public int getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public int getTotalParcelas() {
        return totalParcelas;
    }

    public void setTotalParcelas(int totalParcelas) {
        this.totalParcelas = totalParcelas;
    }

    public Boolean getRecorrente() {
        return recorrente;
    }

    public void setRecorrente(Boolean recorrente) {
        this.recorrente = recorrente;
    }
}
