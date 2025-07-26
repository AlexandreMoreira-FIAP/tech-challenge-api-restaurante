package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.TipoUsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioRepositoryAdapterTest {

    @Mock
    private TipoUsuarioJpaRepository jpaRepository;

    private TipoUsuarioRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new TipoUsuarioRepositoryAdapter(jpaRepository);
    }

    @Test
    void deveBuscarTodos() {
        List<br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity> entities = Arrays.asList(
            br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity.builder()
                .id(1L).codigo("CLIENTE").descricao("Cliente").build(),
            br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity.builder()
                .id(2L).codigo("ADMIN").descricao("Admin").build()
        );
        when(jpaRepository.findAll()).thenReturn(entities);
        
        List<TipoUsuarioEntity> result = adapter.findAll();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CLIENTE", result.get(0).getCodigo());
        assertEquals("ADMIN", result.get(1).getCodigo());
    }

    @Test
    void deveBuscarPorId() {
        br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity entity = 
            br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity.builder()
                .id(1L).codigo("CLIENTE").descricao("Cliente").build();
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        
        Optional<TipoUsuarioEntity> result = adapter.findById(1L);
        
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("CLIENTE", result.get().getCodigo());
    }

    @Test
    void deveBuscarPorCodigo() {
        br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity entity = 
            br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity.builder()
                .id(1L).codigo("CLIENTE").descricao("Cliente").build();
        when(jpaRepository.findByCodigo("CLIENTE")).thenReturn(Optional.of(entity));
        
        Optional<TipoUsuarioEntity> result = adapter.findByCodigo("CLIENTE");
        
        assertTrue(result.isPresent());
        assertEquals("CLIENTE", result.get().getCodigo());
    }

    @Test
    void deveSalvar() {
        TipoUsuarioEntity domain = TipoUsuarioEntity.builder()
            .codigo("GERENTE").descricao("Gerente").build();
        br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity savedEntity = 
            br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity.builder()
                .id(1L).codigo("GERENTE").descricao("Gerente").build();
        
        when(jpaRepository.save(any(br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity.class)))
            .thenReturn(savedEntity);
        
        TipoUsuarioEntity result = adapter.save(domain);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("GERENTE", result.getCodigo());
        verify(jpaRepository).save(any());
    }

    @Test
    void deveVerificarSeExistePorCodigo() {
        when(jpaRepository.existsByCodigo("CLIENTE")).thenReturn(true);
        
        boolean exists = adapter.existsByCodigo("CLIENTE");
        
        assertTrue(exists);
    }

    @Test
    void deveDeletarPorId() {
        adapter.deleteById(1L);
        
        verify(jpaRepository).deleteById(1L);
    }
}