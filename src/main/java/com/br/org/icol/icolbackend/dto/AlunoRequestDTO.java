package com.br.org.icol.icolbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlunoRequestDTO {
    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotBlank(message = "O nome completo é obrigatório.")
    private String nomeCompleto;

    private int idade;

    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @NotBlank(message = "A declaração socioeconômica é obrigatória.")
    private String declaSocie;

    @NotBlank(message = "O endereço é obrigatório.")
    private String endereco;
}
