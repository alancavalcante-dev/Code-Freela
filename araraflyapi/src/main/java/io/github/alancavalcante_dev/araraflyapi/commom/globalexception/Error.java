package io.github.alancavalcante_dev.araraflyapi.commom.globalexception;

import java.util.List;

public class Error {

    public static GlobalExceptionDTO defaultError(String message, int status, List<String> errors) {
        return new GlobalExceptionDTO(message, status, errors);
    }

    public static GlobalExceptionDTO fieldErrors(String message, int status, List<String> errors) {
        return new GlobalExceptionDTO(message, status, errors);
    }
    public static GlobalExceptionDTO usernameDuplicate(String message, int status, List<String> errors) {
        return new GlobalExceptionDTO(message, status, errors);
    }

    public static GlobalExceptionDTO noPriceField(String message, int status, List<String> errors) {
        return new GlobalExceptionDTO(message, status, errors);
    }


}
