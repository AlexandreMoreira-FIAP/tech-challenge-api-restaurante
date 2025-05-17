package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.Usuario;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;

    public Usuario incluir(Usuario usuario) {

        var usuarioEntity = usuarioRepository.save(usuarioMapper.mapToUsuarioEntity(usuario));

        return usuarioMapper.mapToUsuario(usuarioEntity);
    }
}