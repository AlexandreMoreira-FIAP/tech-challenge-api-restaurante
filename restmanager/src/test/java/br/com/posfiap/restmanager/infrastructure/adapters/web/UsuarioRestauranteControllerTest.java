package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioRestauranteDto;
import br.com.posfiap.restmanager.service.UsuarioRestauranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRestauranteControllerTest {

    @Mock
    private UsuarioRestauranteService usuarioRestauranteService;

    @InjectMocks
    private UsuarioRestauranteController usuarioRestauranteController;

    private UsuarioRestauranteDto usuarioRestauranteDto;
    private UsuarioResponseDto usuarioResponseDto;
    private RestauranteResponseDto restauranteResponseDto;

    @BeforeEach
    void setUp() {
        usuarioRestauranteDto = UsuarioRestauranteDto.builder()
                .usuarioId(1L)
                .restauranteId(1L)
                .build();

        usuarioResponseDto = UsuarioResponseDto.builder()
                .id(1L)
                .nome("João Silva")
                .login("joao@test.com")
                .build();

        restauranteResponseDto = RestauranteResponseDto.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@test.com")
                .build();
    }

    @Test
    void deveAssociarUsuarioRestauranteComSucesso() {
        doNothing().when(usuarioRestauranteService).associarUsuarioRestaurante(1L, 1L);

        ResponseEntity<Void> resultado = usuarioRestauranteController.associarUsuarioRestaurante(usuarioRestauranteDto);

        assertNotNull(resultado);
        assertEquals(200, resultado.getStatusCode().value());
        verify(usuarioRestauranteService).associarUsuarioRestaurante(1L, 1L);
    }

    @Test
    void deveDesassociarUsuarioRestauranteComSucesso() {
        doNothing().when(usuarioRestauranteService).desassociarUsuarioRestaurante(1L, 1L);

        ResponseEntity<Void> resultado = usuarioRestauranteController.desassociarUsuarioRestaurante(usuarioRestauranteDto);

        assertNotNull(resultado);
        assertEquals(200, resultado.getStatusCode().value());
        verify(usuarioRestauranteService).desassociarUsuarioRestaurante(1L, 1L);
    }

    @Test
    void deveListarRestaurantesDoUsuarioComSucesso() {
        List<RestauranteResponseDto> restaurantes = Arrays.asList(restauranteResponseDto);
        when(usuarioRestauranteService.listarRestaurantesDoUsuario(1L)).thenReturn(restaurantes);

        ResponseEntity<List<RestauranteResponseDto>> resultado = usuarioRestauranteController.listarRestaurantesDoUsuario(1L);

        assertNotNull(resultado);
        assertEquals(200, resultado.getStatusCode().value());
        assertNotNull(resultado.getBody());
        assertEquals(1, resultado.getBody().size());
        assertEquals(restauranteResponseDto, resultado.getBody().get(0));
        verify(usuarioRestauranteService).listarRestaurantesDoUsuario(1L);
    }

    @Test
    void deveListarUsuariosDoRestauranteComSucesso() {
        List<UsuarioResponseDto> usuarios = Arrays.asList(usuarioResponseDto);
        when(usuarioRestauranteService.listarUsuariosDoRestaurante(1L)).thenReturn(usuarios);

        ResponseEntity<List<UsuarioResponseDto>> resultado = usuarioRestauranteController.listarUsuariosDoRestaurante(1L);

        assertNotNull(resultado);
        assertEquals(200, resultado.getStatusCode().value());
        assertNotNull(resultado.getBody());
        assertEquals(1, resultado.getBody().size());
        assertEquals(usuarioResponseDto, resultado.getBody().get(0));
        verify(usuarioRestauranteService).listarUsuariosDoRestaurante(1L);
    }
}