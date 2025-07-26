package br.com.posfiap.restmanager.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioEntityTest {

    @Test
    void deveCrearTipoUsuarioComBuilder() {
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder()
                .id(1L)
                .codigo("CLIENTE")
                .descricao("Cliente do restaurante")
                .build();

        assertNotNull(tipoUsuario);
        assertEquals(1L, tipoUsuario.getId());
        assertEquals("CLIENTE", tipoUsuario.getCodigo());
        assertEquals("Cliente do restaurante", tipoUsuario.getDescricao());
    }

    @Test
    void devePermitirAlteracaoDePropriedades() {
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder()
                .codigo("ADMIN")
                .descricao("Administrador")
                .build();

        tipoUsuario.setId(2L);
        tipoUsuario.setCodigo("GERENTE");
        tipoUsuario.setDescricao("Gerente do estabelecimento");

        assertEquals(2L, tipoUsuario.getId());
        assertEquals("GERENTE", tipoUsuario.getCodigo());
        assertEquals("Gerente do estabelecimento", tipoUsuario.getDescricao());
    }

    @Test
    void deveImplementarEqualsEHashCode() {
        TipoUsuarioEntity tipo1 = TipoUsuarioEntity.builder()
                .id(1L)
                .codigo("CLIENTE")
                .descricao("Cliente")
                .build();

        TipoUsuarioEntity tipo2 = TipoUsuarioEntity.builder()
                .id(1L)
                .codigo("CLIENTE")
                .descricao("Cliente")
                .build();

        TipoUsuarioEntity tipo3 = TipoUsuarioEntity.builder()
                .id(2L)
                .codigo("ADMIN")
                .descricao("Admin")
                .build();

        assertEquals(tipo1, tipo2);
        assertEquals(tipo1.hashCode(), tipo2.hashCode());
        assertNotEquals(tipo1, tipo3);
        assertNotEquals(tipo1.hashCode(), tipo3.hashCode());
    }

    @Test
    void deveImplementarToString() {
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder()
                .id(1L)
                .codigo("CLIENTE")
                .descricao("Cliente do restaurante")
                .build();

        String toString = tipoUsuario.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("CLIENTE"));
        assertTrue(toString.contains("Cliente do restaurante"));
    }
}