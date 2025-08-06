package br.com.posfiap.restmanager.domain.usecase.restaurante;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarRestaurantesPorProprietarioUseCaseTest {

    private RestauranteRepository restauranteRepository;
    private ListarRestaurantesPorProprietarioUseCase useCase;

    @BeforeEach
    void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        useCase = new ListarRestaurantesPorProprietarioUseCase(restauranteRepository);
    }

    @Test
    void deveListarRestaurantesPorProprietarioComSucesso() {
        // Arrange
        Long idProprietario = 10L;

        Usuario proprietario = Usuario.builder()
                .id(idProprietario)
                .nome("João")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Restaurante restaurante1 = Restaurante.builder()
                .id(1L)
                .nome("Restaurante 1")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h às 22h")
                .proprietario(proprietario)
                .dataUltimaAlteracao(now())
                .build();

        Restaurante restaurante2 = Restaurante.builder()
                .id(2L)
                .nome("Restaurante 2")
                .tipoCozinha("Brasileira")
                .horarioFuncionamento("11h às 23h")
                .proprietario(proprietario)
                .dataUltimaAlteracao(now())
                .build();

        List<Restaurante> restaurantes = List.of(restaurante1, restaurante2);

        when(restauranteRepository.listarPorProprietario(idProprietario)).thenReturn(restaurantes);

        // Act
        List<Restaurante> resultado = useCase.executar(idProprietario);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Restaurante 1", resultado.get(0).getNome());
        assertEquals("Restaurante 2", resultado.get(1).getNome());
        assertEquals(proprietario, resultado.get(0).getProprietario());
        assertEquals(proprietario, resultado.get(1).getProprietario());
        verify(restauranteRepository).listarPorProprietario(idProprietario);
    }

    @Test
    void deveLancarExcecaoQuandoListaVazia() {
        // Arrange
        Long idProprietario = 10L;
        List<Restaurante> listaVazia = new ArrayList<>();

        when(restauranteRepository.listarPorProprietario(idProprietario)).thenReturn(listaVazia);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idProprietario));
        
        assertTrue(exception.getMessage().contains("Nenhum restaurante encontrado"));
        assertTrue(exception.getMessage().contains(idProprietario.toString()));
        verify(restauranteRepository).listarPorProprietario(idProprietario);
    }

    @Test
    void deveLancarExcecaoQuandoListaNula() {
        // Arrange
        Long idProprietario = 10L;

        when(restauranteRepository.listarPorProprietario(idProprietario)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idProprietario));
        
        assertTrue(exception.getMessage().contains("Nenhum restaurante encontrado"));
        assertTrue(exception.getMessage().contains(idProprietario.toString()));
        verify(restauranteRepository).listarPorProprietario(idProprietario);
    }
}