package br.com.posfiap.restmanager.domain.ports;

import br.com.posfiap.restmanager.domain.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorLogin(String login);
    void excluir(Long id);
}