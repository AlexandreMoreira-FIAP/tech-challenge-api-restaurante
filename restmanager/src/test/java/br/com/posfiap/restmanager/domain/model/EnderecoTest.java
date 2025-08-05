package br.com.posfiap.restmanager.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {

    @Test
    void devecriarEnderecoComBuilder() {
        // Act
        Endereco endereco = Endereco.builder()
                .id(1L)
                .rua("Rua das Flores")
                .numero("123")
                .complemento("Apt 45")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01234-567")
                .estado("SP")
                .pais("Brasil")
                .build();

        // Assert
        assertNotNull(endereco);
        assertEquals(1L, endereco.getId());
        assertEquals("Rua das Flores", endereco.getRua());
        assertEquals("123", endereco.getNumero());
        assertEquals("Apt 45", endereco.getComplemento());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("São Paulo", endereco.getCidade());
        assertEquals("01234-567", endereco.getCep());
        assertEquals("SP", endereco.getEstado());
        assertEquals("Brasil", endereco.getPais());
    }

    @Test
    void deveTestarGettersESetters() {
        // Arrange
        Endereco endereco = Endereco.builder().build();

        // Act
        endereco.setId(2L);
        endereco.setRua("Rua Augusta");
        endereco.setNumero("456");
        endereco.setComplemento("Sala 10");
        endereco.setBairro("Consolação");
        endereco.setCidade("São Paulo");
        endereco.setCep("01305-000");
        endereco.setEstado("SP");
        endereco.setPais("Brasil");

        // Assert
        assertEquals(2L, endereco.getId());
        assertEquals("Rua Augusta", endereco.getRua());
        assertEquals("456", endereco.getNumero());
        assertEquals("Sala 10", endereco.getComplemento());
        assertEquals("Consolação", endereco.getBairro());
        assertEquals("São Paulo", endereco.getCidade());
        assertEquals("01305-000", endereco.getCep());
        assertEquals("SP", endereco.getEstado());
        assertEquals("Brasil", endereco.getPais());
    }

    @Test
    void deveTestarEqualsEHashCode() {
        // Arrange
        Endereco endereco1 = Endereco.builder()
                .id(1L)
                .rua("Rua das Flores")
                .numero("123")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        Endereco endereco2 = Endereco.builder()
                .id(1L)
                .rua("Rua das Flores")
                .numero("123")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        Endereco endereco3 = Endereco.builder()
                .id(2L)
                .rua("Rua Augusta")
                .numero("456")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        // Assert
        assertEquals(endereco1, endereco2);
        assertNotEquals(endereco1, endereco3);
        assertEquals(endereco1.hashCode(), endereco2.hashCode());
        assertNotEquals(endereco1.hashCode(), endereco3.hashCode());
    }

    @Test
    void deveTestarToString() {
        // Arrange
        Endereco endereco = Endereco.builder()
                .id(1L)
                .rua("Rua das Flores")
                .numero("123")
                .build();

        // Act
        String toString = endereco.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Endereco"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("rua=Rua das Flores"));
        assertTrue(toString.contains("numero=123"));
    }

    @Test
    void devePermitirValoresNulos() {
        // Act
        Endereco endereco = Endereco.builder()
                .rua("Rua Teste")
                .cidade("São Paulo")
                .build();

        // Assert
        assertNotNull(endereco);
        assertNull(endereco.getId());
        assertEquals("Rua Teste", endereco.getRua());
        assertNull(endereco.getNumero());
        assertNull(endereco.getComplemento());
        assertNull(endereco.getBairro());
        assertEquals("São Paulo", endereco.getCidade());
        assertNull(endereco.getCep());
        assertNull(endereco.getEstado());
        assertNull(endereco.getPais());
    }
}