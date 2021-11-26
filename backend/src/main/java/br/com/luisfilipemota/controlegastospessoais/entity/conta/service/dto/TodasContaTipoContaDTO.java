package br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto;

import java.util.List;
import java.util.UUID;

public class TodasContaTipoContaDTO {
    public List<ContaTipoContaDTO> getContas() {
        return contas;
    }

    public void setContas(List<ContaTipoContaDTO> contas) {
        this.contas = contas;
    }

    private List<ContaTipoContaDTO> contas;
    private Double somatorio;


    public Double getSomatorio() {
        return somatorio;
    }

    public void setSomatorio(Double somatorio) {
        this.somatorio = somatorio;
    }
}
