package com.assistencia.tecnica.config;

import com.assistencia.tecnica.model.Cliente;
import com.assistencia.tecnica.model.OrdemServico;
import com.assistencia.tecnica.model.Usuario;
import com.assistencia.tecnica.repository.ClienteRepository;
import com.assistencia.tecnica.repository.OrdemServicoRepository;
import com.assistencia.tecnica.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner carregarDadosIniciais(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            OrdemServicoRepository ordemServicoRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Usuários
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setUsername("admin");
                admin.setSenha(passwordEncoder.encode("admin"));
                admin.getRoles().clear();
                admin.addRole("ADMIN");

                Usuario user = new Usuario();
                user.setNome("Usuário Teste");
                user.setUsername("user");
                user.setSenha(passwordEncoder.encode("123456"));

                usuarioRepository.saveAll(Arrays.asList(admin, user));
            }

            // Clientes
            if (clienteRepository.count() == 0) {
                Cliente c1 = new Cliente();
                c1.setNome("João Silva");
                c1.setCpf("12345678901");
                c1.setEmail("joao@email.com");
                c1.setTelefone("11999999999");
                c1.setEndereco("Rua A, 123");

                Cliente c2 = new Cliente();
                c2.setNome("Maria Santos");
                c2.setCpf("98765432109");
                c2.setEmail("maria@email.com");
                c2.setTelefone("11888888888");
                c2.setEndereco("Av. B, 456");

                clienteRepository.saveAll(Arrays.asList(c1, c2));
            }

            // Ordens de Serviço
            if (ordemServicoRepository.count() == 0 && clienteRepository.count() > 0) {
                Cliente cliente = clienteRepository.findAll().get(0);

                OrdemServico os1 = new OrdemServico();
                os1.setDescricaoProblema("Computador não liga");
                os1.setValor(new BigDecimal("150.00"));
                os1.setCliente(cliente);

                OrdemServico os2 = new OrdemServico();
                os2.setDescricaoProblema("Notebook com tela quebrada");
                os2.setValor(new BigDecimal("300.00"));
                os2.setCliente(cliente);
                os2.setStatus(OrdemServico.StatusOrdem.CONCLUIDA);

                ordemServicoRepository.saveAll(Arrays.asList(os1, os2));
            }
        };
    }
}