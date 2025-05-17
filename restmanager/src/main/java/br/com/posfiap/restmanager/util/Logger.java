package br.com.posfiap.restmanager.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class Logger {

    public static <T> void logRequestController(String descricao, T requisicao) {

        log.info("Recebendo requisição para {}. Dados: {}.", descricao, requisicao);
    }

    public static <T> void logResponseController(String descricao, T resposta) {

        log.info("Resposta da requisição para {}. Dados: {}", descricao, resposta);
    }
}
