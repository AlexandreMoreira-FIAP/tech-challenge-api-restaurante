package br.com.posfiap.restmanager.infrastructure.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerTest {

    private ListAppender<ILoggingEvent> listAppender;
    private ch.qos.logback.classic.Logger logger;

    @BeforeEach
    void setUp() {
        logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        logger.setLevel(Level.DEBUG);
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(listAppender);
    }

    @Test
    void deveLogarRequestControllerSemDados() {
        // Act
        Logger.logRequestController("criar usuário");

        // Assert  
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.DEBUG, logEvent.getLevel());
        assertTrue(logEvent.getFormattedMessage().contains("Recebendo requisição para criar usuário"));
    }

    @Test
    void deveLogarRequestControllerComDados() {
        // Arrange
        String dados = "{'nome': 'João'}";

        // Act
        Logger.logRequestController("criar usuário", dados);

        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.DEBUG, logEvent.getLevel());
        assertTrue(logEvent.getFormattedMessage().contains("Recebendo requisição para criar usuário"));
        assertTrue(logEvent.getFormattedMessage().contains(dados));
    }

    @Test
    void deveLogarResponseControllerSemDados() {
        // Act
        Logger.logResponseController("criar usuário");

        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.DEBUG, logEvent.getLevel());
        assertTrue(logEvent.getFormattedMessage().contains("Requisição para criar usuário finalizada"));
    }

    @Test
    void deveLogarResponseControllerComDados() {
        // Arrange
        String resposta = "{'id': 1, 'nome': 'João'}";

        // Act
        Logger.logResponseController("criar usuário", resposta);

        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.DEBUG, logEvent.getLevel());
        assertTrue(logEvent.getFormattedMessage().contains("Resposta da requisição para criar usuário"));
        assertTrue(logEvent.getFormattedMessage().contains(resposta));
    }

    @Test
    void deveLogarError() {
        // Arrange
        Exception ex = new RuntimeException("Erro de teste");

        // Act
        Logger.logError("processar requisição", ex);

        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.ERROR, logEvent.getLevel());
        assertTrue(logEvent.getFormattedMessage().contains("Erro durante processar requisição"));
        assertTrue(logEvent.getFormattedMessage().contains("Erro de teste"));
    }
}