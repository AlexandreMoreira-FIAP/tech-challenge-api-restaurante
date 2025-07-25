package br.com.posfiap.restmanager.application.services;

import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioServiceTest {

    private TipoUsuarioService tipoUsuarioService;

    @BeforeEach
    void setUp() {
        tipoUsuarioService = new TipoUsuarioService();
    }

    @Test
    void deveListarTodosOsTiposDeUsuario() {
        List<TipoUsuarioDto> tipos = tipoUsuarioService.listarTodos();

        assertNotNull(tipos);
        assertEquals(2, tipos.size());
        
        assertTrue(tipos.stream().anyMatch(t -> "CLIENTE".equals(t.codigo())));
        assertTrue(tipos.stream().anyMatch(t -> "PROPRIETARIO".equals(t.codigo())));
    }

    @Test
    void deveRetornarDescricaoCorretaParaCliente() {
        List<TipoUsuarioDto> tipos = tipoUsuarioService.listarTodos();
        
        TipoUsuarioDto cliente = tipos.stream()
            .filter(t -> "CLIENTE".equals(t.codigo()))
            .findFirst()
            .orElseThrow();
            
        assertEquals("Cliente do restaurante", cliente.descricao());
    }

    @Test
    void deveRetornarDescricaoCorretaParaProprietario() {
        List<TipoUsuarioDto> tipos = tipoUsuarioService.listarTodos();
        
        TipoUsuarioDto proprietario = tipos.stream()
            .filter(t -> "PROPRIETARIO".equals(t.codigo()))
            .findFirst()
            .orElseThrow();
            
        assertEquals("Proprietário do restaurante", proprietario.descricao());
    }
}