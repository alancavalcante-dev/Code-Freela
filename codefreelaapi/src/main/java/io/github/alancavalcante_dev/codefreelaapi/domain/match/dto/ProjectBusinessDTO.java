package io.github.alancavalcante_dev.codefreelaapi.domain.match.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProjectBusinessDTO {

    @NotNull
    @NotBlank
    String idProject;

    boolean confirm = false;
}
