package br.com.importaai.ecommercepetshop.exception;

/** Lancada quando um recurso solicitado nao existe (resulta em HTTP 404). */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String resource, Long id) {
        return new ResourceNotFoundException(resource + " nao encontrado(a) com id " + id);
    }
}
