package com.assistencia.tecnica.dto;

import jakarta.validation.constraints.NotBlank;

public class AtualizarStatusDTO {

    @NotBlank(message = "Status é obrigatório")
    private String status;

    private String observacoes;

    // Getters e Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}