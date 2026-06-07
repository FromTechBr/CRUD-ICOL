package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.StatusMatricula;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MatriculaRequestDTO {
    @NotNull(message = "O ID do aluno é obrigatório.")
    private Long alunoId;

    @NotNull(message = "O ID da turma é obrigatório.")
    private Long turmaId;

    @NotNull(message = "O status da matrícula é obrigatório.")
    private StatusMatricula statusMatricula;
}
