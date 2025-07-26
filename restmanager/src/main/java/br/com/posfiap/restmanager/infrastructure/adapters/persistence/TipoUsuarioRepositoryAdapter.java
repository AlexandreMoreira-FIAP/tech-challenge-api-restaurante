package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.TipoUsuarioEntity;
import br.com.posfiap.restmanager.domain.ports.TipoUsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TipoUsuarioRepositoryAdapter implements TipoUsuarioRepositoryPort {

    private final TipoUsuarioJpaRepository jpaRepository;

    @Override
    public List<TipoUsuarioEntity> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<TipoUsuarioEntity> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<TipoUsuarioEntity> findByCodigo(String codigo) {
        return jpaRepository.findByCodigo(codigo)
                .map(this::toDomain);
    }

    @Override
    public TipoUsuarioEntity save(TipoUsuarioEntity tipoUsuario) {
        var entity = toEntity(tipoUsuario);
        var saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        return jpaRepository.existsByCodigo(codigo);
    }

    private TipoUsuarioEntity toDomain(br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity entity) {
        return TipoUsuarioEntity.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .descricao(entity.getDescricao())
                .build();
    }

    private br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity toEntity(TipoUsuarioEntity domain) {
        return br.com.posfiap.restmanager.infrastructure.entities.TipoUsuarioEntity.builder()
                .id(domain.getId())
                .codigo(domain.getCodigo())
                .descricao(domain.getDescricao())
                .build();
    }
}