package br.com.posfiap.restmanager.presentation.controller;

import br.com.posfiap.restmanager.application.dto.ItemCardapioCreateDto;
import br.com.posfiap.restmanager.application.dto.ItemCardapioResponseDto;
import br.com.posfiap.restmanager.application.dto.ItemCardapioUpdateDto;
import br.com.posfiap.restmanager.application.mapper.ItemCardapioMapper;
import br.com.posfiap.restmanager.domain.usecase.itemcardapio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.posfiap.restmanager.infrastructure.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.infrastructure.util.Logger.logResponseController;
import static java.text.MessageFormat.format;

@RestController
@RequiredArgsConstructor
class ItemCardapioController implements ItemCardapioApi {

    private static final String INCLUIR_ITEM = "incluir item no cardápio";
    private static final String CONSULTAR_ITEM = "consultar item com ID {0}";
    private static final String ATUALIZAR_ITEM = "atualizar item com ID {0}";
    private static final String EXCLUIR_ITEM = "excluir item com ID {0}";
    private static final String LISTAR_ITENS_POR_RESTAURANTE = "listar itens do restaurante com ID {0}";

    private final CriarItemCardapioUseCase criarItemCardapioUseCase;
    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    private final AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;
    private final DeletarItemCardapioPorIdUseCase deletarItemCardapioPorIdUseCase;
    private final ListarItensCardapioPorRestauranteUseCase listarItensCardapioPorRestauranteUseCase;
    private final ItemCardapioMapper itemCardapioMapper;

    @Override
    public ItemCardapioResponseDto incluir(Long idRestaurante, ItemCardapioCreateDto dto) {
        logRequestController(INCLUIR_ITEM, dto);

        var item = itemCardapioMapper.mapToItemCardapio(dto);
        var criado = criarItemCardapioUseCase.executar(idRestaurante, item);
        var responseDto = itemCardapioMapper.mapToItemCardapioResponseDto(criado);

        logResponseController(INCLUIR_ITEM, responseDto);
        return responseDto;
    }

    @Override
    public ItemCardapioResponseDto buscarPorId(Long id) {
        logRequestController(format(CONSULTAR_ITEM, id));

        var item = buscarItemCardapioPorIdUseCase.executar(id);
        var responseDto = itemCardapioMapper.mapToItemCardapioResponseDto(item);

        logResponseController(format(CONSULTAR_ITEM, id), responseDto);
        return responseDto;
    }

    @Override
    public ItemCardapioResponseDto atualizar(Long idItem, Long idRestaurante, ItemCardapioUpdateDto dto) {
        logRequestController(format(ATUALIZAR_ITEM, idItem), dto);

        var item = itemCardapioMapper.mapToItemCardapio(dto);
        var atualizado = atualizarItemCardapioUseCase.executar(idItem, idRestaurante, item);
        var responseDto = itemCardapioMapper.mapToItemCardapioResponseDto(atualizado);

        logResponseController(format(ATUALIZAR_ITEM, idItem), responseDto);
        return responseDto;
    }

    @Override
    public void excluir(Long id) {
        logRequestController(format(EXCLUIR_ITEM, id));

        deletarItemCardapioPorIdUseCase.executar(id);

        logResponseController(format(EXCLUIR_ITEM, id));
    }

    @Override
    public List<ItemCardapioResponseDto> listarPorRestaurante(Long idRestaurante) {
        logRequestController(format(LISTAR_ITENS_POR_RESTAURANTE, idRestaurante));

        var lista = listarItensCardapioPorRestauranteUseCase.executar(idRestaurante)
                .stream()
                .map(itemCardapioMapper::mapToItemCardapioResponseDto)
                .toList();

        logResponseController(format(LISTAR_ITENS_POR_RESTAURANTE, idRestaurante), lista);
        return lista;
    }
}
