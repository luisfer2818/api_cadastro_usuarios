package com.usuario.services.exceptions;

@SuppressWarnings("serial")
public class ArgumentNotValidException extends RuntimeException {

    public ArgumentNotValidException(String msg) {
        super(msg);
    }
}
