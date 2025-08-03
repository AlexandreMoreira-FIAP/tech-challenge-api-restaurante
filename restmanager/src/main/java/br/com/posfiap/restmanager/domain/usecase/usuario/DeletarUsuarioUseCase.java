package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.exception.AuthenticationException;
import br.com.posfiap.restmanager.domain.exception.BusinessException;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class DeletarUsuarioUseCase {

    private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado com ID {0}.";

    private final UsuarioRepository usuarioRepository;

    public void executar(Long id) {
        if (!usuarioRepository.buscarPorId(id).isPresent()) {
            throw new NotFoundException(format(USUARIO_NAO_ENCONTRADO, id));
        }
        usuarioRepository.excluir(id);
    }
}
