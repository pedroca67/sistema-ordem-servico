/**
 * Exceção para quando um cliente já existe
 */
package com.assistencia.tecnica.exception;

public class ClienteExistenteException extends RuntimeException {

    public ClienteExistenteException(String message) {
        super(message);
    }
}