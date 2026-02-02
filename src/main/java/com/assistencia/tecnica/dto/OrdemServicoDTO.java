package com.assistencia.tecnica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class OrdemServicoDTO {

    @NotBlank(message = "Descrição do problema é obrigatória")
    private String descricaoProblema;

    private String observacoes;

    @NotNull(message = "Valor é obrigatório")
    @PositiveOrZero(message = "Valor deve ser positivo ou zero")
    private BigDecimal valor;

    private String status;

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    // Getters e Setters
    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}