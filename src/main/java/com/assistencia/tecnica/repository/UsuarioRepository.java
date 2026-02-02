package com.assistencia.tecnica.repository;

import com.assistencia.tecnica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de CRUD com a entidade Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo username
     * @param username username do usuário
     * @return Optional contendo o usuário encontrado
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Verifica se existe um usuário com o username informado
     * @param username username a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByUsername(String username);
}