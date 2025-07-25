package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioUpdateDto;
import br.com.posfiap.restmanager.dto.RestauranteSimpleDto;
import br.com.posfiap.restmanager.infrastructure.entities.EnderecoEntity;
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioMapperTest {

    @Autowired private UsuarioMapper usuarioMapper;

    private UsuarioCreateDto usuarioCreateDto;
    private UsuarioUpdateDto usuarioUpdateDto;
    private Usuario usuario;
    private UsuarioEntity usuarioEntity;
    private Endereco endereco;
    private EnderecoDto enderecoDto;
    private RestauranteEntity restauranteEntity;

    @BeforeEach
    void setUp() {
        enderecoDto = EnderecoDto.builder()
                .rua("Rua Teste")
                .numero("123")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01234-567")
                .estado("SP")
                .pais("Brasil")
                .build();

        endereco = Endereco.builder()
                .id(1L)
                .rua("Rua Teste")
                .numero("123")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01234-567")
                .estado("SP")
                .pais("Brasil")
                .build();

        usuarioCreateDto = UsuarioCreateDto.builder()
                .nome("João Silva")
                .email("joao@test.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.FUNCIONARIO)
                .endereco(enderecoDto)
                .build();

        usuarioUpdateDto = UsuarioUpdateDto.builder()
                .nome("João Silva Atualizado")
                .email("joao.novo@test.com")
                .login("joao.novo")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(enderecoDto)
                .build();

        usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@test.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.FUNCIONARIO)
                .endereco(endereco)
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        usuarioEntity = UsuarioEntity.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@test.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.FUNCIONARIO.name())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        restauranteEntity = RestauranteEntity.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@test.com")
                .build();
    }

    @Test
    void deveMapearUsuarioCreateDtoParaUsuario() {
        Usuario resultado = usuarioMapper.mapToUsuario(usuarioCreateDto);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertNull(resultado.getDataUltimaAlteracao());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@test.com", resultado.getEmail());
        assertEquals("joao.silva", resultado.getLogin());
        assertEquals("senha123", resultado.getSenha());
        assertEquals(TipoUsuario.FUNCIONARIO, resultado.getTipoUsuario());
        assertNotNull(resultado.getEndereco());
        assertEquals("Rua Teste", resultado.getEndereco().getRua());
    }

    @Test
    void deveMapearUsuarioUpdateDtoParaUsuario() {
        Usuario resultado = usuarioMapper.mapToUsuario(usuarioUpdateDto);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertNull(resultado.getSenha());
        assertNull(resultado.getDataUltimaAlteracao());
        assertEquals("João Silva Atualizado", resultado.getNome());
        assertEquals("joao.novo@test.com", resultado.getEmail());
        assertEquals("joao.novo", resultado.getLogin());
        assertEquals(TipoUsuario.PROPRIETARIO, resultado.getTipoUsuario());
        assertNotNull(resultado.getEndereco());
    }

    @Test
    void deveMapearUsuarioParaUsuarioResponseDto() {
        UsuarioResponseDto resultado = usuarioMapper.mapToUsuarioResponseDto(usuario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@test.com", resultado.getEmail());
        assertEquals("joao.silva", resultado.getLogin());
        assertEquals(TipoUsuario.FUNCIONARIO, resultado.getTipoUsuario());
        assertNotNull(resultado.getEndereco());
        assertNotNull(resultado.getDataUltimaAlteracao());
    }

    @Test
    void deveMapearEnderecoDto() {
        Endereco resultado = usuarioMapper.mapToEndereco(enderecoDto);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertEquals("Rua Teste", resultado.getRua());
        assertEquals("123", resultado.getNumero());
        assertEquals("Centro", resultado.getBairro());
        assertEquals("São Paulo", resultado.getCidade());
        assertEquals("01234-567", resultado.getCep());
        assertEquals("SP", resultado.getEstado());
        assertEquals("Brasil", resultado.getPais());
    }

    @Test
    void deveMapearUsuarioParaUsuarioEntity() {
        UsuarioEntity resultado = usuarioMapper.mapToUsuarioEntity(usuario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@test.com", resultado.getEmail());
        assertEquals("joao.silva", resultado.getLogin());
        assertEquals("senha123", resultado.getSenha());
        assertEquals("FUNCIONARIO", resultado.getTipoUsuario());
        assertNotNull(resultado.getDataUltimaAlteracao());
    }

    @Test
    void deveMapearUsuarioEntityParaUsuario() {
        Usuario resultado = usuarioMapper.mapToUsuario(usuarioEntity);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@test.com", resultado.getEmail());
        assertEquals("joao.silva", resultado.getLogin());
        assertEquals("senha123", resultado.getSenha());
        assertNotNull(resultado.getDataUltimaAlteracao());
    }

    @Test
    void deveMapearEnderecoParaEnderecoEntity() {
        EnderecoEntity resultado = usuarioMapper.mapToEnderecoEntity(endereco);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Rua Teste", resultado.getRua());
        assertEquals("123", resultado.getNumero());
        assertEquals("Centro", resultado.getBairro());
        assertEquals("São Paulo", resultado.getCidade());
        assertEquals("01234-567", resultado.getCep());
        assertEquals("SP", resultado.getEstado());
        assertEquals("Brasil", resultado.getPais());
    }

    @Test
    void deveMapearRestauranteEntityParaRestauranteSimpleDto() {
        RestauranteSimpleDto resultado = usuarioMapper.mapToRestauranteSimpleDto(restauranteEntity);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Restaurante Teste", resultado.getNome());
        assertEquals("Italiana", resultado.getTipoDeCozinha());
        assertEquals("restaurante@test.com", resultado.getLogin());
    }

    @Test
    void deveRetornarNullQuandoInputForNull() {
        assertNull(usuarioMapper.mapToUsuario((UsuarioCreateDto) null));
        assertNull(usuarioMapper.mapToUsuario((UsuarioUpdateDto) null));
        assertNull(usuarioMapper.mapToUsuarioResponseDto(null));
        assertNull(usuarioMapper.mapToEndereco(null));
        assertNull(usuarioMapper.mapToUsuarioEntity(null));
        assertNull(usuarioMapper.mapToUsuario((UsuarioEntity) null));
        assertNull(usuarioMapper.mapToEnderecoEntity(null));
        assertNull(usuarioMapper.mapToRestauranteSimpleDto(null));
    }
}