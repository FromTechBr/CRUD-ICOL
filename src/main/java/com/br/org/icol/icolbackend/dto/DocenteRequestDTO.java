package com.br.org.icol.icolbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DocenteRequestDTO {
    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotBlank(message = "O nome completo é obrigatório.")
    private String nomeCompleto;

    private String especializacao;
}
