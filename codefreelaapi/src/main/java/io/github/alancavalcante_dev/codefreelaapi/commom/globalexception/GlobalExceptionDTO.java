package io.github.alancavalcante_dev.codefreelaapi.commom.globalexception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GlobalExceptionDTO {
    String message;
    int status;
    List<String> errors;

}
