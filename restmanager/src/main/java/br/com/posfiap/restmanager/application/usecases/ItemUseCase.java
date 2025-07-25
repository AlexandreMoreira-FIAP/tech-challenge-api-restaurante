package br.com.posfiap.restmanager.application.usecases;

import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.domain.ports.ItemRepositoryPort;
import br.com.posfiap.restmanager.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
@RequiredArgsConstructor
public class ItemUseCase {

    private static final String ITEM_NAO_ENCONTRADO = "Item não encontrado com ID {0}.";

    private final ItemRepositoryPort itemRepositoryPort;

    public Item incluir(Item item) {
        return itemRepositoryPort.salvar(item);
    }

    public Item buscarPorId(Long id) {
        return itemRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(ITEM_NAO_ENCONTRADO, id)));
    }

    public void excluir(Long id) {
        buscarPorId(id);
        itemRepositoryPort.excluir(id);
    }

    public Item atualizar(Long id, Item item) {
        var itemAtual = buscarPorId(id);
        item.setId(itemAtual.getId());
        return itemRepositoryPort.salvar(item);
    }
}