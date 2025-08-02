package br.com.posfiap.restmanager.domain.exception;

public class AuthenticationException extends AbstractRestException {
    public AuthenticationException(String msg) {
        super(msg);
    }
}
