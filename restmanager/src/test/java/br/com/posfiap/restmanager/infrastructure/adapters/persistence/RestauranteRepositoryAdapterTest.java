package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteRepositoryAdapterTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private RestauranteRepositoryAdapter restauranteRepositoryAdapter;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@test.com")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();
    }

    @Test
    void deveSalvarRestaurante() {
        when(restauranteRepository.salvar(restaurante)).thenReturn(restaurante);

        Restaurante resultado = restauranteRepositoryAdapter.salvar(restaurante);

        assertNotNull(resultado);
        assertEquals(restaurante, resultado);
        verify(restauranteRepository).salvar(restaurante);
    }

    @Test
    void deveBuscarRestaurantePorId() {
        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));

        Optional<Restaurante> resultado = restauranteRepositoryAdapter.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(restaurante, resultado.get());
        verify(restauranteRepository).buscarPorId(1L);
    }

    @Test
    void deveBuscarRestaurantePorLogin() {
        when(restauranteRepository.buscarPorLogin("restaurante@test.com")).thenReturn(Optional.of(restaurante));

        Optional<Restaurante> resultado = restauranteRepositoryAdapter.buscarPorLogin("restaurante@test.com");

        assertTrue(resultado.isPresent());
        assertEquals(restaurante, resultado.get());
        verify(restauranteRepository).buscarPorLogin("restaurante@test.com");
    }

    @Test
    void deveExcluirRestaurante() {
        doNothing().when(restauranteRepository).excluir(1L);

        restauranteRepositoryAdapter.excluir(1L);

        verify(restauranteRepository).excluir(1L);
    }
}