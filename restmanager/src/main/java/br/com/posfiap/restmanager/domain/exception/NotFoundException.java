package br.com.posfiap.restmanager.domain.exception;

public class NotFoundException extends AbstractRestException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
