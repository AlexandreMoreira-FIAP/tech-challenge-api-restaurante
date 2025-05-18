package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.Usuario;
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

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        var usuarioEntity = usuarioRepository.save(usuarioMapper.mapToUsuarioEntity(usuario));

        return usuarioMapper.mapToUsuario(usuarioEntity);
    }

    public Usuario buscarPorId(Long id) {

        return usuarioRepository.findById(id)
                .map(usuarioMapper::mapToUsuario)
                .orElseThrow(() -> new NotFoundException(format("Usuário não encontrado com ID {0}.", id)));
    }
}