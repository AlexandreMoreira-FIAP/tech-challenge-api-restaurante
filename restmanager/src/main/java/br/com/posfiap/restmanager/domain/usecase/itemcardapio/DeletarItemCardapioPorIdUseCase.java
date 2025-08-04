package br.com.posfiap.restmanager.domain.usecase.itemcardapio;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class DeletarItemCardapioPorIdUseCase {

    private static final String ITEM_NAO_ENCONTRADO = "Item do cardápio não encontrado com ID {0}.";

    private final ItemCardapioRepository itemCardapioRepository;

    public void executar(Long id) {
        if (!itemCardapioRepository.existePorId(id)) {
            throw new NotFoundException(format(ITEM_NAO_ENCONTRADO, id));
        }
        itemCardapioRepository.deletarPorId(id);
    }
}