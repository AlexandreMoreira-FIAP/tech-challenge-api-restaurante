package br.com.posfiap.restmanager.controller;

import br.com.posfiap.restmanager.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static br.com.posfiap.restmanager.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.util.Logger.logResponseController;
import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
class UsuarioController {

    private static final String INCLUIR_USUARIO = "incluir usuário";
    private static final String CONSULTAR_USUARIO_POR_ID = "consultar usuário por id. ID: {0}";

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    UsuarioResponseDto incluir(@RequestBody @Valid UsuarioCreateDto usuarioCreateDto) {

        logRequestController(INCLUIR_USUARIO, usuarioCreateDto);

        var usuario = usuarioService.incluir(usuarioMapper.mapToUsuario(usuarioCreateDto));
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(INCLUIR_USUARIO, usuarioResponseDto);
        return usuarioResponseDto;
    }

    @GetMapping("{id}")
    UsuarioResponseDto buscarPorId(@PathVariable Long id) {

        logRequestController(format(CONSULTAR_USUARIO_POR_ID, id));

        var usuario = usuarioService.buscarPorId(id);
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(format(CONSULTAR_USUARIO_POR_ID, id), usuarioResponseDto);
        return usuarioResponseDto;
    }
}