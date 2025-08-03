package br.com.posfiap.restmanager.infrastructure.persistence;

import br.com.posfiap.restmanager.application.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.UsuarioEntity;
import br.com.posfiap.restmanager.infrastructure.persistence.jpa.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = usuarioMapper.mapToUsuarioEntity(usuario);
        UsuarioEntity saved = usuarioJpaRepository.save(entity);
        return usuarioMapper.mapToUsuario(saved);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioJpaRepository.findById(id)
                .map(usuarioMapper::mapToUsuario);
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
    public Optional<Usuario> buscarComRestaurantes(Long id) {
        return usuarioJpaRepository.findWithRestaurantesById(id)
                .map(usuarioMapper::mapToUsuario);
    }
}
