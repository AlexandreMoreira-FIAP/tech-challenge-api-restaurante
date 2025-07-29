package br.com.posfiap.restmanager.controller;

import br.com.posfiap.restmanager.dto.SenhaDto;
import br.com.posfiap.restmanager.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.RestauranteUpdateDto;
import br.com.posfiap.restmanager.mapper.RestauranteMapper;
import br.com.posfiap.restmanager.service.RestauranteService;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import static br.com.posfiap.restmanager.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.util.Logger.logResponseController;
import static java.text.MessageFormat.format;

@RestController
@RequiredArgsConstructor
class RestauranteController implements RestauranteApi {

    private static final String INCLUIR_RESTAURANTE = "incluir restaurante";
    private static final String CONSULTAR_RESTAURANTE = "consultar usuário com ID {0}";
    private static final String ATUALIZAR_RESTAURANTE = "atualizar usuário com ID {0}";
    private static final String EXCLUIR_RESTAURANTE = "excluir usuário com ID {0}";
    private static final String ALTERAR_SENHA_USUARIO = "alterar senha do usuário com ID {0}";

    private final RestauranteService restauranteService;
    private final RestauranteMapper restauranteMapper;

    @Override
    public RestauranteResponseDto incluir(RestauranteCreateDto restauranteCreateDto) {

        logRequestController(INCLUIR_RESTAURANTE, restauranteCreateDto);

        var restaurante = restauranteService.incluir(
                restauranteMapper.mapToRestaurante(restauranteCreateDto), 
                restauranteCreateDto.getUsuarioIds());
        var restauranteResponseDto = restauranteMapper.mapToRestauranteResponseDto(restaurante);

        logResponseController(INCLUIR_RESTAURANTE, restauranteResponseDto);
        return restauranteResponseDto;
    }

    @Override
    public RestauranteResponseDto buscarPorId(Long id) {

        logRequestController(format(CONSULTAR_RESTAURANTE, id));

        var restaurante = restauranteService.buscarPorId(id);
        var restauranteResponseDto = restauranteMapper.mapToRestauranteResponseDto(restaurante);

        logResponseController(format(CONSULTAR_RESTAURANTE, id), restauranteResponseDto);
        return restauranteResponseDto;
    }

    @Override
    public RestauranteResponseDto atualizar(Long id, RestauranteUpdateDto restauranteUpdateDto) {

        logRequestController(format(ATUALIZAR_RESTAURANTE, id), restauranteUpdateDto);

        var restaurante = restauranteService.atualizar(id, restauranteMapper.mapToRestaurante(restauranteUpdateDto));
        var restauranteResponseDto = restauranteMapper.mapToRestauranteResponseDto(restaurante);

        logResponseController(format(ATUALIZAR_RESTAURANTE, id), restauranteResponseDto);
        return restauranteResponseDto;
    }

    @Override
    public void excluir(Long id) {

        logRequestController(format(EXCLUIR_RESTAURANTE, id));

        restauranteService.excluir(id);

        logResponseController(format(EXCLUIR_RESTAURANTE, id));
    }

    @Override
    public void alterarSenha(Long id, SenhaDto senhaDto) {

        logRequestController(format(ALTERAR_SENHA_USUARIO, id));

        restauranteService.alterarSenha(id, senhaDto.getSenhaAtual(), senhaDto.getNovaSenha());

        logResponseController(format(ALTERAR_SENHA_USUARIO, id));
    }
}