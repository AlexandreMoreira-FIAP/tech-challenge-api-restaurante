package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class BuscarUsuarioComRestaurantesUseCase {

    private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado com ID {0}.";

    private final UsuarioRepository usuarioRepository;

    public Usuario executar(Long id) {
        return usuarioRepository.buscarComRestaurantes(id)
                .orElseThrow(() -> new NotFoundException(format(USUARIO_NAO_ENCONTRADO, id)));
    }
}
