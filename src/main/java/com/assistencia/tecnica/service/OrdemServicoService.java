package com.assistencia.tecnica.service;

import com.assistencia.tecnica.dto.AtualizarStatusDTO;
import com.assistencia.tecnica.dto.OrdemServicoDTO;
import com.assistencia.tecnica.model.Cliente;
import com.assistencia.tecnica.model.OrdemServico;
import com.assistencia.tecnica.repository.ClienteRepository;
import com.assistencia.tecnica.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public OrdemServico criarOrdemServico(OrdemServicoDTO ordemServicoDTO) {
        // Busca cliente
        Cliente cliente = clienteRepository.findById(ordemServicoDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        // Cria nova ordem de serviço
        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setDescricaoProblema(ordemServicoDTO.getDescricaoProblema());

        if (ordemServicoDTO.getObservacoes() != null) {
            ordemServico.setObservacoes(ordemServicoDTO.getObservacoes());
        }

        if (ordemServicoDTO.getValor() != null) {
            ordemServico.setValor(ordemServicoDTO.getValor());
        }

        ordemServico.setCliente(cliente);

        // Define status se fornecido
        if (ordemServicoDTO.getStatus() != null) {
            try {
                OrdemServico.StatusOrdem status = OrdemServico.StatusOrdem.valueOf(ordemServicoDTO.getStatus());
                ordemServico.setStatus(status);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido: " + ordemServicoDTO.getStatus());
            }
        }

        return ordemServicoRepository.save(ordemServico);
    }

    public List<OrdemServico> listarTodas() {
        return ordemServicoRepository.findAll();
    }

    public Optional<OrdemServico> buscarPorId(Long id) {
        return ordemServicoRepository.findById(id);
    }

    public List<OrdemServico> buscarPorStatus(String status) {
        try {
            OrdemServico.StatusOrdem statusEnum = OrdemServico.StatusOrdem.valueOf(status);
            return ordemServicoRepository.findByStatus(statusEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }

    public List<OrdemServico> buscarPorCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        return ordemServicoRepository.findByCliente(cliente);
    }

    @Transactional
    public OrdemServico atualizarStatus(Long id, AtualizarStatusDTO atualizarStatusDTO) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada"));

        // Atualiza status
        try {
            OrdemServico.StatusOrdem novoStatus = OrdemServico.StatusOrdem.valueOf(atualizarStatusDTO.getStatus());
            ordemServico.setStatus(novoStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + atualizarStatusDTO.getStatus());
        }

        // Adiciona observações se fornecidas
        if (atualizarStatusDTO.getObservacoes() != null && !atualizarStatusDTO.getObservacoes().isEmpty()) {
            String observacoesAtuais = ordemServico.getObservacoes();
            String novasObservacoes = atualizarStatusDTO.getObservacoes();

            if (observacoesAtuais != null && !observacoesAtuais.isEmpty()) {
                ordemServico.setObservacoes(observacoesAtuais + "\n---\n" + novasObservacoes);
            } else {
                ordemServico.setObservacoes(novasObservacoes);
            }
        }

        return ordemServicoRepository.save(ordemServico);
    }

    @Transactional
    public OrdemServico atualizarValor(Long id, BigDecimal valor) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada"));

        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }

        ordemServico.setValor(valor);
        return ordemServicoRepository.save(ordemServico);
    }

    public BigDecimal calcularTotalFaturadoNoPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return ordemServicoRepository.calcularTotalFaturadoNoPeriodo(dataInicio, dataFim);
    }

    public List<OrdemServico> buscarConcluidasNoPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return ordemServicoRepository.findConcluidasNoPeriodo(dataInicio, dataFim);
    }
    public OrdemServico atualizarGeral(Long id, OrdemServicoDTO dto) {
        // 1. Busca a OS atual
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));

        // 2. Atualiza os campos básicos
        os.setDescricaoProblema(dto.getDescricaoProblema());
        os.setObservacoes(dto.getObservacoes());
        os.setValor(dto.getValor());

        // 3. Atualiza o Status (convertendo String para o Enum)
        if (dto.getStatus() != null) {
            os.setStatus(OrdemServico.StatusOrdem.valueOf(dto.getStatus()));
        }

        // 4. Salva as mudanças
        return ordemServicoRepository.save(os);


    }
    public void excluir(Long id) {
        // Busca para garantir que existe antes de tentar deletar
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));

        ordemServicoRepository.delete(os);
    }
}