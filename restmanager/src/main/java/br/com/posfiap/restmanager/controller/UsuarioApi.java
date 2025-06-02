package br.com.posfiap.restmanager.controller;

import br.com.posfiap.restmanager.dto.SenhaDto;
import br.com.posfiap.restmanager.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@RequestMapping("/v1/usuarios")
interface UsuarioApi {

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Incluir usuário")
    UsuarioResponseDto incluir(@RequestBody @Valid UsuarioCreateDto usuarioCreateDto);

    @GetMapping("{id}")
    @Operation(summary = "Consultar usuário")
    UsuarioResponseDto buscarPorId(@PathVariable Long id);

    @PutMapping("{id}")
    @Operation(summary = "Atualizar usuário")
    UsuarioResponseDto atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDto usuarioUpdateDto);

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir usuário")
    void excluir(@PathVariable Long id);

    @PatchMapping("{id}/senha")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Alterar senha do usuário")
    void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaDto senhaDto);
}