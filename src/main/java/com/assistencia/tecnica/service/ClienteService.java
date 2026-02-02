package com.assistencia.tecnica.service;

import com.assistencia.tecnica.dto.ClienteDTO;
import com.assistencia.tecnica.exception.ClienteExistenteException;
import com.assistencia.tecnica.model.Cliente;
import com.assistencia.tecnica.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente criarCliente(ClienteDTO clienteDTO) {
        // Verifica se já existe cliente com o mesmo CPF
        if (clienteDTO.getCpf() != null && clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            throw new ClienteExistenteException("Já existe cliente com CPF: " + clienteDTO.getCpf());
        }

        // Verifica se já existe cliente com o mesmo email
        if (clienteDTO.getEmail() != null && clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new ClienteExistenteException("Já existe cliente com email: " + clienteDTO.getEmail());
        }

        // Cria novo cliente
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEndereco(clienteDTO.getEndereco());

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Cliente atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        // Atualiza dados
        if (clienteDTO.getNome() != null) {
            cliente.setNome(clienteDTO.getNome());
        }

        if (clienteDTO.getEmail() != null) {
            cliente.setEmail(clienteDTO.getEmail());
        }

        if (clienteDTO.getTelefone() != null) {
            cliente.setTelefone(clienteDTO.getTelefone());
        }

        if (clienteDTO.getEndereco() != null) {
            cliente.setEndereco(clienteDTO.getEndereco());
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void excluirCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}