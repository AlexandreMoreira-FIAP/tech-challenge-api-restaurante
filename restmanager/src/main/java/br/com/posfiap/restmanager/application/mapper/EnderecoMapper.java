package br.com.posfiap.restmanager.application.mapper;

import br.com.posfiap.restmanager.domain.model.Endereco;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.EnderecoEntity;
import br.com.posfiap.restmanager.application.dto.EnderecoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco mapToEndereco(EnderecoDto enderecoDto);

    EnderecoDto mapToEnderecoDto(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    EnderecoEntity mapToEnderecoEntity(Endereco endereco);

    Endereco mapToEndereco(EnderecoEntity enderecoEntity);
}
