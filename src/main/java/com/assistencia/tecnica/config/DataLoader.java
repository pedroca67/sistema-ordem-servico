package com.assistencia.tecnica.config;

import com.assistencia.tecnica.model.Usuario;
import com.assistencia.tecnica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Adicione isso

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setUsername("admin");
            // CRIPTOGRAFA A SENHA AQUI
            admin.setSenha(passwordEncoder.encode("admin"));
            admin.addRole("ADMIN");
            usuarioRepository.save(admin);
            System.out.println("Usuário admin criado com sucesso!");
        }
    }
}