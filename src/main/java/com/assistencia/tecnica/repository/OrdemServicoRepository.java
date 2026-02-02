package com.assistencia.tecnica.repository;

import com.assistencia.tecnica.model.Cliente;
import com.assistencia.tecnica.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações de CRUD com a entidade OrdemServico
 */
@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    /**
     * Busca ordens de serviço pelo status
     * @param status status das ordens
     * @return lista de ordens de serviço com o status informado
     */
    List<OrdemServico> findByStatus(OrdemServico.StatusOrdem status);

    /**
     * Busca ordens de serviço pelo cliente
     * @param cliente cliente das ordens
     * @return lista de ordens de serviço do cliente informado
     */
    List<OrdemServico> findByCliente(Cliente cliente);

    /**
     * Busca ordens de serviço concluídas em um período
     * @param dataInicio data inicial do período
     * @param dataFim data final do período
     * @return lista de ordens de serviço concluídas no período
     */
    @Query("SELECT o FROM OrdemServico o WHERE o.status = 'CONCLUIDA' " +
            "AND o.dataAbertura BETWEEN :dataInicio AND :dataFim")
    List<OrdemServico> findConcluidasNoPeriodo(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);

    /**
     * Calcula o valor total faturado em um período
     * @param dataInicio data inicial do período
     * @param dataFim data final do período
     * @return valor total faturado no período
     */
    @Query("SELECT COALESCE(SUM(o.valor), 0) FROM OrdemServico o " +
            "WHERE o.status = 'CONCLUIDA' " +
            "AND o.dataAbertura BETWEEN :dataInicio AND :dataFim")
    BigDecimal calcularTotalFaturadoNoPeriodo(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);
}