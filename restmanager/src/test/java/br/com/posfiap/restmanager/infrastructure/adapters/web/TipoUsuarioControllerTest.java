package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.TipoUsuarioDto;
import br.com.posfiap.restmanager.application.services.TipoUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipoUsuarioController.class)
class TipoUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TipoUsuarioService tipoUsuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void deveListarTodosOsTiposDeUsuario() throws Exception {
        List<TipoUsuarioDto> tiposEsperados = Arrays.asList(
            new TipoUsuarioDto("CLIENTE", "Cliente do restaurante"),
            new TipoUsuarioDto("PROPRIETARIO", "Proprietário do restaurante")
        );

        when(tipoUsuarioService.listarTodos()).thenReturn(tiposEsperados);

        mockMvc.perform(get("/v1/tipos-usuario"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].codigo").value("CLIENTE"))
                .andExpect(jsonPath("$[0].descricao").value("Cliente do restaurante"))
                .andExpect(jsonPath("$[1].codigo").value("PROPRIETARIO"))
                .andExpect(jsonPath("$[1].descricao").value("Proprietário do restaurante"));
    }
}