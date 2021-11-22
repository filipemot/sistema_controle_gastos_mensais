package br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto;

import java.util.List;
import java.util.UUID;

public class ContaTipoContaDTO {
    private List<ContaDTO> contas;
    private Double somatorio;
    private UUID tipoContaId;
    private String nomeTipoConta;

    public List<ContaDTO> getContas() {
        return contas;
    }

    public void setContas(List<ContaDTO> contas) {
        this.contas = contas;
    }

    public Double getSomatorio() {
        return somatorio;
    }

    public void setSomatorio(Double somatorio) {
        this.somatorio = somatorio;
    }

    public UUID getTipoContaId() {
        return tipoContaId;
    }

    public void setTipoContaId(UUID tipoContaId) {
        this.tipoContaId = tipoContaId;
    }

    public String getNomeTipoConta() {
        return nomeTipoConta;
    }

    public void setNomeTipoConta(String nomeTipoConta) {
        this.nomeTipoConta = nomeTipoConta;
    }
}
