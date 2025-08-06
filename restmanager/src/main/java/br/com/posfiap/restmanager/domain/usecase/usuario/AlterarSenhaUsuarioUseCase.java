package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.exception.AuthenticationException;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static java.text.MessageFormat.format;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class AlterarSenhaUsuarioUseCase {

    private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado com ID {0}.";
    private static final String SENHA_INCORRETA = "Senha incorreta.";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void executar(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(USUARIO_NAO_ENCONTRADO, id)));

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new AuthenticationException(SENHA_INCORRETA);
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuario.setDataUltimaAlteracao(now());
        usuarioRepository.salvar(usuario);
    }
}
