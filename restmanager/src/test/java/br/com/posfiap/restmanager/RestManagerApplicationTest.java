package br.com.posfiap.restmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestManagerApplicationTest {

    @Test
    void deveConterAnotacaoSpringBootApplication() {
        Class<RestManagerApplication> appClass = RestManagerApplication.class;
        
        assertTrue(appClass.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    void deveInstanciarClasseAplicacao() {
        RestManagerApplication app = new RestManagerApplication();
        assertNotNull(app);
    }
}