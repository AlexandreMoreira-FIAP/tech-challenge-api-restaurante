package br.com.posfiap.restmanager.domain.repository;

import br.com.posfiap.restmanager.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long id);

    void excluir(Long id);

    Optional<Usuario> buscarPorLogin(String login);

    List<Usuario> buscarPorIds(List<Long> ids);
}