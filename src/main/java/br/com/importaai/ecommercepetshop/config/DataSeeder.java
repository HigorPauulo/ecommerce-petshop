package br.com.importaai.ecommercepetshop.config;

import br.com.importaai.ecommercepetshop.model.Role;
import br.com.importaai.ecommercepetshop.model.User;
import br.com.importaai.ecommercepetshop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Cria um usuario admin padrao na primeira execucao (senha gerada com BCrypt
 * em runtime, evitando hash hardcoded no script SQL).
 * Credenciais iniciais: admin@petshop.com / admin123 (troque apos o 1o acesso).
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.existsByEmail("admin@petshop.com")) {
            return;
        }
        User admin = new User();
        admin.setName("Administrador");
        admin.setEmail("admin@petshop.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ROLE_ADMIN);
        userRepository.save(admin);
    }
}
