package com.assistencia.tecnica.repository;

import com.assistencia.tecnica.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de CRUD com a entidade Cliente
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca clientes pelo nome (case-insensitive e partial match)
     * @param nome nome ou parte do nome do cliente
     * @return lista de clientes encontrados
     */
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Cliente> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    /**
     * Verifica se existe um cliente com o CPF informado
     * @param cpf CPF a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se existe um cliente com o email informado
     * @param email email a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);
}