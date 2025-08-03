package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.exception.BusinessException;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class CadastrarUsuarioUseCase {

    private static final String LOGIN_INDISPONIVEL = "Login {0} não está disponível.";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario executar(Usuario usuario) {
        validarDisponibilidadeLogin(usuario.getLogin());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.salvar(usuario);
    }

    private void validarDisponibilidadeLogin(String login) {
        usuarioRepository.buscarPorLogin(login)
                .ifPresent(usuario -> {
                    throw new BusinessException(format(LOGIN_INDISPONIVEL, login));
                });
    }
}
