package br.com.posfiap.restmanager.presentation.controller;

import br.com.posfiap.restmanager.application.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.application.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.application.mapper.RestauranteMapper;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.usecase.restaurante.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestauranteController.class)
@AutoConfigureMockMvc(addFilters = false) // 🔓 Desativa filtros do Spring Security
@Import(RestauranteControllerTest.RestauranteUseCaseTestConfig.class)
class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CriarRestauranteUseCase criarRestauranteUseCase;

    @Autowired
    private RestauranteMapper restauranteMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private RestauranteCreateDto createDto;

    @BeforeEach
    void setUp() {
        createDto = RestauranteCreateDto.builder()
                .nome("Restaurante Teste")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h às 22h")
                .build();

        Restaurante restaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h às 22h")
                .build();

        RestauranteResponseDto responseDto = RestauranteResponseDto.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h às 22h")
                .build();

        Mockito.when(restauranteMapper.mapToRestaurante(any(RestauranteCreateDto.class))).thenReturn(restaurante);
        Mockito.when(criarRestauranteUseCase.executar(eq(1L), any())).thenReturn(restaurante);
        Mockito.when(restauranteMapper.mapToRestauranteResponseDto(any())).thenReturn(responseDto);
    }

    @Test
    void deveCriarRestauranteComSucesso() throws Exception {
        mockMvc.perform(post("/api/v1/restaurantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Restaurante Teste"))
                .andExpect(jsonPath("$.tipoCozinha").value("Italiana"));
    }

    @TestConfiguration
    static class RestauranteUseCaseTestConfig {

        @Bean
        public CriarRestauranteUseCase criarRestauranteUseCase() {
            return Mockito.mock(CriarRestauranteUseCase.class);
        }

        @Bean
        public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase() {
            return Mockito.mock(BuscarRestaurantePorIdUseCase.class);
        }

        @Bean
        public AtualizarRestauranteUseCase atualizarRestauranteUseCase() {
            return Mockito.mock(AtualizarRestauranteUseCase.class);
        }

        @Bean
        public DeletarRestaurantePorIdUseCase deletarRestaurantePorIdUseCase() {
            return Mockito.mock(DeletarRestaurantePorIdUseCase.class);
        }

        @Bean
        public ListarRestaurantesPorProprietarioUseCase listarRestaurantesPorProprietarioUseCase() {
            return Mockito.mock(ListarRestaurantesPorProprietarioUseCase.class);
        }

        @Bean
        public RestauranteMapper restauranteMapper() {
            return Mockito.mock(RestauranteMapper.class);
        }
    }
}
