package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.RestauranteUpdateDto;
import br.com.posfiap.restmanager.dto.UsuarioSimpleDto;
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
class RestauranteMapperTest {

    @Autowired private RestauranteMapper restauranteMapper;

    private RestauranteCreateDto restauranteCreateDto;
    private RestauranteUpdateDto restauranteUpdateDto;
    private Restaurante restaurante;
    private RestauranteEntity restauranteEntity;
    private Endereco endereco;
    private EnderecoDto enderecoDto;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        enderecoDto = EnderecoDto.builder()
                .rua("Rua do Restaurante")
                .numero("456")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01234-567")
                .estado("SP")
                .pais("Brasil")
                .build();

        endereco = Endereco.builder()
                .id(1L)
                .rua("Rua do Restaurante")
                .numero("456")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01234-567")
                .estado("SP")
                .pais("Brasil")
                .build();

        restauranteCreateDto = RestauranteCreateDto.builder()
                .nome("Restaurante Teste")
                .login("restaurante@test.com")
                .senha("senha123")
                .tipoDeCozinha("Italiana")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(enderecoDto)
                .build();

        restauranteUpdateDto = RestauranteUpdateDto.builder()
                .nome("Restaurante Atualizado")
                .login("restaurante.novo@test.com")
                .tipoDeCozinha("Brasileira")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(enderecoDto)
                .build();

        restaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .login("restaurante@test.com")
                .senha("senha123")
                .tipoDeCozinha("Italiana")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .endereco(endereco)
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        restauranteEntity = RestauranteEntity.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .login("restaurante@test.com")
                .senha("senha123")
                .tipoDeCozinha("Italiana")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();

        usuarioEntity = UsuarioEntity.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@test.com")
                .login("joao.silva")
                .senha("senha123")
                .build();
    }

    @Test
    void deveMapearRestauranteCreateDtoParaRestaurante() {
        Restaurante resultado = restauranteMapper.mapToRestaurante(restauranteCreateDto);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertNull(resultado.getDataUltimaAlteracao());
        assertEquals("Restaurante Teste", resultado.getNome());
        assertEquals("restaurante@test.com", resultado.getLogin());
        assertEquals("senha123", resultado.getSenha());
        assertEquals("Italiana", resultado.getTipoDeCozinha());
        assertEquals(TipoUsuario.PROPRIETARIO, resultado.getTipoUsuario());
        assertNotNull(resultado.getEndereco());
        assertEquals("Rua do Restaurante", resultado.getEndereco().getRua());
    }

    @Test
    void deveMapearRestauranteUpdateDtoParaRestaurante() {
        Restaurante resultado = restauranteMapper.mapToRestaurante(restauranteUpdateDto);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertNull(resultado.getSenha());
        assertNull(resultado.getDataUltimaAlteracao());
        assertEquals("Restaurante Atualizado", resultado.getNome());
        assertEquals("restaurante.novo@test.com", resultado.getLogin());
        assertEquals("Brasileira", resultado.getTipoDeCozinha());
        assertEquals(TipoUsuario.FUNCIONARIO, resultado.getTipoUsuario());
        assertNotNull(resultado.getEndereco());
    }

    @Test
    void deveMapearRestauranteParaRestauranteResponseDto() {
        RestauranteResponseDto resultado = restauranteMapper.mapToRestauranteResponseDto(restaurante);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Restaurante Teste", resultado.getNome());
        assertEquals("restaurante@test.com", resultado.getLogin());
        assertEquals("Italiana", resultado.getTipoDeCozinha());
        assertEquals(TipoUsuario.PROPRIETARIO, resultado.getTipoUsuario());
        assertNotNull(resultado.getEndereco());
        assertNotNull(resultado.getDataUltimaAlteracao());
    }

    @Test
    void deveMapearEnderecoDto() {
        Endereco resultado = restauranteMapper.mapToEndereco(enderecoDto);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertEquals("Rua do Restaurante", resultado.getRua());
        assertEquals("456", resultado.getNumero());
        assertEquals("Centro", resultado.getBairro());
        assertEquals("São Paulo", resultado.getCidade());
        assertEquals("01234-567", resultado.getCep());
        assertEquals("SP", resultado.getEstado());
        assertEquals("Brasil", resultado.getPais());
    }

    @Test
    void deveMapearRestauranteParaRestauranteEntity() {
        RestauranteEntity resultado = restauranteMapper.mapToRestauranteEntity(restaurante);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Restaurante Teste", resultado.getNome());
        assertEquals("restaurante@test.com", resultado.getLogin());
        assertEquals("senha123", resultado.getSenha());
        assertEquals("Italiana", resultado.getTipoDeCozinha());
        assertEquals(TipoUsuario.PROPRIETARIO, resultado.getTipoUsuario());
        assertNotNull(resultado.getDataUltimaAlteracao());
    }

    @Test
    void deveMapearRestauranteEntityParaRestaurante() {
        Restaurante resultado = restauranteMapper.mapToRestaurante(restauranteEntity);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Restaurante Teste", resultado.getNome());
        assertEquals("restaurante@test.com", resultado.getLogin());
        assertEquals("senha123", resultado.getSenha());
        assertEquals("Italiana", resultado.getTipoDeCozinha());
        assertEquals(TipoUsuario.PROPRIETARIO, resultado.getTipoUsuario());
        assertNotNull(resultado.getDataUltimaAlteracao());
    }

    @Test
    void deveMapearEnderecoParaEnderecoEntity() {
        EnderecoEntity resultado = restauranteMapper.mapToEnderecoEntity(endereco);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Rua do Restaurante", resultado.getRua());
        assertEquals("456", resultado.getNumero());
        assertEquals("Centro", resultado.getBairro());
        assertEquals("São Paulo", resultado.getCidade());
        assertEquals("01234-567", resultado.getCep());
        assertEquals("SP", resultado.getEstado());
        assertEquals("Brasil", resultado.getPais());
    }

    @Test
    void deveMapearUsuarioEntityParaUsuarioSimpleDto() {
        UsuarioSimpleDto resultado = restauranteMapper.mapToUsuarioSimpleDto(usuarioEntity);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@test.com", resultado.getEmail());
        assertEquals("joao.silva", resultado.getLogin());
    }

    @Test
    void deveRetornarNullQuandoInputForNull() {
        assertNull(restauranteMapper.mapToRestaurante((RestauranteCreateDto) null));
        assertNull(restauranteMapper.mapToRestaurante((RestauranteUpdateDto) null));
        assertNull(restauranteMapper.mapToRestauranteResponseDto(null));
        assertNull(restauranteMapper.mapToEndereco(null));
        assertNull(restauranteMapper.mapToRestauranteEntity(null));
        assertNull(restauranteMapper.mapToRestaurante((RestauranteEntity) null));
        assertNull(restauranteMapper.mapToEnderecoEntity(null));
        assertNull(restauranteMapper.mapToUsuarioSimpleDto(null));
    }
}