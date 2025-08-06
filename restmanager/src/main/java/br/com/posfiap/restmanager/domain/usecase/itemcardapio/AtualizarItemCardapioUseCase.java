package br.com.posfiap.restmanager.domain.usecase.itemcardapio;

import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.exception.UnauthorizedException;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class AtualizarItemCardapioUseCase {

    private static final String ITEM_NAO_ENCONTRADO = "Item do cardápio não encontrado com ID {0}.";
    private static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante não encontrado com ID {0}.";

    private final ItemCardapioRepository itemCardapioRepository;
    private final RestauranteRepository restauranteRepository;

    public ItemCardapio executar(Long idItem, Long idRestaurante, ItemCardapio itemAtualizado) {
        ItemCardapio existente = itemCardapioRepository.buscarPorId(idItem)
                .orElseThrow(() -> new NotFoundException(format(ITEM_NAO_ENCONTRADO, idItem)));

        Restaurante restaurante = restauranteRepository.buscarPorId(idRestaurante)
                .orElseThrow(() -> new NotFoundException(format(RESTAURANTE_NAO_ENCONTRADO, idRestaurante)));

        if (!existente.getRestaurante().getId().equals(restaurante.getId())) {
            throw new UnauthorizedException("Este item não pertence ao restaurante informado.");
        }

        itemAtualizado.setId(existente.getId());
        itemAtualizado.setRestaurante(restaurante);
        itemAtualizado.setDataUltimaAlteracao(LocalDateTime.now());

        return itemCardapioRepository.salvar(itemAtualizado);
    }

}
