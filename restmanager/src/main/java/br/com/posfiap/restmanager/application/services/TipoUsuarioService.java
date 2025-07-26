package br.com.posfiap.restmanager.application.services;

import br.com.posfiap.restmanager.domain.entities.TipoUsuarioEntity;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.ports.TipoUsuarioRepositoryPort;
import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoUsuarioService {

    private final TipoUsuarioRepositoryPort tipoUsuarioRepository;

    public List<TipoUsuarioDto> listarTodos() {
        var tiposFromDb = tipoUsuarioRepository.findAll();
        if (tiposFromDb.isEmpty()) {
            return Arrays.stream(TipoUsuario.values())
                    .map(tipo -> new TipoUsuarioDto(
                            null,
                            tipo.name(),
                            getDescricao(tipo)
                    ))
                    .toList();
        }
        return tiposFromDb.stream()
                .map(this::toDto)
                .toList();
    }

    public TipoUsuarioDto buscarPorId(Long id) {
        var tipoUsuario = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de usuário não encontrado com ID: " + id));
        return toDto(tipoUsuario);
    }

    public TipoUsuarioDto criar(TipoUsuarioDto dto) {
        if (tipoUsuarioRepository.existsByCodigo(dto.codigo())) {
            throw new BusinessException("Já existe um tipo de usuário com o código: " + dto.codigo());
        }
        
        var tipoUsuario = TipoUsuarioEntity.builder()
                .codigo(dto.codigo())
                .descricao(dto.descricao())
                .build();
        
        var saved = tipoUsuarioRepository.save(tipoUsuario);
        return toDto(saved);
    }

    public TipoUsuarioDto atualizar(Long id, TipoUsuarioDto dto) {
        var existing = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de usuário não encontrado com ID: " + id));
        
        if (!existing.getCodigo().equals(dto.codigo()) && tipoUsuarioRepository.existsByCodigo(dto.codigo())) {
            throw new BusinessException("Já existe um tipo de usuário com o código: " + dto.codigo());
        }
        
        var updated = TipoUsuarioEntity.builder()
                .id(id)
                .codigo(dto.codigo())
                .descricao(dto.descricao())
                .build();
        
        var saved = tipoUsuarioRepository.save(updated);
        return toDto(saved);
    }

    public void deletar(Long id) {
        if (!tipoUsuarioRepository.findById(id).isPresent()) {
            throw new NotFoundException("Tipo de usuário não encontrado com ID: " + id);
        }
        tipoUsuarioRepository.deleteById(id);
    }

    private TipoUsuarioDto toDto(TipoUsuarioEntity entity) {
        return new TipoUsuarioDto(
                entity.getId(),
                entity.getCodigo(),
                entity.getDescricao()
        );
    }

    private String getDescricao(TipoUsuario tipo) {
        return switch (tipo) {
            case CLIENTE -> "Cliente do restaurante";
            case PROPRIETARIO -> "Proprietário do restaurante";
        };
    }
}