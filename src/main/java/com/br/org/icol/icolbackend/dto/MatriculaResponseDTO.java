package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.StatusMatricula;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MatriculaResponseDTO {
    private Long id;
    private LocalDate dataMatricula;
    private StatusMatricula statusMatricula;
    private Long alunoId;
    private String nomeAluno;
    private Long turmaId;
}
