package br.com.posfiap.restmanager.infrastructure.adapters.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigurationTest {

    @Test
    void deveInstanciarConfiguracaoOpenApi() {
        OpenApiConfiguration config = new OpenApiConfiguration();
        assertNotNull(config);
    }

    @Test
    void deveConterAnotacaoOpenAPIDefinition() {
        Class<OpenApiConfiguration> configClass = OpenApiConfiguration.class;
        
        assertTrue(configClass.isAnnotationPresent(OpenAPIDefinition.class));
        
        OpenAPIDefinition annotation = configClass.getAnnotation(OpenAPIDefinition.class);
        assertNotNull(annotation);
        
        assertEquals("API para gerenciamento de informações de usuários", annotation.info().title());
        assertEquals("1.0.0", annotation.info().version());
        assertEquals("Componente responsável por gerenciar informações de usuários", annotation.info().description());
        assertEquals("/", annotation.servers()[0].url());
    }

    @Test
    void deveConterAnotacaoConfiguration() {
        Class<OpenApiConfiguration> configClass = OpenApiConfiguration.class;
        
        assertTrue(configClass.isAnnotationPresent(org.springframework.context.annotation.Configuration.class));
    }
}