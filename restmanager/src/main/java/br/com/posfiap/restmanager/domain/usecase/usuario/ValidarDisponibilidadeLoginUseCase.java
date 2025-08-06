package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.exception.BusinessException;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class ValidarDisponibilidadeLoginUseCase {

    private static final String LOGIN_INDISPONIVEL = "Login {0} não está disponível.";

    private final UsuarioRepository usuarioRepository;

    public void executar(String login) {
        usuarioRepository.buscarPorLogin(login)
                .ifPresent(usuario -> {
                    throw new BusinessException(format(LOGIN_INDISPONIVEL, login));
                });
    }
}
