package br.com.luisfilipemota.controlegastospessoais.entity.conta.service.dto;

import java.util.List;

public class ContaSomatorioDTO {
    private List<ContaDTO> contas;
    private Double somatorio;

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
}
