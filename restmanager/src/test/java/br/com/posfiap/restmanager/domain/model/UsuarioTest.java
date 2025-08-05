package br.com.posfiap.restmanager.domain.model;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveCriarUsuarioComBuilder() {
        // Arrange
        LocalDateTime agora = LocalDateTime.now();
        Endereco endereco = Endereco.builder()
                .rua("Rua das Flores")
                .numero("123")
                .build();

        // Act
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .endereco(endereco)
                .dataUltimaAlteracao(agora)
                .build();

        // Assert
        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("João Silva", usuario.getNome());
        assertEquals("joao@email.com", usuario.getEmail());
        assertEquals("joao.silva", usuario.getLogin());
        assertEquals("senha123", usuario.getSenha());
        assertEquals(TipoUsuario.CLIENTE, usuario.getTipoUsuario());
        assertEquals(endereco, usuario.getEndereco());
        assertEquals(agora, usuario.getDataUltimaAlteracao());
    }

    @Test
    void deveTestarGettersESetters() {
        // Arrange
        Usuario usuario = Usuario.builder().build();
        LocalDateTime agora = LocalDateTime.now();
        Endereco endereco = Endereco.builder()
                .rua("Rua Augusta")
                .build();
        List<Restaurante> restaurantes = new ArrayList<>();

        // Act
        usuario.setId(2L);
        usuario.setNome("Maria Silva");
        usuario.setEmail("maria@email.com");
        usuario.setLogin("maria.silva");
        usuario.setSenha("senha456");
        usuario.setTipoUsuario(TipoUsuario.PROPRIETARIO);
        usuario.setEndereco(endereco);
        usuario.setRestaurantes(restaurantes);
        usuario.setDataUltimaAlteracao(agora);

        // Assert
        assertEquals(2L, usuario.getId());
        assertEquals("Maria Silva", usuario.getNome());
        assertEquals("maria@email.com", usuario.getEmail());
        assertEquals("maria.silva", usuario.getLogin());
        assertEquals("senha456", usuario.getSenha());
        assertEquals(TipoUsuario.PROPRIETARIO, usuario.getTipoUsuario());
        assertEquals(endereco, usuario.getEndereco());
        assertEquals(restaurantes, usuario.getRestaurantes());
        assertEquals(agora, usuario.getDataUltimaAlteracao());
    }

    @Test
    void deveTestarEqualsEHashCode() {
        // Arrange
        Endereco endereco = Endereco.builder()
                .rua("Rua das Flores")
                .build();

        Usuario usuario1 = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .endereco(endereco)
                .build();

        Usuario usuario2 = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .endereco(endereco)
                .build();

        Usuario usuario3 = Usuario.builder()
                .id(2L)
                .nome("Maria Silva")
                .email("maria@email.com")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        // Assert
        assertEquals(usuario1, usuario2);
        assertNotEquals(usuario1, usuario3);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
        assertNotEquals(usuario1.hashCode(), usuario3.hashCode());
    }

    @Test
    void deveTestarToString() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();

        // Act
        String toString = usuario.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Usuario"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome=João Silva"));
        assertTrue(toString.contains("email=joao@email.com"));
        assertTrue(toString.contains("tipoUsuario=CLIENTE"));
    }

    @Test
    void devePermitirValoresNulos() {
        // Act
        Usuario usuario = Usuario.builder()
                .nome("Teste Usuario")
                .email("teste@email.com")
                .build();

        // Assert
        assertNotNull(usuario);
        assertNull(usuario.getId());
        assertEquals("Teste Usuario", usuario.getNome());
        assertEquals("teste@email.com", usuario.getEmail());
        assertNull(usuario.getLogin());
        assertNull(usuario.getSenha());
        assertNull(usuario.getTipoUsuario());
        assertNull(usuario.getEndereco());
        assertNull(usuario.getRestaurantes());
        assertNull(usuario.getDataUltimaAlteracao());
    }

    @Test
    void deveTestarTipoUsuarioEnum() {
        // Arrange & Act
        Usuario cliente = Usuario.builder()
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();

        Usuario proprietario = Usuario.builder()
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        // Assert
        assertEquals(TipoUsuario.CLIENTE, cliente.getTipoUsuario());
        assertEquals(TipoUsuario.PROPRIETARIO, proprietario.getTipoUsuario());
        assertNotEquals(cliente.getTipoUsuario(), proprietario.getTipoUsuario());
    }
}