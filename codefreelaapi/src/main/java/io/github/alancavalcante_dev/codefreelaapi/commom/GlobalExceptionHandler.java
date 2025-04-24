package io.github.alancavalcante_dev.codefreelaapi.commom;

import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.GlobalExceptionDTO;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalExceptionDTO> handleGenericException(RuntimeException ex) {
        GlobalExceptionDTO exeption = Error.defaultError(
                "Aconteceu um erro, entre em contato com o Administrador!", HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(ex.getMessage()));
        return ResponseEntity.status(exeption.getStatus()).body(exeption);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionDTO> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .toList();

        GlobalExceptionDTO exeption = Error.fieldErrors("Erro de validação de campo", HttpStatus.BAD_REQUEST.value(), errors);
        return ResponseEntity.status(exeption.getStatus()).body(exeption);
    }

    @ExceptionHandler(UsernameDuplicadoExeption.class)
    public ResponseEntity<GlobalExceptionDTO> handleUsernameDuplicateException(UsernameDuplicadoExeption ex) {
        GlobalExceptionDTO exeption = Error.usernameDuplicate(
                "Username duplicado", HttpStatus.CONFLICT.value(), List.of(ex.getMessage()));
        return ResponseEntity.status(exeption.getStatus()).body(exeption);
    }

    @ExceptionHandler(CpfExistsException.class)
    public ResponseEntity<GlobalExceptionDTO> handlerCpfExistsException(CpfExistsException ex) {
        GlobalExceptionDTO exeption = Error.usernameDuplicate(
                "CPF já existente", HttpStatus.CONFLICT.value(), List.of(ex.getMessage()));
        return ResponseEntity.status(exeption.getStatus()).body(exeption);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<GlobalExceptionDTO> handlerEmailExistsException(EmailExistsException ex) {
        GlobalExceptionDTO exeption = Error.usernameDuplicate(
                "Email já existente", HttpStatus.CONFLICT.value(), List.of(ex.getMessage()));
        return ResponseEntity.status(exeption.getStatus()).body(exeption);
    }

    @ExceptionHandler(SomeValueMustBeFilled.class)
    public ResponseEntity<GlobalExceptionDTO> handlerSomeValueMustBeFilled(SomeValueMustBeFilled ex) {
        GlobalExceptionDTO exeption = Error.noPriceField(
                "Nenhum campo de preço preenchido", HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage()));
        return ResponseEntity.status(exeption.getStatus()).body(exeption);
    }

    @ExceptionHandler(CurrentDateGreaterThanProjectDate.class)
    public ResponseEntity<GlobalExceptionDTO> handlerCurrentDateGreaterThanProjectDate(CurrentDateGreaterThanProjectDate ex) {
        GlobalExceptionDTO exeption = Error.defaultError(
                "Data do projeto menor que a data atual", HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage()));
        return ResponseEntity.status(exeption.getStatus()).body(exeption);
    }


}

