package br.com.posfiap.restmanager.infrastructure.persistence;

import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.ItemCardapioEntity;
import br.com.posfiap.restmanager.infrastructure.persistence.jpa.ItemCardapioJpaRepository;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import br.com.posfiap.restmanager.application.mapper.ItemCardapioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemCardapioRepositoryImpl implements ItemCardapioRepository {

    private final ItemCardapioJpaRepository jpaRepository;
    private final ItemCardapioMapper mapper;

    @Override
    public ItemCardapio salvar(ItemCardapio itemCardapio) {
        ItemCardapioEntity entity = mapper.toEntity(itemCardapio);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<ItemCardapio> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ItemCardapio> buscarPorRestauranteId(Long restauranteId) {
        return jpaRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }
}
