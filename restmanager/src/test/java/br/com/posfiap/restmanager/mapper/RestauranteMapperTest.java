package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.infrastructure.entities.EnderecoEntity;
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteMapperTest {

    private final RestauranteMapper mapper = Mappers.getMapper(RestauranteMapper.class);

    @Test
    void testToRestaurante() {
        RestauranteCreateDto dto = RestauranteCreateDto.builder()
                .nome("Restaurante Teste")
                .login("teste@restaurante.com")
                .senha("senha123")
                .tipoDeCozinha("Italiana")
                .horarioFuncionamento("08:00-22:00")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(EnderecoDto.builder()
                        .rua("Rua Teste")
                        .numero("123")
                        .cidade("São Paulo")
                        .build())
                .usuarioProprietarioId(1L)
                .build();

        Restaurante result = mapper.mapToRestaurante(dto);

        assertNotNull(result);
        assertEquals("Restaurante Teste", result.getNome());
        assertEquals("teste@restaurante.com", result.getLogin());
        assertEquals("senha123", result.getSenha());
        assertEquals("Italiana", result.getTipoDeCozinha());
        assertEquals("08:00-22:00", result.getHorarioFuncionamento());
        assertEquals(TipoUsuario.PROPRIETARIO, result.getTipoUsuario());
        assertNotNull(result.getEndereco());
        assertEquals("Rua Teste", result.getEndereco().getRua());
    }

    @Test
    void testToRestauranteEntity() {
        Restaurante restaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .login("teste@restaurante.com")
                .senha("senha123")
                .tipoDeCozinha("Italiana")
                .horarioFuncionamento("08:00-22:00")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(Endereco.builder()
                        .rua("Rua Teste")
                        .numero("123")
                        .cidade("São Paulo")
                        .build())
                .usuarioProprietario(Usuario.builder().id(1L).build())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        RestauranteEntity result = mapper.mapToRestauranteEntity(restaurante);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Restaurante Teste", result.getNome());
        assertEquals("teste@restaurante.com", result.getLogin());
        assertEquals("senha123", result.getSenha());
        assertEquals("Italiana", result.getTipoDeCozinha());
        assertEquals("08:00-22:00", result.getHorarioFuncionamento());
        assertEquals(TipoUsuario.PROPRIETARIO, result.getTipoUsuario());
        assertNotNull(result.getEndereco());
        assertEquals("Rua Teste", result.getEndereco().getRua());
    }

    @Test
    void testToRestauranteResponseDto() {
        RestauranteEntity entity = RestauranteEntity.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .login("teste@restaurante.com")
                .senha("senha123")
                .tipoDeCozinha("Italiana")
                .horarioFuncionamento("08:00-22:00")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(EnderecoEntity.builder()
                        .rua("Rua Teste")
                        .numero("123")
                        .cidade("São Paulo")
                        .build())
                .usuarios(List.of())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        RestauranteResponseDto result = mapper.mapToRestauranteResponseDto(mapper.mapToRestaurante(entity));

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Restaurante Teste", result.getNome());
        assertEquals("teste@restaurante.com", result.getLogin());
        assertEquals("senha123", result.getSenha());
        assertEquals("Italiana", result.getTipoDeCozinha());
        assertEquals("08:00-22:00", result.getHorarioFuncionamento());
        assertEquals(TipoUsuario.PROPRIETARIO, result.getTipoUsuario());
        assertNotNull(result.getEndereco());
        assertEquals("Rua Teste", result.getEndereco().getRua());
    }
}