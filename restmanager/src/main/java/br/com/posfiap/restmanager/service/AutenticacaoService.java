package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.error.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutenticacaoService {

    private static final String AUTENTICACAO_INVALIDA = "Login ou senha incorreto.";

    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public void autenticarLogin(String login, String senha) {

        usuarioService.buscarPorLogin(login).ifPresentOrElse(usuario -> {

            if (!passwordEncoder.matches(senha, usuario.getSenha())) {

                autenticacaoInvalida();
            }
        }, this::autenticacaoInvalida);
    }

    private void autenticacaoInvalida() {

        throw new AuthenticationException(AUTENTICACAO_INVALIDA);
    }
}