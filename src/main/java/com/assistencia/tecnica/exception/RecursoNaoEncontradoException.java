/**
 * Exceção para recursos não encontrados
 */
package com.assistencia.tecnica.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}