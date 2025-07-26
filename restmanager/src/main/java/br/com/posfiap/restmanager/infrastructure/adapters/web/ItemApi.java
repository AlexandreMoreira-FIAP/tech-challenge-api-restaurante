package br.com.posfiap.restmanager.infrastructure.adapters.web;


import br.com.posfiap.restmanager.dto.ItemCreateDto;
import br.com.posfiap.restmanager.dto.ItemResponseDto;
import br.com.posfiap.restmanager.dto.ItemUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@RequestMapping("/v1/itens")
interface ItemApi {

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Incluir item")
    ItemResponseDto incluir(@RequestBody @Valid ItemCreateDto itemCreateDto);

    @GetMapping("{id}")
    @Operation(summary = "Consultar item")
    ItemResponseDto buscarPorId(@PathVariable Long id);

    @PutMapping("{id}")
    @Operation(summary = "Atualizar item")
    ItemResponseDto atualizar(@PathVariable Long id, @RequestBody @Valid ItemUpdateDto itemUpdateDto);

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir item")
    void excluir(@PathVariable Long id);


}