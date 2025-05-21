package br.com.posfiap.restmanager.controller;

import br.com.posfiap.restmanager.dto.SenhaDto;
import br.com.posfiap.restmanager.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioUpdateDto;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static br.com.posfiap.restmanager.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.util.Logger.logResponseController;
import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
class UsuarioController {

    private static final String INCLUIR_USUARIO = "incluir usuário";
    private static final String CONSULTAR_USUARIO = "consultar usuário com ID {0}";
    private static final String ATUALIZAR_USUARIO = "atualizar usuário com ID {0}";
    private static final String EXCLUIR_USUARIO = "excluir usuário com ID {0}";
    private static final String ALTERAR_SENHA_USUARIO = "alterar senha do usuário com ID {0}";

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Incluir usuário")
    UsuarioResponseDto incluir(@RequestBody @Valid UsuarioCreateDto usuarioCreateDto) {

        logRequestController(INCLUIR_USUARIO, usuarioCreateDto);

        var usuario = usuarioService.incluir(usuarioMapper.mapToUsuario(usuarioCreateDto));
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(INCLUIR_USUARIO, usuarioResponseDto);
        return usuarioResponseDto;
    }

    @GetMapping("{id}")
    @Operation(summary = "Consultar usuário")
    UsuarioResponseDto buscarPorId(@PathVariable Long id) {

        logRequestController(format(CONSULTAR_USUARIO, id));

        var usuario = usuarioService.buscarPorId(id);
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(format(CONSULTAR_USUARIO, id), usuarioResponseDto);
        return usuarioResponseDto;
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar usuário")
    UsuarioResponseDto atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDto usuarioUpdateDto) {

        logRequestController(format(ATUALIZAR_USUARIO, id), usuarioUpdateDto);

        var usuario = usuarioService.atualizar(id, usuarioMapper.mapToUsuario(usuarioUpdateDto));
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(format(ATUALIZAR_USUARIO, id), usuarioResponseDto);
        return usuarioResponseDto;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir usuário")
    void excluir(@PathVariable Long id) {

        logRequestController(format(EXCLUIR_USUARIO, id));

        usuarioService.excluir(id);

        logResponseController(format(EXCLUIR_USUARIO, id));
    }

    @PatchMapping("{id}/senha")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Alterar senha do usuário")
    void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaDto senhaDto) {

        logRequestController(format(ALTERAR_SENHA_USUARIO, id));

        usuarioService.alterarSenha(id, senhaDto.getSenhaAtual(), senhaDto.getNovaSenha());

        logResponseController(format(ALTERAR_SENHA_USUARIO, id));
    }
}