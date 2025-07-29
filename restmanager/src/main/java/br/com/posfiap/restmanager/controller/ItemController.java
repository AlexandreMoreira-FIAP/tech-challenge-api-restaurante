package br.com.posfiap.restmanager.controller;

import br.com.posfiap.restmanager.dto.SenhaDto;
import br.com.posfiap.restmanager.dto.ItemCreateDto;
import br.com.posfiap.restmanager.dto.ItemResponseDto;
import br.com.posfiap.restmanager.dto.ItemUpdateDto;
import br.com.posfiap.restmanager.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import static br.com.posfiap.restmanager.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.util.Logger.logResponseController;
import static java.text.MessageFormat.format;
import br.com.posfiap.restmanager.service.ItemService;

@RestController
@RequiredArgsConstructor
class ItemController implements ItemApi {

    private static final String INCLUIR_ITEM = "incluir usuário";
    private static final String CONSULTAR_ITEM = "consultar usuário com ID {0}";
    private static final String ATUALIZAR_ITEM = "atualizar usuário com ID {0}";
    private static final String EXCLUIR_ITEM = "excluir usuário com ID {0}";


    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Override
    public ItemResponseDto incluir(ItemCreateDto itemCreateDto) {

        logRequestController(INCLUIR_ITEM, itemCreateDto);

        var item = itemService.incluir(itemMapper.mapToItem(itemCreateDto), itemCreateDto.getRestauranteId());
        var itemResponseDto = itemMapper.mapToItemResponseDto(item);

        logResponseController(INCLUIR_ITEM, itemResponseDto);
        return itemResponseDto;
    }

    @Override
    public ItemResponseDto buscarPorId(Long id) {

        logRequestController(format(CONSULTAR_ITEM, id));

        var item = itemService.buscarPorId(id);
        var itemResponseDto = itemMapper.mapToItemResponseDto(item);

        logResponseController(format(CONSULTAR_ITEM, id), itemResponseDto);
        return itemResponseDto;
    }

    @Override
    public ItemResponseDto atualizar(Long id, ItemUpdateDto itemUpdateDto) {

        logRequestController(format(ATUALIZAR_ITEM, id), itemUpdateDto);

        var item = itemService.atualizar(id, itemMapper.mapToItem(itemUpdateDto));
        var itemResponseDto = itemMapper.mapToItemResponseDto(item);

        logResponseController(format(ATUALIZAR_ITEM, id), itemResponseDto);
        return itemResponseDto;
    }

    @Override
    public void excluir(Long id) {

        logRequestController(format(EXCLUIR_ITEM, id));

        itemService.excluir(id);

        logResponseController(format(EXCLUIR_ITEM, id));
    }


}