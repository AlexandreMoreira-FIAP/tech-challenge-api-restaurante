package br.com.posfiap.restmanager.controller;

import br.com.posfiap.restmanager.dto.SenhaDto;
import br.com.posfiap.restmanager.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioUpdateDto;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import br.com.posfiap.restmanager.service.UsuarioService;

import static br.com.posfiap.restmanager.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.util.Logger.logResponseController;
import static java.text.MessageFormat.format;

@RestController
@RequiredArgsConstructor
class UsuarioController implements UsuarioApi {

    private static final String INCLUIR_USUARIO = "incluir usuário";
    private static final String CONSULTAR_USUARIO = "consultar usuário com ID {0}";
    private static final String ATUALIZAR_USUARIO = "atualizar usuário com ID {0}";
    private static final String EXCLUIR_USUARIO = "excluir usuário com ID {0}";
    private static final String ALTERAR_SENHA_USUARIO = "alterar senha do usuário com ID {0}";

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponseDto incluir(UsuarioCreateDto usuarioCreateDto) {

        logRequestController(INCLUIR_USUARIO, usuarioCreateDto);

        var usuario = usuarioService.incluir(usuarioMapper.mapToUsuario(usuarioCreateDto));
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(INCLUIR_USUARIO, usuarioResponseDto);
        return usuarioResponseDto;
    }

    @Override
    public UsuarioResponseDto buscarPorId(Long id) {

        logRequestController(format(CONSULTAR_USUARIO, id));

        var usuario = usuarioService.buscarPorId(id);
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(format(CONSULTAR_USUARIO, id), usuarioResponseDto);
        return usuarioResponseDto;
    }

    @Override
    public UsuarioResponseDto atualizar(Long id, UsuarioUpdateDto usuarioUpdateDto) {

        logRequestController(format(ATUALIZAR_USUARIO, id), usuarioUpdateDto);

        var usuario = usuarioService.atualizar(id, usuarioMapper.mapToUsuario(usuarioUpdateDto));
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(format(ATUALIZAR_USUARIO, id), usuarioResponseDto);
        return usuarioResponseDto;
    }

    @Override
    public void excluir(Long id) {

        logRequestController(format(EXCLUIR_USUARIO, id));

        usuarioService.excluir(id);

        logResponseController(format(EXCLUIR_USUARIO, id));
    }

    @Override
    public void alterarSenha(Long id, SenhaDto senhaDto) {

        logRequestController(format(ALTERAR_SENHA_USUARIO, id));

        usuarioService.alterarSenha(id, senhaDto.getSenhaAtual(), senhaDto.getNovaSenha());

        logResponseController(format(ALTERAR_SENHA_USUARIO, id));
    }
}