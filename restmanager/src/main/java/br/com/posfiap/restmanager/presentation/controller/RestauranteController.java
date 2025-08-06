package br.com.posfiap.restmanager.presentation.controller;

import br.com.posfiap.restmanager.application.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.application.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.application.dto.RestauranteUpdateDto;
import br.com.posfiap.restmanager.application.mapper.RestauranteMapper;
import br.com.posfiap.restmanager.domain.usecase.restaurante.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.posfiap.restmanager.infrastructure.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.infrastructure.util.Logger.logResponseController;
import static java.text.MessageFormat.format;

@RestController
@RequiredArgsConstructor
class RestauranteController implements RestauranteApi {

    private static final String INCLUIR_RESTAURANTE = "incluir restaurante";
    private static final String CONSULTAR_RESTAURANTE = "consultar restaurante com ID {0}";
    private static final String ATUALIZAR_RESTAURANTE = "atualizar restaurante com ID {0}";
    private static final String EXCLUIR_RESTAURANTE = "excluir restaurante com ID {0}";
    private static final String LISTAR_RESTAURANTES_POR_PROPRIETARIO = "listar restaurantes do proprietário com ID {0}";

    private final CriarRestauranteUseCase criarRestauranteUseCase;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final AtualizarRestauranteUseCase atualizarRestauranteUseCase;
    private final DeletarRestaurantePorIdUseCase deletarRestaurantePorIdUseCase;
    private final ListarRestaurantesPorProprietarioUseCase listarRestaurantesPorProprietarioUseCase;
    private final RestauranteMapper restauranteMapper;

    @Override
    public RestauranteResponseDto incluir(Long idProprietario, RestauranteCreateDto restauranteCreateDto) {
        logRequestController(INCLUIR_RESTAURANTE, restauranteCreateDto);

        var restaurante = restauranteMapper.mapToRestaurante(restauranteCreateDto);
        var criado = criarRestauranteUseCase.executar(idProprietario, restaurante);
        var responseDto = restauranteMapper.mapToRestauranteResponseDto(criado);

        logResponseController(INCLUIR_RESTAURANTE, responseDto);
        return responseDto;
    }

    @Override
    public RestauranteResponseDto buscarPorId(Long id) {
        logRequestController(format(CONSULTAR_RESTAURANTE, id));

        var restaurante = buscarRestaurantePorIdUseCase.executar(id);
        var responseDto = restauranteMapper.mapToRestauranteResponseDto(restaurante);

        logResponseController(format(CONSULTAR_RESTAURANTE, id), responseDto);
        return responseDto;
    }

    @Override
    public RestauranteResponseDto atualizar(Long idRestaurante, Long idProprietario, RestauranteUpdateDto dto) {
        logRequestController(format(ATUALIZAR_RESTAURANTE, idRestaurante), dto);

        var restaurante = restauranteMapper.mapToRestaurante(dto);
        var atualizado = atualizarRestauranteUseCase.executar(idRestaurante, idProprietario, restaurante);
        var responseDto = restauranteMapper.mapToRestauranteResponseDto(atualizado);

        logResponseController(format(ATUALIZAR_RESTAURANTE, idRestaurante), responseDto);
        return responseDto;
    }

    @Override
    public void excluir(Long id) {
        logRequestController(format(EXCLUIR_RESTAURANTE, id));

        var restaurante = buscarRestaurantePorIdUseCase.executar(id);
        deletarRestaurantePorIdUseCase.executar(id);

        logResponseController(format(EXCLUIR_RESTAURANTE, id));
    }

    @Override
    public List<RestauranteResponseDto> listarPorProprietario(Long idProprietario) {
        logRequestController(format(LISTAR_RESTAURANTES_POR_PROPRIETARIO, idProprietario));

        var lista = listarRestaurantesPorProprietarioUseCase.executar(idProprietario)
                .stream()
                .map(restauranteMapper::mapToRestauranteResponseDto)
                .toList();

        logResponseController(format(LISTAR_RESTAURANTES_POR_PROPRIETARIO, idProprietario), lista);
        return lista;
    }
}
