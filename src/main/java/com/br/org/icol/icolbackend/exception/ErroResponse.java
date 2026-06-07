package com.br.org.icol.icolbackend.exception;

import java.time.LocalDateTime;

public record ErroResponse(
    int status,
    String erro,
    String mensagem,
    LocalDateTime timestamp
) {}
