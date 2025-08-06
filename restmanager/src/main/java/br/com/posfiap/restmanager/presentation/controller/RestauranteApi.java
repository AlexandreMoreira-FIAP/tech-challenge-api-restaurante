package br.com.posfiap.restmanager.presentation.controller;

import br.com.posfiap.restmanager.application.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.application.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.application.dto.RestauranteUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/api/v1/restaurantes")
public interface RestauranteApi {

    @Operation(summary = "Cadastrar um novo restaurante")
    @PostMapping("/{idProprietario}")
    @ResponseStatus(HttpStatus.CREATED)
    RestauranteResponseDto incluir(@PathVariable("idProprietario") Long idProprietario,
                                   @Valid @RequestBody RestauranteCreateDto dto
    );

    @Operation(summary = "Buscar restaurante por ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    RestauranteResponseDto buscarPorId(@PathVariable Long id);

    @PutMapping("/{idRestaurante}/proprietario/{idProprietario}")
    RestauranteResponseDto atualizar(@PathVariable Long idRestaurante,
                                     @PathVariable Long idProprietario,
                                     @RequestBody RestauranteUpdateDto dto
    );


    @Operation(summary = "Excluir um restaurante por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void excluir(@PathVariable Long id);

    @Operation(summary = "Listar restaurantes de um proprietário")
    @GetMapping("/proprietario/{idProprietario}")
    @ResponseStatus(HttpStatus.OK)
    List<RestauranteResponseDto> listarPorProprietario(@PathVariable Long idProprietario);
}
