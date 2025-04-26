package io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums;

public enum StateBusiness {
    // regra de negócio quando projeto não começou a ser programado
    OPEN,
    CANCELED,

    // quando o projeto começou a ser programado (negociação concluida com desenvoledor e aceita)
    WORKING,
    FINISHED,
}
