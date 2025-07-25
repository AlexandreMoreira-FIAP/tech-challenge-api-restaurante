package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.dto.SenhaDto;
import br.com.posfiap.restmanager.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.RestauranteUpdateDto;
import br.com.posfiap.restmanager.mapper.RestauranteMapper;
import br.com.posfiap.restmanager.service.RestauranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteControllerTest {

    @Mock
    private RestauranteService restauranteService;

    @Mock
    private RestauranteMapper restauranteMapper;

    @InjectMocks
    private RestauranteController restauranteController;

    private Restaurante restaurante;
    private RestauranteCreateDto restauranteCreateDto;
    private RestauranteUpdateDto restauranteUpdateDto;
    private RestauranteResponseDto restauranteResponseDto;

    @BeforeEach
    void setUp() {
        restaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@test.com")
                .senha("senha123")
                .build();

        restauranteCreateDto = RestauranteCreateDto.builder()
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@test.com")
                .senha("senha123")
                .build();

        restauranteUpdateDto = RestauranteUpdateDto.builder()
                .nome("Restaurante Atualizado")
                .tipoDeCozinha("Brasileira")
                .login("restaurante.novo@test.com")
                .build();

        restauranteResponseDto = RestauranteResponseDto.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@test.com")
                .build();
    }

    @Test
    void deveIncluirRestauranteComSucesso() {
        when(restauranteMapper.mapToRestaurante(restauranteCreateDto)).thenReturn(restaurante);
        when(restauranteService.incluir(any(Restaurante.class))).thenReturn(restaurante);
        when(restauranteMapper.mapToRestauranteResponseDto(restaurante)).thenReturn(restauranteResponseDto);

        RestauranteResponseDto resultado = restauranteController.incluir(restauranteCreateDto);

        assertNotNull(resultado);
        assertEquals(restauranteResponseDto, resultado);
        verify(restauranteMapper).mapToRestaurante(restauranteCreateDto);
        verify(restauranteService).incluir(restaurante);
        verify(restauranteMapper).mapToRestauranteResponseDto(restaurante);
    }

    @Test
    void deveBuscarRestaurantePorIdComSucesso() {
        when(restauranteService.buscarPorId(1L)).thenReturn(restaurante);
        when(restauranteMapper.mapToRestauranteResponseDto(restaurante)).thenReturn(restauranteResponseDto);

        RestauranteResponseDto resultado = restauranteController.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(restauranteResponseDto, resultado);
        verify(restauranteService).buscarPorId(1L);
        verify(restauranteMapper).mapToRestauranteResponseDto(restaurante);
    }

    @Test
    void deveAtualizarRestauranteComSucesso() {
        when(restauranteMapper.mapToRestaurante(restauranteUpdateDto)).thenReturn(restaurante);
        when(restauranteService.atualizar(anyLong(), any(Restaurante.class))).thenReturn(restaurante);
        when(restauranteMapper.mapToRestauranteResponseDto(restaurante)).thenReturn(restauranteResponseDto);

        RestauranteResponseDto resultado = restauranteController.atualizar(1L, restauranteUpdateDto);

        assertNotNull(resultado);
        assertEquals(restauranteResponseDto, resultado);
        verify(restauranteMapper).mapToRestaurante(restauranteUpdateDto);
        verify(restauranteService).atualizar(1L, restaurante);
        verify(restauranteMapper).mapToRestauranteResponseDto(restaurante);
    }

    @Test
    void deveExcluirRestauranteComSucesso() {
        doNothing().when(restauranteService).excluir(1L);

        restauranteController.excluir(1L);

        verify(restauranteService).excluir(1L);
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        SenhaDto senhaDto = SenhaDto.builder()
                .senhaAtual("senhaAtual")
                .novaSenha("novaSenha")
                .build();

        doNothing().when(restauranteService).alterarSenha(1L, "senhaAtual", "novaSenha");

        restauranteController.alterarSenha(1L, senhaDto);

        verify(restauranteService).alterarSenha(1L, "senhaAtual", "novaSenha");
    }
}