package br.com.posfiap.restmanager.domain.exception;

public class UnauthorizedException extends AbstractRestException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
