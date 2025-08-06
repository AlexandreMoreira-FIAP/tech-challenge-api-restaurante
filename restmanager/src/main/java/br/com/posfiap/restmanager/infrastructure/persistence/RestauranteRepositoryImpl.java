package br.com.posfiap.restmanager.infrastructure.persistence;

import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.RestauranteEntity;
import br.com.posfiap.restmanager.infrastructure.persistence.jpa.RestauranteJpaRepository;
import br.com.posfiap.restmanager.application.mapper.RestauranteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RestauranteRepositoryImpl implements RestauranteRepository {

    private final RestauranteJpaRepository jpaRepository;
    private final RestauranteMapper mapper;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        RestauranteEntity entity = mapper.toEntity(restaurante);
        RestauranteEntity salvo = jpaRepository.save(entity);
        return mapper.toDomain(salvo);
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Restaurante> listarPorProprietario(Long idProprietario) {
        return jpaRepository.findAll().stream()
                .filter(entity -> entity.getProprietario() != null &&
                        entity.getProprietario().getId().equals(idProprietario))
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Long id) {
        jpaRepository.deleteById(id);
    }
}
