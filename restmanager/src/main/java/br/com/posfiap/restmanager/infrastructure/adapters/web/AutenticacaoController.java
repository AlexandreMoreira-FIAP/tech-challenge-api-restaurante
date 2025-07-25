package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.LoginDto;
import br.com.posfiap.restmanager.service.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static br.com.posfiap.restmanager.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.util.Logger.logResponseController;
import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/autenticacao")
class AutenticacaoController {

    private static final String AUTENTICAR_LOGIN = "autenticar login {0}";

    private final AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Autenticar login e senha")
    void autenticarLogin(@RequestBody @Valid LoginDto loginDto) {

        logRequestController(format(AUTENTICAR_LOGIN, loginDto.getLogin()));

        autenticacaoService.autenticarLogin(loginDto.getLogin(), loginDto.getSenha());

        logResponseController(format(AUTENTICAR_LOGIN, loginDto.getLogin()));
    }
}