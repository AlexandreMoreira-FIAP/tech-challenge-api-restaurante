package br.com.posfiap.restmanager.application.mapper;

import br.com.posfiap.restmanager.application.dto.RestauranteResumoDto;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EnderecoMapper.class)
public interface RestauranteResumoMapper {
    RestauranteResumoDto toResumoDto(Restaurante restaurante);
}

