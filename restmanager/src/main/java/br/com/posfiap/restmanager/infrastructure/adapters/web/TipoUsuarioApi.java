package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/tipos-usuario")
interface TipoUsuarioApi {

    @GetMapping
    @Operation(summary = "Listar todos os tipos de usuário disponíveis")
    List<TipoUsuarioDto> listarTodos();
}