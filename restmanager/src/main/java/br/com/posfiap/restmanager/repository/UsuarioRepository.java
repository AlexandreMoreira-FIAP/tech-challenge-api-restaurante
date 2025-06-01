package br.com.posfiap.restmanager.repository;

import br.com.posfiap.restmanager.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long id);

    void excluir(Long id);

    Optional<Usuario> buscarPorLogin(String login);
}