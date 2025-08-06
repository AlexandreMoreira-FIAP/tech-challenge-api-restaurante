package br.com.posfiap.restmanager.domain.usecase.itemcardapio;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class BuscarItemCardapioPorIdUseCase {

    private static final String ITEM_NAO_ENCONTRADO = "Item do cardápio não encontrado com ID {0}.";

    private final ItemCardapioRepository itemCardapioRepository;

    public ItemCardapio executar(Long id) {
        return itemCardapioRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(ITEM_NAO_ENCONTRADO, id)));
    }
}