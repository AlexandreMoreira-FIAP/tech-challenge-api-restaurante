package br.com.posfiap.restmanager.application.services;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TipoUsuarioService {

    public List<TipoUsuarioDto> listarTodos() {
        return Arrays.stream(TipoUsuario.values())
                .map(this::mapToDto)
                .toList();
    }

    private TipoUsuarioDto mapToDto(TipoUsuario tipoUsuario) {
        return new TipoUsuarioDto(
                tipoUsuario.name(),
                getDescricao(tipoUsuario)
        );
    }

    private String getDescricao(TipoUsuario tipoUsuario) {
        return switch (tipoUsuario) {
            case CLIENTE -> "Cliente do restaurante";
            case PROPRIETARIO -> "Proprietário do restaurante";
        };
    }
}