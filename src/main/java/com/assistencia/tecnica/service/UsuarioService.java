package com.assistencia.tecnica.service;

import com.assistencia.tecnica.dto.UsuarioDTO;
import com.assistencia.tecnica.exception.UsuarioExistenteException;
import com.assistencia.tecnica.model.Usuario;
import com.assistencia.tecnica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para operações de negócio com usuários
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Cria um novo usuário
     * @param usuarioDTO DTO com os dados do usuário
     * @return usuário criado
     * @throws UsuarioExistenteException se já existir usuário com o mesmo username
     */
    @Transactional
    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        // Verifica se já existe usuário com o mesmo username
        if (usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
            throw new UsuarioExistenteException("Username já está em uso: " + usuarioDTO.getUsername());
        }

        // Cria novo usuário
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        // Define role (padrão: USER)
        if (usuarioDTO.getRole() != null && !usuarioDTO.getRole().isEmpty()) {
            usuario.getRoles().clear();
            usuario.addRole(usuarioDTO.getRole());
        }

        return usuarioRepository.save(usuario);
    }

    /**
     * Busca todos os usuários
     * @return lista de todos os usuários
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca usuário pelo ID
     * @param id ID do usuário
     * @return usuário encontrado
     */
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca usuário pelo username
     * @param username username do usuário
     * @return usuário encontrado
     */
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Atualiza um usuário existente
     * @param id ID do usuário
     * @param usuarioDTO DTO com os dados atualizados
     * @return usuário atualizado
     */
    @Transactional
    public Usuario atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Atualiza dados se fornecidos
        if (usuarioDTO.getNome() != null) {
            usuario.setNome(usuarioDTO.getNome());
        }

        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    /**
     * Exclui um usuário
     * @param id ID do usuário
     */
    @Transactional
    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}