package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import br.com.posfiap.restmanager.application.services.TipoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TipoUsuarioController implements TipoUsuarioApi {

    private final TipoUsuarioService tipoUsuarioService;

    @Override
    public List<TipoUsuarioDto> listarTodos() {
        return tipoUsuarioService.listarTodos();
    }

    @Override
    public TipoUsuarioDto buscarPorId(Long id) {
        return tipoUsuarioService.buscarPorId(id);
    }

    @Override
    public TipoUsuarioDto criar(TipoUsuarioDto tipoUsuarioDto) {
        return tipoUsuarioService.criar(tipoUsuarioDto);
    }

    @Override
    public TipoUsuarioDto atualizar(Long id, TipoUsuarioDto tipoUsuarioDto) {
        return tipoUsuarioService.atualizar(id, tipoUsuarioDto);
    }

    @Override
    public void deletar(Long id) {
        tipoUsuarioService.deletar(id);
    }
}