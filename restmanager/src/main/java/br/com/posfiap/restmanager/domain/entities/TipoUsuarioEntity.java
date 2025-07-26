package br.com.posfiap.restmanager.domain.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoUsuarioEntity {
    private Long id;
    private String codigo;
    private String descricao;
}