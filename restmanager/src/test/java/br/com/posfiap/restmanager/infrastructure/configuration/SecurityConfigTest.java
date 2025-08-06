package br.com.posfiap.restmanager.infrastructure.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    void deveConfigurarPasswordEncoder() {
        // Act
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void deveTestarCriptografiaDesenha() {
        // Arrange
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String senha = "minhasenha123";

        // Act
        String senhaCriptografada = passwordEncoder.encode(senha);

        // Assert
        assertNotNull(senhaCriptografada);
        assertTrue(passwordEncoder.matches(senha, senhaCriptografada));
        assertFalse(passwordEncoder.matches("senhaerrada", senhaCriptografada));
    }
}