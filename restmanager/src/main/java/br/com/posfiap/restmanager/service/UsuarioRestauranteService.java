package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.Restaurante;
import br.com.posfiap.restmanager.domain.Usuario;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.entity.RestauranteEntity;
import br.com.posfiap.restmanager.entity.UsuarioEntity;
import br.com.posfiap.restmanager.error.NotFoundException;
import br.com.posfiap.restmanager.mapper.RestauranteMapper;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.repository.RestauranteJpaRepository;
import br.com.posfiap.restmanager.repository.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioRestauranteService {

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final RestauranteJpaRepository restauranteJpaRepository;
    private final UsuarioMapper usuarioMapper;
    private final RestauranteMapper restauranteMapper;

    public void associarUsuarioRestaurante(Long usuarioId, Long restauranteId) {
        UsuarioEntity usuario = usuarioJpaRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        RestauranteEntity restaurante = restauranteJpaRepository.findById(restauranteId)
                .orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));

        if (!restaurante.getUsuarios().contains(usuario)) {
            restaurante.getUsuarios().add(usuario);
            restauranteJpaRepository.save(restaurante);
        }
    }

    public void desassociarUsuarioRestaurante(Long usuarioId, Long restauranteId) {
        UsuarioEntity usuario = usuarioJpaRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        RestauranteEntity restaurante = restauranteJpaRepository.findById(restauranteId)
                .orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));

        restaurante.getUsuarios().remove(usuario);
        restauranteJpaRepository.save(restaurante);
    }

    @Transactional(readOnly = true)
    public List<RestauranteResponseDto> listarRestaurantesDoUsuario(Long usuarioId) {
        UsuarioEntity usuario = usuarioJpaRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return usuario.getRestaurantes().stream()
                .map(restaurante -> restauranteMapper.mapToRestauranteResponseDto(
                        restauranteMapper.mapToRestaurante(restaurante)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> listarUsuariosDoRestaurante(Long restauranteId) {
        RestauranteEntity restaurante = restauranteJpaRepository.findById(restauranteId)
                .orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));

        return restaurante.getUsuarios().stream()
                .map(usuario -> usuarioMapper.mapToUsuarioResponseDto(
                        usuarioMapper.mapToUsuario(usuario)))
                .collect(Collectors.toList());
    }
}