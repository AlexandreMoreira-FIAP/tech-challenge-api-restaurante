package br.com.posfiap.restmanager.domain.usecase.itemcardapio;

import br.com.posfiap.restmanager.domain.exception.NotFoundException;
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
public class CriarItemCardapioUseCase {

    private static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante não encontrado com ID {0}.";

    private final ItemCardapioRepository itemCardapioRepository;
    private final RestauranteRepository restauranteRepository;

    public ItemCardapio executar(Long idRestaurante, ItemCardapio itemCardapio) {
        Restaurante restaurante = restauranteRepository.buscarPorId(idRestaurante)
                .orElseThrow(() -> new NotFoundException(format(RESTAURANTE_NAO_ENCONTRADO, idRestaurante)));

        itemCardapio.setRestaurante(restaurante);
        itemCardapio.setDataUltimaAlteracao(LocalDateTime.now());

        return itemCardapioRepository.salvar(itemCardapio);
    }
}
