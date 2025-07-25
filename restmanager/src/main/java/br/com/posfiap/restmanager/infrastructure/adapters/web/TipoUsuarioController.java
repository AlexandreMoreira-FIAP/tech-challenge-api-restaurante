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
}