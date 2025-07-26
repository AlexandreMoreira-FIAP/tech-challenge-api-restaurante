package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.domain.ports.RestauranteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestauranteRepositoryAdapter implements RestauranteRepositoryPort {

    private final RestauranteRepository restauranteRepository;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return restauranteRepository.salvar(restaurante);
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.buscarPorId(id);
    }

    @Override
    public Optional<Restaurante> buscarPorLogin(String login) {
        return restauranteRepository.buscarPorLogin(login);
    }

    @Override
    public void excluir(Long id) {
        restauranteRepository.excluir(id);
    }
}