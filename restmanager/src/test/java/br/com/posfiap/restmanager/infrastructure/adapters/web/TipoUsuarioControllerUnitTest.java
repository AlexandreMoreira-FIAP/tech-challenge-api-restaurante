package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import br.com.posfiap.restmanager.application.services.TipoUsuarioService;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioControllerUnitTest {

    @Mock
    private TipoUsuarioService tipoUsuarioService;

    private TipoUsuarioController controller;

    @BeforeEach
    void setUp() {
        controller = new TipoUsuarioController(tipoUsuarioService);
    }

    @Test
    void deveListarTodos() {
        List<TipoUsuarioDto> tipos = Arrays.asList(
            new TipoUsuarioDto(1L, "CLIENTE", "Cliente"),
            new TipoUsuarioDto(2L, "ADMIN", "Admin")
        );
        when(tipoUsuarioService.listarTodos()).thenReturn(tipos);

        List<TipoUsuarioDto> result = controller.listarTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CLIENTE", result.get(0).codigo());
        verify(tipoUsuarioService).listarTodos();
    }

    @Test
    void deveBuscarPorId() {
        TipoUsuarioDto tipo = new TipoUsuarioDto(1L, "CLIENTE", "Cliente");
        when(tipoUsuarioService.buscarPorId(1L)).thenReturn(tipo);

        TipoUsuarioDto result = controller.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("CLIENTE", result.codigo());
        verify(tipoUsuarioService).buscarPorId(1L);
    }

    @Test
    void deveCriar() {
        TipoUsuarioDto novoTipo = new TipoUsuarioDto(null, "GERENTE", "Gerente");
        TipoUsuarioDto tipoSalvo = new TipoUsuarioDto(1L, "GERENTE", "Gerente");
        when(tipoUsuarioService.criar(any())).thenReturn(tipoSalvo);

        TipoUsuarioDto result = controller.criar(novoTipo);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("GERENTE", result.codigo());
        verify(tipoUsuarioService).criar(novoTipo);
    }

    @Test
    void deveAtualizar() {
        TipoUsuarioDto tipoAtualizado = new TipoUsuarioDto(1L, "CLIENTE", "Cliente atualizado");
        when(tipoUsuarioService.atualizar(eq(1L), any())).thenReturn(tipoAtualizado);

        TipoUsuarioDto result = controller.atualizar(1L, tipoAtualizado);

        assertNotNull(result);
        assertEquals("Cliente atualizado", result.descricao());
        verify(tipoUsuarioService).atualizar(1L, tipoAtualizado);
    }

    @Test
    void deveDeletar() {
        assertDoesNotThrow(() -> controller.deletar(1L));
        verify(tipoUsuarioService).deletar(1L);
    }

    @Test
    void deveHandleNotFoundExceptionEmBuscarPorId() {
        when(tipoUsuarioService.buscarPorId(999L))
                .thenThrow(new NotFoundException("Não encontrado"));

        assertThrows(NotFoundException.class, () -> controller.buscarPorId(999L));
    }

    @Test
    void deveHandleBusinessExceptionEmCriar() {
        TipoUsuarioDto tipo = new TipoUsuarioDto(null, "CLIENTE", "Cliente");
        when(tipoUsuarioService.criar(any()))
                .thenThrow(new BusinessException("Código já existe"));

        assertThrows(BusinessException.class, () -> controller.criar(tipo));
    }
}