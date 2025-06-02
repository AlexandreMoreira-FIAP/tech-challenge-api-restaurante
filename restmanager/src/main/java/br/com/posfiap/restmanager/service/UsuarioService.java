package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.Usuario;
import br.com.posfiap.restmanager.error.AuthenticationException;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import br.com.posfiap.restmanager.repository.UsuarioRepository;
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
public class UsuarioService {

    private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado com ID {0}.";
    private static final String LOGIN_INDISPONIVEL = "Login {0} não está disponível.";
    private static final String SENHA_INCORRETA = "Senha incorreta.";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario incluir(Usuario usuario) {

        validarDisponibilidadeLogin(usuario.getLogin());

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.salvar(usuario);
    }

    public Usuario buscarPorId(Long id) {

        return usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(USUARIO_NAO_ENCONTRADO, id)));
    }

    public void excluir(Long id) {

        buscarPorId(id);
        usuarioRepository.excluir(id);
    }

    public Optional<Usuario> buscarPorLogin(String login) {

        return usuarioRepository.buscarPorLogin(login);
    }

    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {

        var usuario = buscarPorId(id);

        validarSenha(senhaAtual, usuario);

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuario.setDataUltimaAlteracao(now());

        usuarioRepository.salvar(usuario);
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

        return usuarioRepository.salvar(usuario);
    }

    private void validarDisponibilidadeLogin(String login) {

        usuarioRepository.buscarPorLogin(login)
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