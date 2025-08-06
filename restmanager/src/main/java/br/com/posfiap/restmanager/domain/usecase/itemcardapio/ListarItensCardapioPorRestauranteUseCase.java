package br.com.posfiap.restmanager.domain.usecase.itemcardapio;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarItensCardapioPorRestauranteUseCase {

    private final ItemCardapioRepository itemCardapioRepository;

    public List<ItemCardapio> executar(Long restauranteId) {
        return itemCardapioRepository.buscarPorRestauranteId(restauranteId);
    }
}