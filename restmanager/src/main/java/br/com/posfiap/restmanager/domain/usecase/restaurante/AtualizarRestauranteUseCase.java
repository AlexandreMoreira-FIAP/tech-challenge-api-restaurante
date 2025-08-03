package br.com.posfiap.restmanager.domain.usecase.restaurante;

import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.exception.UnauthorizedException;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class AtualizarRestauranteUseCase {

    private static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante não encontrado com ID {0}.";
    private static final String USUARIO_NAO_AUTORIZADO = "Usuário com ID {0} não tem permissão para atualizar o restaurante com ID {1}.";

    private final RestauranteRepository restauranteRepository;

    public Restaurante executar(Long idRestaurante, Long idUsuario, Restaurante restauranteAtualizado) {
        Restaurante restauranteExistente = restauranteRepository.buscarPorId(idRestaurante)
                .orElseThrow(() -> new NotFoundException(format(RESTAURANTE_NAO_ENCONTRADO, idRestaurante)));

        if (!restauranteExistente.getProprietario().getId().equals(idUsuario)) {
            throw new UnauthorizedException(USUARIO_NAO_AUTORIZADO);
        }

        restauranteAtualizado.setId(restauranteExistente.getId());
        restauranteAtualizado.setDataUltimaAlteracao(now());

        return restauranteRepository.salvar(restauranteAtualizado);
    }

}
