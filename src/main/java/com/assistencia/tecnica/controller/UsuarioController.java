package com.assistencia.tecnica.controller;

import com.assistencia.tecnica.dto.UsuarioDTO;
import com.assistencia.tecnica.model.Usuario;
import com.assistencia.tecnica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para operações com usuários
 */
@RestController
@RequestMapping("/api/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Cria um novo usuário
     * @param usuarioDTO DTO com dados do usuário
     * @return usuário criado
     */
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    /**
     * Lista todos os usuários
     * @return lista de usuários
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Busca usuário pelo ID
     * @param id ID do usuário
     * @return usuário encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza um usuário
     * @param id ID do usuário
     * @param usuarioDTO DTO com dados atualizados
     * @return usuário atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.atualizarUsuario(id, usuarioDTO);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Exclui um usuário
     * @param id ID do usuário
     * @return resposta vazia
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        try {
            usuarioService.excluirUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}