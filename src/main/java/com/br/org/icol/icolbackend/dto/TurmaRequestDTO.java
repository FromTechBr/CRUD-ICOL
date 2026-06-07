package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.StatusTurma;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TurmaRequestDTO {
    @NotNull(message = "O ID do curso é obrigatório.")
    private Long cursoId;

    @NotNull(message = "O ID do docente é obrigatório.")
    private Long docenteId;

    @NotNull(message = "O status da turma é obrigatório.")
    private StatusTurma statusTurma;

    @Positive(message = "O número máximo de alunos deve ser maior que 0.")
    private int numMaxAlunos;

    @NotBlank(message = "O cronograma de horários é obrigatório.")
    private String cronogramaHorarios;
}
