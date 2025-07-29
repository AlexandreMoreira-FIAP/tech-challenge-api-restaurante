package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.Item;
import br.com.posfiap.restmanager.error.AuthenticationException;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import br.com.posfiap.restmanager.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
@RequiredArgsConstructor
public class ItemService {

    private static final String Item_NAO_ENCONTRADO = "Item não encontrado com ID {0}.";

    private final ItemRepository itemRepository;
    private final RestauranteService restauranteService;

    public Item incluir(Item item) {

        return itemRepository.salvar(item);
    }

    public Item incluir(Item item, Long restauranteId) {

        // Verificar se o restaurante existe
        var restaurante = restauranteService.buscarPorId(restauranteId);
        
        // Salvar o item primeiro
        var itemSalvo = itemRepository.salvar(item);
        
        // Associar ao restaurante (será feito pelo relacionamento JPA)
        itemSalvo.setRestaurante(restaurante);
        
        return itemRepository.salvar(itemSalvo);
    }

    public Item buscarPorId(Long id) {

        return itemRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(Item_NAO_ENCONTRADO, id)));
    }

    public void excluir(Long id) {

        buscarPorId(id);
        itemRepository.excluir(id);
    }

    public Item atualizar(Long id, Item item) {

        var itemAtual = buscarPorId(id);

        item.setId(itemAtual.getId());

        return itemRepository.salvar(item);
    }

}