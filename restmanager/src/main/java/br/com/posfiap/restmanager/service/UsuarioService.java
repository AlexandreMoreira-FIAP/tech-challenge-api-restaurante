package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.application.usecases.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioUseCase usuarioUseCase;

    public Usuario incluir(Usuario usuario) {
        return usuarioUseCase.incluir(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioUseCase.buscarPorId(id);
    }

    public void excluir(Long id) {
        usuarioUseCase.excluir(id);
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        return usuarioUseCase.buscarPorLogin(login);
    }

    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        usuarioUseCase.alterarSenha(id, senhaAtual, novaSenha);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        return usuarioUseCase.atualizar(id, usuario);
    }

    public List<Usuario> buscarPorIds(List<Long> ids) {
        return usuarioRepository.buscarPorIds(ids);
    }
}