package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.SenhaDto;
import br.com.posfiap.restmanager.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.RestauranteUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@RequestMapping("/v1/restaurante")
interface RestauranteApi {

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Incluir restaurante")
    RestauranteResponseDto incluir(@RequestBody @Valid RestauranteCreateDto restauranteCreateDto);

    @GetMapping("{id}")
    @Operation(summary = "Consultar restaurante")
    RestauranteResponseDto buscarPorId(@PathVariable Long id);

    @PutMapping("{id}")
    @Operation(summary = "Atualizar restaurante")
    RestauranteResponseDto atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteUpdateDto restauranteUpdateDto);

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir restaurante")
    void excluir(@PathVariable Long id);

    @PatchMapping("{id}/senha")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Alterar senha do dono do restaurante")
    void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaDto senhaDto);
}