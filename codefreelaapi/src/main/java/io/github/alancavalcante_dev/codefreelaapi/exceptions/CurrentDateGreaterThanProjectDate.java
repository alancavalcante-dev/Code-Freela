package io.github.alancavalcante_dev.codefreelaapi.exceptions;

public class CurrentDateGreaterThanProjectDate extends RuntimeException {
    public CurrentDateGreaterThanProjectDate(String message) {
        super(message);
    }
}
