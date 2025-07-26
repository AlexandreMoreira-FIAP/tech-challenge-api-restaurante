package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.mapper.RestauranteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
class RestauranteRepositoryImpl implements RestauranteRepository {

    private final RestauranteJpaRepository restauranteJpaRepository;
    private final RestauranteMapper restauranteMapper;

    @Override
    public Restaurante salvar(Restaurante restaurante) {

        var restauranteEntity = restauranteJpaRepository.save(restauranteMapper.mapToRestauranteEntity(restaurante));

        return restauranteMapper.mapToRestaurante(restauranteEntity);
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {

        return restauranteJpaRepository.findById(id)
                .map(restauranteMapper::mapToRestaurante);
    }

    @Override
    public void excluir(Long id) {

        restauranteJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Restaurante> buscarPorLogin(String login) {

        return restauranteJpaRepository.findByLogin(login)
                .map(restauranteMapper::mapToRestaurante);
    }
}