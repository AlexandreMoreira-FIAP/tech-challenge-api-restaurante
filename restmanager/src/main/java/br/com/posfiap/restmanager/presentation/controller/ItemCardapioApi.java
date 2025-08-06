package br.com.posfiap.restmanager.presentation.controller;

import br.com.posfiap.restmanager.application.dto.ItemCardapioCreateDto;
import br.com.posfiap.restmanager.application.dto.ItemCardapioResponseDto;
import br.com.posfiap.restmanager.application.dto.ItemCardapioUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/api/v1/itens-cardapio")
public interface ItemCardapioApi {

    @Operation(summary = "Cadastrar um novo item no cardápio")
    @PostMapping("/{idRestaurante}")
    @ResponseStatus(HttpStatus.CREATED)
    ItemCardapioResponseDto incluir(@PathVariable("idRestaurante") Long idRestaurante,
                                    @Valid @RequestBody ItemCardapioCreateDto dto);

    @Operation(summary = "Buscar item do cardápio por ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ItemCardapioResponseDto buscarPorId(@PathVariable Long id);

    @PutMapping("/{idItem}/restaurante/{idRestaurante}")
    ItemCardapioResponseDto atualizar(@PathVariable Long idItem,
                                      @PathVariable Long idRestaurante,
                                      @RequestBody ItemCardapioUpdateDto dto);

    @Operation(summary = "Excluir item do cardápio por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void excluir(@PathVariable Long id);

    @Operation(summary = "Listar itens do cardápio de um restaurante")
    @GetMapping("/restaurante/{idRestaurante}")
    @ResponseStatus(HttpStatus.OK)
    List<ItemCardapioResponseDto> listarPorRestaurante(@PathVariable Long idRestaurante);
}
