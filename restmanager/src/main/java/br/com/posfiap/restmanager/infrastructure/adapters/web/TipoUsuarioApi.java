package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RequestMapping("/v1/tipos-usuario")
interface TipoUsuarioApi {

    @GetMapping
    @Operation(summary = "Listar todos os tipos de usuário disponíveis")
    List<TipoUsuarioDto> listarTodos();

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuário por ID")
    TipoUsuarioDto buscarPorId(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Criar novo tipo de usuário")
    TipoUsuarioDto criar(@Valid @RequestBody TipoUsuarioDto tipoUsuarioDto);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de usuário")
    TipoUsuarioDto atualizar(@PathVariable Long id, @Valid @RequestBody TipoUsuarioDto tipoUsuarioDto);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tipo de usuário")
    void deletar(@PathVariable Long id);
}