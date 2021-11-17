package br.com.luisfilipemota.controlegastospessoais.entity.tipoconta.service.dto;

import java.util.UUID;

public class TipoContaDTO {

    private UUID id;

    private String descricao;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
