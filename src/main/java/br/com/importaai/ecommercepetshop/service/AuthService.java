package br.com.importaai.ecommercepetshop.service;

import br.com.importaai.ecommercepetshop.dto.request.LoginRequest;
import br.com.importaai.ecommercepetshop.dto.request.RegisterRequest;
import br.com.importaai.ecommercepetshop.dto.response.TokenResponse;
import br.com.importaai.ecommercepetshop.exception.BusinessException;
import br.com.importaai.ecommercepetshop.model.Role;
import br.com.importaai.ecommercepetshop.model.User;
import br.com.importaai.ecommercepetshop.repository.UserRepository;
import br.com.importaai.ecommercepetshop.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("E-mail ja cadastrado");
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return generateToken(user);
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Credenciais invalidas"));
        return generateToken(user);
    }

    private TokenResponse generateToken(User user) {
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return TokenResponse.bearer(token, jwtService.getExpirationMs());
    }
}
