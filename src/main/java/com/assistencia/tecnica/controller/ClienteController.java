package com.assistencia.tecnica.controller;

import com.assistencia.tecnica.dto.ClienteDTO;
import com.assistencia.tecnica.model.Cliente;
import com.assistencia.tecnica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para operações com clientes
 */
@RestController
@RequestMapping("/api/clientes")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Cria um novo cliente
     * @param clienteDTO DTO com dados do cliente
     * @return cliente criado
     */
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.criarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    /**
     * Lista todos os clientes
     * @return lista de clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Busca cliente pelo ID
     * @param id ID do cliente
     * @return cliente encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca clientes pelo nome
     * @param nome nome ou parte do nome
     * @return lista de clientes encontrados
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

    /**
     * Atualiza um cliente
     * @param id ID do cliente
     * @param clienteDTO DTO com dados atualizados
     * @return cliente atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteService.atualizarCliente(id, clienteDTO);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Exclui um cliente
     * @param id ID do cliente
     * @return resposta vazia
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        try {
            clienteService.excluirCliente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}