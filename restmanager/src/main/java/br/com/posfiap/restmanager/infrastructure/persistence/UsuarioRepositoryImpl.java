package br.com.posfiap.restmanager.infrastructure.persistence;

import br.com.posfiap.restmanager.application.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import br.com.posfiap.restmanager.infrastructure.persistence.jpa.UsuarioJpaRepository;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public List<Usuario> buscarPorIds(List<Long> ids) {
        return usuarioJpaRepository.findAllById(ids).stream()
                .map(usuarioMapper::mapToUsuario)
                .toList();
    }
}
