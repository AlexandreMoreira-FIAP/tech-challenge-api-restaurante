package br.com.posfiap.restmanager.infrastructure.adapters.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    void deveRetornarBCryptPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void deveCriptografarSenhaCorretamente() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        
        String senhaOriginal = "senha123";
        String senhaCriptografada = passwordEncoder.encode(senhaOriginal);
        
        assertNotNull(senhaCriptografada);
        assertNotEquals(senhaOriginal, senhaCriptografada);
        assertTrue(passwordEncoder.matches(senhaOriginal, senhaCriptografada));
    }

    @Test
    void deveInstanciarSecurityConfig() {
        SecurityConfig config = new SecurityConfig();
        assertNotNull(config);
    }
}