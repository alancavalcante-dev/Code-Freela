package io.github.alancavalcante_dev.codefreelaapi.exceptions;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}
