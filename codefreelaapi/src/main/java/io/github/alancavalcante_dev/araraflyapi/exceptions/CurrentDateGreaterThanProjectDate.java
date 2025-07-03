package io.github.alancavalcante_dev.araraflyapi.exceptions;

public class CurrentDateGreaterThanProjectDate extends RuntimeException {
    public CurrentDateGreaterThanProjectDate(String message) {
        super(message);
    }
}
