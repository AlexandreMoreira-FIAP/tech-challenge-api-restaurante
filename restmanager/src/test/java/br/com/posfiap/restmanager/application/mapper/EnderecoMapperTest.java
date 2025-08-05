package br.com.posfiap.restmanager.application.mapper;

import br.com.posfiap.restmanager.application.dto.EnderecoDto;
import br.com.posfiap.restmanager.domain.model.Endereco;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.EnderecoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoMapperTest {

    private EnderecoMapper enderecoMapper;

    @BeforeEach
    void setUp() {
        enderecoMapper = Mappers.getMapper(EnderecoMapper.class);
    }

    @Test
    void deveMapearEnderecoDtoParaEndereco() {
        // Arrange
        EnderecoDto dto = EnderecoDto.builder()
                .rua("Rua das Flores")
                .numero("123")
                .complemento("Apt 45")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01234-567")
                .estado("SP")
                .pais("Brasil")
                .build();

        // Act
        Endereco endereco = enderecoMapper.mapToEndereco(dto);

        // Assert
        assertNotNull(endereco);
        assertNull(endereco.getId()); // ID deve ser ignorado conforme mapping
        assertEquals(dto.getRua(), endereco.getRua());
        assertEquals(dto.getNumero(), endereco.getNumero());
        assertEquals(dto.getComplemento(), endereco.getComplemento());
        assertEquals(dto.getBairro(), endereco.getBairro());
        assertEquals(dto.getCidade(), endereco.getCidade());
        assertEquals(dto.getCep(), endereco.getCep());
        assertEquals(dto.getEstado(), endereco.getEstado());
        assertEquals(dto.getPais(), endereco.getPais());
    }

    @Test
    void deveMapearEnderecoParaEnderecoDto() {
        // Arrange
        Endereco endereco = Endereco.builder()
                .id(1L)
                .rua("Rua Augusta")
                .numero("456")
                .complemento("Sala 10")
                .bairro("Consolação")
                .cidade("São Paulo")
                .cep("01305-000")
                .estado("SP")
                .pais("Brasil")
                .build();

        // Act
        EnderecoDto dto = enderecoMapper.mapToEnderecoDto(endereco);

        // Assert
        assertNotNull(dto);
        assertEquals(endereco.getRua(), dto.getRua());
        assertEquals(endereco.getNumero(), dto.getNumero());
        assertEquals(endereco.getComplemento(), dto.getComplemento());
        assertEquals(endereco.getBairro(), dto.getBairro());
        assertEquals(endereco.getCidade(), dto.getCidade());
        assertEquals(endereco.getCep(), dto.getCep());
        assertEquals(endereco.getEstado(), dto.getEstado());
        assertEquals(endereco.getPais(), dto.getPais());
    }

    @Test
    void deveMapearEnderecoParaEnderecoEntity() {
        // Arrange
        Endereco endereco = Endereco.builder()
                .id(1L)
                .rua("Rua Paulista")
                .numero("789")
                .complemento("Cobertura")
                .bairro("Bela Vista")
                .cidade("São Paulo")
                .cep("01310-100")
                .estado("SP")
                .pais("Brasil")
                .build();

        // Act
        EnderecoEntity entity = enderecoMapper.mapToEnderecoEntity(endereco);

        // Assert
        assertNotNull(entity);
        assertEquals(endereco.getId(), entity.getId());
        assertEquals(endereco.getRua(), entity.getRua());
        assertEquals(endereco.getNumero(), entity.getNumero());
        assertEquals(endereco.getComplemento(), entity.getComplemento());
        assertEquals(endereco.getBairro(), entity.getBairro());
        assertEquals(endereco.getCidade(), entity.getCidade());
        assertEquals(endereco.getCep(), entity.getCep());
        assertEquals(endereco.getEstado(), entity.getEstado());
        assertEquals(endereco.getPais(), entity.getPais());
    }

    @Test
    void deveMapearEnderecoEntityParaEndereco() {
        // Arrange
        EnderecoEntity entity = EnderecoEntity.builder()
                .id(2L)
                .rua("Av. Faria Lima")
                .numero("1000")
                .complemento("14º andar")
                .bairro("Itaim Bibi")
                .cidade("São Paulo")
                .cep("04538-132")
                .estado("SP")
                .pais("Brasil")
                .build();

        // Act
        Endereco endereco = enderecoMapper.mapToEndereco(entity);

        // Assert
        assertNotNull(endereco);
        assertEquals(entity.getId(), endereco.getId());
        assertEquals(entity.getRua(), endereco.getRua());
        assertEquals(entity.getNumero(), endereco.getNumero());
        assertEquals(entity.getComplemento(), endereco.getComplemento());
        assertEquals(entity.getBairro(), endereco.getBairro());
        assertEquals(entity.getCidade(), endereco.getCidade());
        assertEquals(entity.getCep(), endereco.getCep());
        assertEquals(entity.getEstado(), endereco.getEstado());
        assertEquals(entity.getPais(), endereco.getPais());
    }

    @Test
    void deveMapearEnderecoDtoComValoresNulos() {
        // Arrange
        EnderecoDto dto = EnderecoDto.builder()
                .rua("Rua Teste")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        // Act
        Endereco endereco = enderecoMapper.mapToEndereco(dto);

        // Assert
        assertNotNull(endereco);
        assertNull(endereco.getId()); // ID deve ser ignorado
        assertEquals("Rua Teste", endereco.getRua());
        assertNull(endereco.getNumero());
        assertNull(endereco.getComplemento());
        assertNull(endereco.getBairro());
        assertEquals("São Paulo", endereco.getCidade());
        assertNull(endereco.getCep());
        assertEquals("SP", endereco.getEstado());
        assertNull(endereco.getPais());
    }
}