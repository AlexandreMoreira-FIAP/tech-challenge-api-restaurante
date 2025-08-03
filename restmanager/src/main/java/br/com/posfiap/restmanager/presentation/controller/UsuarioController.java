package br.com.posfiap.restmanager.presentation.controller;

import br.com.posfiap.restmanager.application.dto.*;
import br.com.posfiap.restmanager.application.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.domain.usecase.usuario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import static br.com.posfiap.restmanager.infrastructure.util.Logger.logRequestController;
import static br.com.posfiap.restmanager.infrastructure.util.Logger.logResponseController;
import static java.text.MessageFormat.format;

@RestController
@RequiredArgsConstructor
class UsuarioController implements UsuarioApi {

    private static final String INCLUIR_USUARIO = "incluir usuário";
    private static final String CONSULTAR_USUARIO = "consultar usuário com ID {0}";
    private static final String CONSULTAR_USUARIO_COM_RESTAURANTES = "consultar usuário com restaurantes com ID {0}";
    private static final String ATUALIZAR_USUARIO = "atualizar usuário com ID {0}";
    private static final String EXCLUIR_USUARIO = "excluir usuário com ID {0}";
    private static final String ALTERAR_SENHA_USUARIO = "alterar senha do usuário com ID {0}";

    private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final AlterarSenhaUsuarioUseCase alterarSenhaUsuarioUseCase;
    private final BuscarUsuarioComRestaurantesUseCase buscarUsuarioComRestaurantesUseCase;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponseDto incluir(UsuarioCreateDto usuarioCreateDto) {
        logRequestController(INCLUIR_USUARIO, usuarioCreateDto);

        var usuario = cadastrarUsuarioUseCase.executar(usuarioMapper.mapToUsuario(usuarioCreateDto));
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(INCLUIR_USUARIO, usuarioResponseDto);
        return usuarioResponseDto;
    }

    @Override
    public UsuarioResponseDto buscarPorId(Long id) {
        logRequestController(format(CONSULTAR_USUARIO, id));

        var usuario = buscarUsuarioPorIdUseCase.executar(id);
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(usuario);

        logResponseController(format(CONSULTAR_USUARIO, id), usuarioResponseDto);
        return usuarioResponseDto;
    }

    @Override
    public UsuarioResponseDto atualizar(Long id, UsuarioUpdateDto usuarioUpdateDto) {
        logRequestController(format(ATUALIZAR_USUARIO, id), usuarioUpdateDto);

        var usuario = usuarioMapper.mapToUsuario(usuarioUpdateDto);
        var atualizado = atualizarUsuarioUseCase.executar(id, usuario);
        var usuarioResponseDto = usuarioMapper.mapToUsuarioResponseDto(atualizado);

        logResponseController(format(ATUALIZAR_USUARIO, id), usuarioResponseDto);
        return usuarioResponseDto;
    }

    @Override
    public void excluir(Long id) {
        logRequestController(format(EXCLUIR_USUARIO, id));
        deletarUsuarioUseCase.executar(id);
        logResponseController(format(EXCLUIR_USUARIO, id));
    }

    @Override
    public void alterarSenha(Long id, SenhaDto senhaDto) {
        logRequestController(format(ALTERAR_SENHA_USUARIO, id));
        alterarSenhaUsuarioUseCase.executar(id, senhaDto.getSenhaAtual(), senhaDto.getNovaSenha());
        logResponseController(format(ALTERAR_SENHA_USUARIO, id));
    }

    @Override
    public UsuarioComRestaurantesDto buscarComRestaurantes(Long id) {
        logRequestController(format(CONSULTAR_USUARIO_COM_RESTAURANTES, id));

        var usuario = buscarUsuarioComRestaurantesUseCase.executar(id);
        var usuarioComRestaurantesDto = usuarioMapper.mapToUsuarioComRestaurantesDto(usuario);

        logResponseController(format(CONSULTAR_USUARIO_COM_RESTAURANTES, id), usuarioComRestaurantesDto);
        return usuarioComRestaurantesDto;
    }
}
