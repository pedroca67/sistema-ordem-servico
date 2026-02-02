package com.assistencia.tecnica.exception;

/**
 * Exceção para quando um usuário já existe
 */
public class UsuarioExistenteException extends RuntimeException {

    public UsuarioExistenteException(String message) {
        super(message);
    }
}