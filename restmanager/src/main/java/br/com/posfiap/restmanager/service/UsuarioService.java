package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.Usuario;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario incluir(Usuario usuario) {

        validarDisponibilidadeLogin(usuario.getLogin());

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        var usuarioEntity = usuarioRepository.save(usuarioMapper.mapToUsuarioEntity(usuario));

        return usuarioMapper.mapToUsuario(usuarioEntity);
    }

    public Usuario buscarPorId(Long id) {

        return usuarioRepository.findById(id)
                .map(usuarioMapper::mapToUsuario)
                .orElseThrow(() -> new NotFoundException(format("Usuário não encontrado com ID {0}.", id)));
    }

    public Usuario atualizar(Long id, Usuario usuario) {

        var usuarioEntityAtual = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Usuário não encontrado com ID {0}.", id)));

        validarDisponibilidadeLogin(usuario.getLogin());

        var usuarioEntity = usuarioMapper.mapToUsuarioEntity(usuarioEntityAtual, usuario);

        return usuarioMapper.mapToUsuario(usuarioRepository.save(usuarioEntity));
    }

    public void excluir(Long id) {

        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }

    public void validarDisponibilidadeLogin(String login) {

        usuarioRepository.findByLogin(login)
                .ifPresent(usuarioEntity -> {
                    throw new BusinessException(format("Login {0} não está disponível.", login));
                });
    }
}