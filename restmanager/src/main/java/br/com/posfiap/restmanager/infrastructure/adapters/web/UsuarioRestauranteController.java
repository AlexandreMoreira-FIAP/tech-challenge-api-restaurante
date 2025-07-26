package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.UsuarioRestauranteDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.service.UsuarioRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/usuario-restaurante")
@RequiredArgsConstructor
@Tag(name = "Usuario-Restaurante", description = "Gerenciamento do relacionamento entre usuários e restaurantes")
public class UsuarioRestauranteController {

    private final UsuarioRestauranteService usuarioRestauranteService;

    @PostMapping("/associar")
    @Operation(summary = "Associar usuário a restaurante")
    public ResponseEntity<Void> associarUsuarioRestaurante(@Valid @RequestBody UsuarioRestauranteDto dto) {
        usuarioRestauranteService.associarUsuarioRestaurante(dto.getUsuarioId(), dto.getRestauranteId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/desassociar")
    @Operation(summary = "Desassociar usuário de restaurante")
    public ResponseEntity<Void> desassociarUsuarioRestaurante(@Valid @RequestBody UsuarioRestauranteDto dto) {
        usuarioRestauranteService.desassociarUsuarioRestaurante(dto.getUsuarioId(), dto.getRestauranteId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/{usuarioId}/restaurantes")
    @Operation(summary = "Listar restaurantes de um usuário")
    public ResponseEntity<List<RestauranteResponseDto>> listarRestaurantesDoUsuario(@PathVariable Long usuarioId) {
        List<RestauranteResponseDto> restaurantes = usuarioRestauranteService.listarRestaurantesDoUsuario(usuarioId);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/restaurante/{restauranteId}/usuarios")
    @Operation(summary = "Listar usuários de um restaurante")
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuariosDoRestaurante(@PathVariable Long restauranteId) {
        List<UsuarioResponseDto> usuarios = usuarioRestauranteService.listarUsuariosDoRestaurante(restauranteId);
        return ResponseEntity.ok(usuarios);
    }
}