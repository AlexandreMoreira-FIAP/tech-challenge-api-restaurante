package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.infrastructure.entities.EnderecoEntity;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private final UsuarioMapper mapper = Mappers.getMapper(UsuarioMapper.class);

    @Test
    void testMapToUsuario() {
        UsuarioCreateDto dto = UsuarioCreateDto.builder()
                .nome("João Silva")
                .email("joao@teste.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .endereco(EnderecoDto.builder()
                        .rua("Rua Teste")
                        .numero("123")
                        .cidade("São Paulo")
                        .build())
                .build();

        Usuario result = mapper.mapToUsuario(dto);

        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
        assertEquals("joao@teste.com", result.getEmail());
        assertEquals("joao.silva", result.getLogin());
        // UsuarioResponseDto não expõe senha por segurança
        assertEquals(TipoUsuario.CLIENTE, result.getTipoUsuario());
        assertNotNull(result.getEndereco());
        assertEquals("Rua Teste", result.getEndereco().getRua());
    }

    @Test
    void testMapToUsuarioEntity() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@teste.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .endereco(Endereco.builder()
                        .rua("Rua Teste")
                        .numero("123")
                        .cidade("São Paulo")
                        .build())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        UsuarioEntity result = mapper.mapToUsuarioEntity(usuario);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João Silva", result.getNome());
        assertEquals("joao@teste.com", result.getEmail());
        assertEquals("joao.silva", result.getLogin());
        // UsuarioResponseDto não expõe senha por segurança
        assertEquals("CLIENTE", result.getTipoUsuario());
        assertNotNull(result.getEndereco());
        assertEquals("Rua Teste", result.getEndereco().getRua());
    }

    @Test
    void testMapToUsuarioResponseDto() {
        UsuarioEntity entity = UsuarioEntity.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@teste.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario("CLIENTE")
                .endereco(EnderecoEntity.builder()
                        .rua("Rua Teste")
                        .numero("123")
                        .cidade("São Paulo")
                        .build())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        UsuarioResponseDto result = mapper.mapToUsuarioResponseDto(mapper.mapToUsuario(entity));

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João Silva", result.getNome());
        assertEquals("joao@teste.com", result.getEmail());
        assertEquals("joao.silva", result.getLogin());
        // UsuarioResponseDto não expõe senha por segurança
        assertEquals(TipoUsuario.CLIENTE, result.getTipoUsuario());
        assertNotNull(result.getEndereco());
        assertEquals("Rua Teste", result.getEndereco().getRua());
    }
}