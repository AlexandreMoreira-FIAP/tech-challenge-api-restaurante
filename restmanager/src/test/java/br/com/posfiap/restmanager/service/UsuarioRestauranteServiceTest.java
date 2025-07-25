package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.error.NotFoundException;
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import br.com.posfiap.restmanager.mapper.RestauranteMapper;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.infrastructure.adapters.persistence.RestauranteJpaRepository;
import br.com.posfiap.restmanager.infrastructure.adapters.persistence.UsuarioJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRestauranteServiceTest {

    @Mock
    private UsuarioJpaRepository usuarioJpaRepository;

    @Mock
    private RestauranteJpaRepository restauranteJpaRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private RestauranteMapper restauranteMapper;

    @InjectMocks
    private UsuarioRestauranteService usuarioRestauranteService;

    private UsuarioEntity usuarioEntity;
    private RestauranteEntity restauranteEntity;
    private UsuarioResponseDto usuarioResponseDto;
    private RestauranteResponseDto restauranteResponseDto;

    @BeforeEach
    void setUp() {
        usuarioEntity = UsuarioEntity.builder()
                .id(1L)
                .login("usuario@test.com")
                .restaurantes(new ArrayList<>())
                .build();

        restauranteEntity = RestauranteEntity.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .usuarios(new ArrayList<>())
                .build();

        usuarioResponseDto = UsuarioResponseDto.builder()
                .id(1L)
                .login("usuario@test.com")
                .build();

        restauranteResponseDto = RestauranteResponseDto.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .build();
    }

    @Test
    void deveAssociarUsuarioRestauranteComSucesso() {
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.of(restauranteEntity));
        when(restauranteJpaRepository.save(any(RestauranteEntity.class))).thenReturn(restauranteEntity);

        usuarioRestauranteService.associarUsuarioRestaurante(1L, 1L);

        assertTrue(restauranteEntity.getUsuarios().contains(usuarioEntity));
        verify(usuarioJpaRepository).findById(1L);
        verify(restauranteJpaRepository).findById(1L);
        verify(restauranteJpaRepository).save(restauranteEntity);
    }

    @Test
    void naoDeveAssociarUsuarioJaAssociado() {
        restauranteEntity.getUsuarios().add(usuarioEntity);
        
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.of(restauranteEntity));

        usuarioRestauranteService.associarUsuarioRestaurante(1L, 1L);

        assertEquals(1, restauranteEntity.getUsuarios().size());
        verify(usuarioJpaRepository).findById(1L);
        verify(restauranteJpaRepository).findById(1L);
        verify(restauranteJpaRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoAssociar() {
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> usuarioRestauranteService.associarUsuarioRestaurante(1L, 1L));

        verify(usuarioJpaRepository).findById(1L);
        verify(restauranteJpaRepository, never()).findById(anyLong());
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontradoAoAssociar() {
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> usuarioRestauranteService.associarUsuarioRestaurante(1L, 1L));

        verify(usuarioJpaRepository).findById(1L);
        verify(restauranteJpaRepository).findById(1L);
    }

    @Test
    void deveDesassociarUsuarioRestauranteComSucesso() {
        restauranteEntity.getUsuarios().add(usuarioEntity);
        
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.of(restauranteEntity));
        when(restauranteJpaRepository.save(any(RestauranteEntity.class))).thenReturn(restauranteEntity);

        usuarioRestauranteService.desassociarUsuarioRestaurante(1L, 1L);

        assertFalse(restauranteEntity.getUsuarios().contains(usuarioEntity));
        verify(usuarioJpaRepository).findById(1L);
        verify(restauranteJpaRepository).findById(1L);
        verify(restauranteJpaRepository).save(restauranteEntity);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoDesassociar() {
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> usuarioRestauranteService.desassociarUsuarioRestaurante(1L, 1L));

        verify(usuarioJpaRepository).findById(1L);
        verify(restauranteJpaRepository, never()).findById(anyLong());
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontradoAoDesassociar() {
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> usuarioRestauranteService.desassociarUsuarioRestaurante(1L, 1L));

        verify(usuarioJpaRepository).findById(1L);
        verify(restauranteJpaRepository).findById(1L);
    }

    @Test
    void deveListarRestaurantesDoUsuario() {
        usuarioEntity.setRestaurantes(Arrays.asList(restauranteEntity));
        
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(restauranteMapper.mapToRestaurante(any(RestauranteEntity.class))).thenReturn(null);
        when(restauranteMapper.mapToRestauranteResponseDto(any())).thenReturn(restauranteResponseDto);

        List<RestauranteResponseDto> resultado = usuarioRestauranteService.listarRestaurantesDoUsuario(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(restauranteResponseDto, resultado.get(0));
        verify(usuarioJpaRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoListarRestaurantes() {
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> usuarioRestauranteService.listarRestaurantesDoUsuario(1L));

        verify(usuarioJpaRepository).findById(1L);
    }

    @Test
    void deveListarUsuariosDoRestaurante() {
        restauranteEntity.setUsuarios(Arrays.asList(usuarioEntity));
        
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.of(restauranteEntity));
        when(usuarioMapper.mapToUsuario(any(UsuarioEntity.class))).thenReturn(null);
        when(usuarioMapper.mapToUsuarioResponseDto(any())).thenReturn(usuarioResponseDto);

        List<UsuarioResponseDto> resultado = usuarioRestauranteService.listarUsuariosDoRestaurante(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(usuarioResponseDto, resultado.get(0));
        verify(restauranteJpaRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontradoAoListarUsuarios() {
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> usuarioRestauranteService.listarUsuariosDoRestaurante(1L));

        verify(restauranteJpaRepository).findById(1L);
    }
}