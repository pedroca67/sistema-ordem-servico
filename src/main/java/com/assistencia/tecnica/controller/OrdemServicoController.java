package com.assistencia.tecnica.controller;

import com.assistencia.tecnica.dto.AtualizarStatusDTO;
import com.assistencia.tecnica.dto.OrdemServicoDTO;
import com.assistencia.tecnica.model.OrdemServico;
import com.assistencia.tecnica.service.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para operações com ordens de serviço
 */
@RestController
@RequestMapping("/api/ordens-servico")
@Validated
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    /**
     * Cria uma nova ordem de serviço
     * @param ordemServicoDTO DTO com dados da ordem
     * @return ordem de serviço criada
     */
    @PostMapping
    public ResponseEntity<OrdemServico> criarOrdemServico(@Valid @RequestBody OrdemServicoDTO ordemServicoDTO) {
        OrdemServico ordemServico = ordemServicoService.criarOrdemServico(ordemServicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemServico);
    }

    /**
     * Lista todas as ordens de serviço
     * @return lista de ordens de serviço
     */
    @GetMapping
    public ResponseEntity<List<OrdemServico>> listarOrdensServico() {
        List<OrdemServico> ordens = ordemServicoService.listarTodas();
        return ResponseEntity.ok(ordens);
    }

    /**
     * Busca ordem de serviço pelo ID
     * @param id ID da ordem
     * @return ordem de serviço encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> buscarOrdemServico(@PathVariable Long id) {
        return ordemServicoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca ordens de serviço pelo status
     * @param status status das ordens
     * @return lista de ordens com o status informado
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemServico>> buscarPorStatus(@PathVariable String status) {
        try {
            List<OrdemServico> ordens = ordemServicoService.buscarPorStatus(status);
            return ResponseEntity.ok(ordens);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Busca ordens de serviço pelo cliente
     * @param clienteId ID do cliente
     * @return lista de ordens do cliente
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<OrdemServico>> buscarPorCliente(@PathVariable Long clienteId) {
        try {
            List<OrdemServico> ordens = ordemServicoService.buscarPorCliente(clienteId);
            return ResponseEntity.ok(ordens);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualiza o status de uma ordem de serviço
     * @param id ID da ordem
     * @param atualizarStatusDTO DTO com novo status
     * @return ordem de serviço atualizada
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<OrdemServico> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusDTO atualizarStatusDTO) {
        try {
            OrdemServico ordem = ordemServicoService.atualizarStatus(id, atualizarStatusDTO);
            return ResponseEntity.ok(ordem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualiza o valor de uma ordem de serviço
     * @param id ID da ordem
     * @param valor novo valor
     * @return ordem de serviço atualizada
     */
    @PutMapping("/{id}/valor")
    public ResponseEntity<OrdemServico> atualizarValor(
            @PathVariable Long id,
            @RequestParam BigDecimal valor) {
        try {
            OrdemServico ordem = ordemServicoService.atualizarValor(id, valor);
            return ResponseEntity.ok(ordem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Calcula o valor total faturado em um período
     * @param dataInicio data inicial (yyyy-MM-dd HH:mm:ss)
     * @param dataFim data final (yyyy-MM-dd HH:mm:ss)
     * @return valor total faturado
     */
    @GetMapping("/relatorios/total-faturado")
    public ResponseEntity<BigDecimal> calcularTotalFaturado(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataFim) {
        BigDecimal total = ordemServicoService.calcularTotalFaturadoNoPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(total);
    }

    /**
     * Busca ordens concluídas em um período
     * @param dataInicio data inicial (yyyy-MM-dd HH:mm:ss)
     * @param dataFim data final (yyyy-MM-dd HH:mm:ss)
     * @return lista de ordens concluídas
     */
    @GetMapping("/relatorios/concluidas-periodo")
    public ResponseEntity<List<OrdemServico>> buscarConcluidasNoPeriodo(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataFim) {
        List<OrdemServico> ordens = ordemServicoService.buscarConcluidasNoPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(ordens);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrdemServico> atualizarGeral(@PathVariable Long id, @Valid @RequestBody OrdemServicoDTO dto) {
        try {
            OrdemServico atualizada = ordemServicoService.atualizarGeral(id, dto);
            return ResponseEntity.ok(atualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Exclui uma ordem de serviço pelo ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOrdemServico(@PathVariable Long id) {
        try {
            ordemServicoService.excluir(id);
            return ResponseEntity.noContent().build(); // Retorna 204 (Sucesso sem conteúdo)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}