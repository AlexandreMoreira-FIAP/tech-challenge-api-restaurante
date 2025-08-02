package br.com.posfiap.restmanager.infrastructure.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class Logger {

    public static void logRequestController(String descricao) {
        log.debug("Recebendo requisição para {}.", descricao);
    }

    public static <T> void logRequestController(String descricao, T requisicao) {
        log.debug("Recebendo requisição para {}. Dados: {}.", descricao, requisicao);
    }

    public static void logResponseController(String descricao) {
        log.debug("Requisição para {} finalizada.", descricao);
    }

    public static <T> void logResponseController(String descricao, T resposta) {
        log.debug("Resposta da requisição para {}. Dados: {}", descricao, resposta);
    }

    public static void logError(String descricao, Throwable ex) {
        log.error("Erro durante {}: {}", descricao, ex.getMessage(), ex);
    }
}
