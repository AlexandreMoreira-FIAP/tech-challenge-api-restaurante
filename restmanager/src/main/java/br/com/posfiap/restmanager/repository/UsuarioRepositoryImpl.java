package br.com.posfiap.restmanager.repository;

import br.com.posfiap.restmanager.domain.Usuario;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
}