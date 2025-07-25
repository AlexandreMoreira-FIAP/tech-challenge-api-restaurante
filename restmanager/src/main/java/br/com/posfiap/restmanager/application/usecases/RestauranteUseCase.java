package br.com.posfiap.restmanager.application.usecases;

import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.domain.ports.RestauranteRepositoryPort;
import br.com.posfiap.restmanager.error.AuthenticationException;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
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
public class RestauranteUseCase {

    private static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante não encontrado com ID {0}.";
    private static final String LOGIN_INDISPONIVEL = "Login {0} não está disponível.";
    private static final String SENHA_INCORRETA = "Senha incorreta.";

    private final RestauranteRepositoryPort restauranteRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public Restaurante incluir(Restaurante restaurante) {
        validarDisponibilidadeLogin(restaurante.getLogin());
        restaurante.setSenha(passwordEncoder.encode(restaurante.getSenha()));
        return restauranteRepositoryPort.salvar(restaurante);
    }

    public Restaurante buscarPorId(Long id) {
        return restauranteRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException(format(RESTAURANTE_NAO_ENCONTRADO, id)));
    }

    public void excluir(Long id) {
        buscarPorId(id);
        restauranteRepositoryPort.excluir(id);
    }

    public Optional<Restaurante> buscarPorLogin(String login) {
        return restauranteRepositoryPort.buscarPorLogin(login);
    }

    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        var restaurante = buscarPorId(id);
        validarSenha(senhaAtual, restaurante);
        restaurante.setSenha(passwordEncoder.encode(novaSenha));
        restaurante.setDataUltimaAlteracao(now());
        restauranteRepositoryPort.salvar(restaurante);
    }

    public Restaurante atualizar(Long id, Restaurante restaurante) {
        var restauranteAtual = buscarPorId(id);

        if (isNotBlank(restauranteAtual.getLogin()) && !restauranteAtual.getLogin().equals(restaurante.getLogin())) {
            validarDisponibilidadeLogin(restaurante.getLogin());
        }

        if (nonNull(restauranteAtual.getEndereco()) && nonNull(restaurante.getEndereco())) {
            restaurante.getEndereco().setId(restauranteAtual.getEndereco().getId());
        }

        restaurante.setId(restauranteAtual.getId());
        restaurante.setSenha(restauranteAtual.getSenha());

        return restauranteRepositoryPort.salvar(restaurante);
    }

    private void validarDisponibilidadeLogin(String login) {
        restauranteRepositoryPort.buscarPorLogin(login)
                .ifPresent(restaurante -> {
                    throw new BusinessException(format(LOGIN_INDISPONIVEL, login));
                });
    }

    private void validarSenha(String senhaAtual, Restaurante restaurante) {
        if (!passwordEncoder.matches(senhaAtual, restaurante.getSenha())) {
            throw new AuthenticationException(SENHA_INCORRETA);
        }
    }
}