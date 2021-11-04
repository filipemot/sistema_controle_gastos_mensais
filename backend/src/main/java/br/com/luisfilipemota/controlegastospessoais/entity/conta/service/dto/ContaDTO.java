package br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto;

import br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto.TipoContaDTO;
import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ContaDTO {
    private Long id;

    private UsuarioDTO usuario;

    private TipoContaDTO tipoConta;


    private LocalDateTime dataConta;

    private int mesConta;

    private int anoConta;

    private String descricao;

    private Double valor;

    private int numeroParcela;

    private int totalParcelas;

    private Boolean recorrente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public TipoContaDTO getTipoConta() {
        return tipoConta;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getDataConta() {
        return dataConta;
    }

    public void setDataConta(LocalDateTime dataConta) {
        this.dataConta = dataConta;
    }

    public void setTipoConta(TipoContaDTO tipoConta) {
        this.tipoConta = tipoConta;
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
