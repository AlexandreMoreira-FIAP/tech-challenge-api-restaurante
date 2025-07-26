package br.com.posfiap.restmanager.integration;

import br.com.posfiap.restmanager.application.services.TipoUsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class SimpleIntegrationTest {

    @Autowired
    private br.com.posfiap.restmanager.application.services.TipoUsuarioService tipoUsuarioService;

    @Test
    void contextLoads() {
        assertNotNull(tipoUsuarioService);
    }

    @Test
    void deveListarTiposDeUsuario() {
        var tipos = tipoUsuarioService.listarTodos();
        
        assertNotNull(tipos);
        assertEquals(2, tipos.size());
        
        var cliente = tipos.stream()
                .filter(tipo -> "CLIENTE".equals(tipo.codigo()))
                .findFirst();
        assertTrue(cliente.isPresent());
        assertEquals("Cliente do restaurante", cliente.get().descricao());
        
        var proprietario = tipos.stream()
                .filter(tipo -> "PROPRIETARIO".equals(tipo.codigo()))
                .findFirst();
        assertTrue(proprietario.isPresent());
        assertEquals("Proprietário do restaurante", proprietario.get().descricao());
    }
}