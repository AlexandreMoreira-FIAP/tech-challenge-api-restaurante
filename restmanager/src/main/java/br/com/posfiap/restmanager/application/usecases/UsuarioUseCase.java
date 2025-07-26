package br.com.posfiap.restmanager.application.usecases;

import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.domain.ports.UsuarioRepositoryPort;
import br.com.posfiap.restmanager.error.AuthenticationException;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.text.MessageFormat.format;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class UsuarioUseCase {

    private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado com ID {0}.";
    private static final String LOGIN_INDISPONIVEL = "Login {0} não está disponível.";
    private static final String SENHA_INCORRETA = "Senha incorreta.";

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public Usuario incluir(Usuario usuario) {
        validarDisponibilidadeLogin(usuario.getLogin());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepositoryPort.salvar(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(USUARIO_NAO_ENCONTRADO, id)));
    }

    public void excluir(Long id) {
        buscarPorId(id);
        usuarioRepositoryPort.excluir(id);
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        return usuarioRepositoryPort.buscarPorLogin(login);
    }

    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        var usuario = buscarPorId(id);
        validarSenha(senhaAtual, usuario);
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuario.setDataUltimaAlteracao(now());
        usuarioRepositoryPort.salvar(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        var usuarioAtual = buscarPorId(id);

        if (isNotBlank(usuarioAtual.getLogin()) && !usuarioAtual.getLogin().equals(usuario.getLogin())) {
            validarDisponibilidadeLogin(usuario.getLogin());
        }

        if (nonNull(usuarioAtual.getEndereco()) && nonNull(usuario.getEndereco())) {
            usuario.getEndereco().setId(usuarioAtual.getEndereco().getId());
        }

        usuario.setId(usuarioAtual.getId());
        usuario.setSenha(usuarioAtual.getSenha());

        return usuarioRepositoryPort.salvar(usuario);
    }

    private void validarDisponibilidadeLogin(String login) {
        usuarioRepositoryPort.buscarPorLogin(login)
                .ifPresent(usuario -> {
                    throw new BusinessException(format(LOGIN_INDISPONIVEL, login));
                });
    }

    private void validarSenha(String senhaAtual, Usuario usuario) {
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new AuthenticationException(SENHA_INCORRETA);
        }
    }
}