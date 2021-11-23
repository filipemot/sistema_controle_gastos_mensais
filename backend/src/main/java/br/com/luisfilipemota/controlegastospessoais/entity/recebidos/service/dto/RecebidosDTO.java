package br.com.luisfilipemota.controlegastospessoais.entity.recebidos.service.dto;

import br.com.luisfilipemota.controlegastospessoais.entity.usuario.service.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class RecebidosDTO {
    private UUID id;

    private UsuarioDTO usuario;

    private LocalDateTime dataRecebido;

    private int mesRecebido;

    private int anoRecebido;

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
