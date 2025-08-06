package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class AtualizarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidarDisponibilidadeLoginUseCase validarDisponibilidadeLoginUseCase;

    public Usuario executar(Long id, Usuario usuario) {
        var usuarioAtual = buscarUsuarioPorIdUseCase.executar(id);

        if (isNotBlank(usuarioAtual.getLogin()) && !usuarioAtual.getLogin().equals(usuario.getLogin())) {
            validarDisponibilidadeLoginUseCase.executar(usuario.getLogin());
        }

        if (nonNull(usuarioAtual.getEndereco()) && nonNull(usuario.getEndereco())) {
            usuario.getEndereco().setId(usuarioAtual.getEndereco().getId());
        }

        usuario.setId(usuarioAtual.getId());
        usuario.setSenha(usuarioAtual.getSenha());
        usuario.setDataUltimaAlteracao(now());

        return usuarioRepository.salvar(usuario);
    }
}
