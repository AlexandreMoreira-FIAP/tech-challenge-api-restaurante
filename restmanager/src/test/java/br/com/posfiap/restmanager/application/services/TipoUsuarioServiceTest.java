package br.com.posfiap.restmanager.application.services;

import br.com.posfiap.restmanager.domain.entities.TipoUsuarioEntity;
import br.com.posfiap.restmanager.domain.ports.TipoUsuarioRepositoryPort;
import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioServiceTest {

    @Mock
    private TipoUsuarioRepositoryPort tipoUsuarioRepository;

    private TipoUsuarioService tipoUsuarioService;

    @BeforeEach
    void setUp() {
        tipoUsuarioService = new TipoUsuarioService(tipoUsuarioRepository);
    }

    @Test
    void deveListarTodosOsTiposDeUsuario() {
        when(tipoUsuarioRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<TipoUsuarioDto> tipos = tipoUsuarioService.listarTodos();

        assertNotNull(tipos);
        assertEquals(2, tipos.size());
        
        assertTrue(tipos.stream().anyMatch(t -> "CLIENTE".equals(t.codigo())));
        assertTrue(tipos.stream().anyMatch(t -> "PROPRIETARIO".equals(t.codigo())));
    }

    @Test
    void deveRetornarDescricaoCorretaParaCliente() {
        when(tipoUsuarioRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<TipoUsuarioDto> tipos = tipoUsuarioService.listarTodos();
        
        TipoUsuarioDto cliente = tipos.stream()
            .filter(t -> "CLIENTE".equals(t.codigo()))
            .findFirst()
            .orElseThrow();
            
        assertEquals("Cliente do restaurante", cliente.descricao());
    }

    @Test
    void deveRetornarDescricaoCorretaParaProprietario() {
        when(tipoUsuarioRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<TipoUsuarioDto> tipos = tipoUsuarioService.listarTodos();
        
        TipoUsuarioDto proprietario = tipos.stream()
            .filter(t -> "PROPRIETARIO".equals(t.codigo()))
            .findFirst()
            .orElseThrow();
            
        assertEquals("Proprietário do restaurante", proprietario.descricao());
    }

    @Test
    void deveListarTiposDoRepositorioQuandoExistirem() {
        List<TipoUsuarioEntity> tiposExistentes = Arrays.asList(
            TipoUsuarioEntity.builder().id(1L).codigo("CLIENTE").descricao("Cliente").build(),
            TipoUsuarioEntity.builder().id(2L).codigo("ADMIN").descricao("Administrador").build()
        );
        when(tipoUsuarioRepository.findAll()).thenReturn(tiposExistentes);
        
        List<TipoUsuarioDto> tipos = tipoUsuarioService.listarTodos();
        
        assertEquals(2, tipos.size());
        assertEquals("CLIENTE", tipos.get(0).codigo());
        assertEquals("ADMIN", tipos.get(1).codigo());
    }

    @Test
    void deveBuscarTipoUsuarioPorId() {
        TipoUsuarioEntity tipo = TipoUsuarioEntity.builder()
            .id(1L)
            .codigo("CLIENTE")
            .descricao("Cliente do restaurante")
            .build();
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.of(tipo));
        
        TipoUsuarioDto result = tipoUsuarioService.buscarPorId(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("CLIENTE", result.codigo());
        assertEquals("Cliente do restaurante", result.descricao());
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.empty());
        
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> tipoUsuarioService.buscarPorId(1L)
        );
        
        assertEquals("Tipo de usuário não encontrado com ID: 1", exception.getMessage());
    }

    @Test
    void deveCriarNovoTipoUsuario() {
        TipoUsuarioDto dto = new TipoUsuarioDto(null, "GERENTE", "Gerente do restaurante");
        TipoUsuarioEntity savedEntity = TipoUsuarioEntity.builder()
            .id(1L)
            .codigo("GERENTE")
            .descricao("Gerente do restaurante")
            .build();
        
        when(tipoUsuarioRepository.existsByCodigo("GERENTE")).thenReturn(false);
        when(tipoUsuarioRepository.save(any(TipoUsuarioEntity.class))).thenReturn(savedEntity);
        
        TipoUsuarioDto result = tipoUsuarioService.criar(dto);
        
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("GERENTE", result.codigo());
        assertEquals("Gerente do restaurante", result.descricao());
        verify(tipoUsuarioRepository).save(any(TipoUsuarioEntity.class));
    }

    @Test
    void deveLancarExcecaoAoCriarTipoUsuarioComCodigoExistente() {
        TipoUsuarioDto dto = new TipoUsuarioDto(null, "CLIENTE", "Cliente");
        when(tipoUsuarioRepository.existsByCodigo("CLIENTE")).thenReturn(true);
        
        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> tipoUsuarioService.criar(dto)
        );
        
        assertEquals("Já existe um tipo de usuário com o código: CLIENTE", exception.getMessage());
        verify(tipoUsuarioRepository, never()).save(any());
    }

    @Test
    void deveAtualizarTipoUsuarioExistente() {
        TipoUsuarioEntity existing = TipoUsuarioEntity.builder()
            .id(1L)
            .codigo("CLIENTE")
            .descricao("Cliente antigo")
            .build();
        TipoUsuarioDto dto = new TipoUsuarioDto(1L, "CLIENTE", "Cliente atualizado");
        TipoUsuarioEntity updated = TipoUsuarioEntity.builder()
            .id(1L)
            .codigo("CLIENTE")
            .descricao("Cliente atualizado")
            .build();
        
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(tipoUsuarioRepository.save(any(TipoUsuarioEntity.class))).thenReturn(updated);
        
        TipoUsuarioDto result = tipoUsuarioService.atualizar(1L, dto);
        
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("CLIENTE", result.codigo());
        assertEquals("Cliente atualizado", result.descricao());
    }

    @Test
    void deveLancarExcecaoAoAtualizarTipoUsuarioInexistente() {
        TipoUsuarioDto dto = new TipoUsuarioDto(1L, "CLIENTE", "Cliente");
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.empty());
        
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> tipoUsuarioService.atualizar(1L, dto)
        );
        
        assertEquals("Tipo de usuário não encontrado com ID: 1", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoAtualizarComCodigoJaExistente() {
        TipoUsuarioEntity existing = TipoUsuarioEntity.builder()
            .id(1L)
            .codigo("CLIENTE")
            .descricao("Cliente")
            .build();
        TipoUsuarioDto dto = new TipoUsuarioDto(1L, "ADMIN", "Administrador");
        
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(tipoUsuarioRepository.existsByCodigo("ADMIN")).thenReturn(true);
        
        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> tipoUsuarioService.atualizar(1L, dto)
        );
        
        assertEquals("Já existe um tipo de usuário com o código: ADMIN", exception.getMessage());
    }

    @Test
    void deveDeletarTipoUsuarioExistente() {
        TipoUsuarioEntity existing = TipoUsuarioEntity.builder()
            .id(1L)
            .codigo("CLIENTE")
            .descricao("Cliente")
            .build();
        
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.of(existing));
        
        assertDoesNotThrow(() -> tipoUsuarioService.deletar(1L));
        
        verify(tipoUsuarioRepository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarTipoUsuarioInexistente() {
        when(tipoUsuarioRepository.findById(1L)).thenReturn(Optional.empty());
        
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> tipoUsuarioService.deletar(1L)
        );
        
        assertEquals("Tipo de usuário não encontrado com ID: 1", exception.getMessage());
        verify(tipoUsuarioRepository, never()).deleteById(anyLong());
    }
}