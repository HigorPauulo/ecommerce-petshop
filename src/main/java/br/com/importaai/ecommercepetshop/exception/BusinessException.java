package br.com.importaai.ecommercepetshop.exception;

/** Lancada quando uma regra de negocio e violada (resulta em HTTP 409). */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
