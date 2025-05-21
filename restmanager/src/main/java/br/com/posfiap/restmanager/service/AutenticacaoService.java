package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.error.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutenticacaoService {

    private final PasswordEncoder passwordEncoder;

    public String criptografarSenha(String senha) {

        return passwordEncoder.encode(senha);
    }

    public void validarSenha(String senha, String senhaCriptografada) {

        if (!passwordEncoder.matches(senha, senhaCriptografada)) {

            throw new AuthenticationException("Senha incorreta.");
        }
    }
}
