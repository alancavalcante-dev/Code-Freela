package io.github.alancavalcante_dev.codefreelaapi.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class GlobalExceptionDTO {
    String message;
    int status;
    List<String> errors;

}
