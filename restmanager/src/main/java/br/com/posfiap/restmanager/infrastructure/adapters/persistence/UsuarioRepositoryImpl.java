package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario salvar(Usuario usuario) {

        var usuarioEntity = usuarioJpaRepository.save(usuarioMapper.mapToUsuarioEntity(usuario));

        return usuarioMapper.mapToUsuario(usuarioEntity);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {

        return usuarioJpaRepository.findById(id)
                .map(usuarioEntity -> {
                    var usuario = usuarioMapper.mapToUsuario(usuarioEntity);
                    // Mapear restaurantes manualmente para evitar loop
                    if (usuarioEntity.getRestaurantes() != null) {
                        var restaurantes = usuarioEntity.getRestaurantes().stream()
                                .map(restauranteEntity -> br.com.posfiap.restmanager.domain.Restaurante.builder()
                                    .id(restauranteEntity.getId())
                                    .nome(restauranteEntity.getNome())
                                    .tipoDeCozinha(restauranteEntity.getTipoDeCozinha())
                                    .login(restauranteEntity.getLogin())
                                    .tipoUsuario(restauranteEntity.getTipoUsuario())
                                    .dataUltimaAlteracao(restauranteEntity.getDataUltimaAlteracao())
                                    .usuarios(null) // Evitar referência circular
                                    .build())
                                .toList();
                        usuario.setRestaurantes(restaurantes);
                    }
                    return usuario;
                });
    }

    @Override
    public void excluir(Long id) {

        usuarioJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {

        return usuarioJpaRepository.findByLogin(login)
                .map(usuarioMapper::mapToUsuario);
    }

    @Override
    public List<Usuario> buscarPorIds(List<Long> ids) {
        return usuarioJpaRepository.findAllById(ids)
                .stream()
                .map(usuarioMapper::mapToUsuario)
                .toList();
    }
}