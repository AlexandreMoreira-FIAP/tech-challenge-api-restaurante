package br.com.posfiap.restmanager.domain.usecase.restaurante;

import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class DeletarRestaurantePorIdUseCase {

    private static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante não encontrado com ID {0}.";

    private final RestauranteRepository restauranteRepository;

    public void executar(Long id) {
        var restaurante = restauranteRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(RESTAURANTE_NAO_ENCONTRADO, id)));

        restauranteRepository.deletarPorId(restaurante.getId());
    }
}
