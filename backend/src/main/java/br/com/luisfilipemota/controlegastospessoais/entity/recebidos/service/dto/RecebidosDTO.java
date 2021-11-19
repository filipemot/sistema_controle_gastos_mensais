package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class RecebidosDTO {
    private UUID id;

    private UsuarioDTO usuario;

    private LocalDateTime dataConta;

    private int mesConta;

    private int anoConta;

    private String descricao;

    private Double valor;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
