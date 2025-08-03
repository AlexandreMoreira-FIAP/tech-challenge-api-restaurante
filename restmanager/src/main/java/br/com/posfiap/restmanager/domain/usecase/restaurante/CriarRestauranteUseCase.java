package br.com.posfiap.restmanager.domain.usecase.restaurante;

import br.com.posfiap.restmanager.domain.exception.BusinessException;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.posfiap.restmanager.domain.enums.TipoUsuario.PROPRIETARIO;
import static java.text.MessageFormat.format;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class CriarRestauranteUseCase {

    private static final String USUARIO_NAO_ENCONTRADO = "Usuário com ID {0} não encontrado.";
    private static final String USUARIO_NAO_E_PROPRIETARIO = "Apenas usuários do tipo PROPRIETARIO podem criar restaurantes.";

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public Restaurante executar(Long idUsuario, Restaurante restaurante) {
        Usuario usuario = usuarioRepository.buscarPorId(idUsuario)
                .orElseThrow(() -> new NotFoundException(format(USUARIO_NAO_ENCONTRADO, idUsuario)));

        if (usuario.getTipoUsuario() != PROPRIETARIO) {
            throw new BusinessException(USUARIO_NAO_E_PROPRIETARIO);
        }

        restaurante.setProprietario(usuario);
        restaurante.setDataUltimaAlteracao(now());
        return restauranteRepository.salvar(restaurante);
    }
}
