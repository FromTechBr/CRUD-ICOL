package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.StatusTurma;
import lombok.Data;

@Data
public class TurmaResponseDTO {
    private Long id;
    private StatusTurma statusTurma;
    private int numMaxAlunos;
    private String cronogramaHorarios;
    private Long cursoId;
    private String nomeCurso;
    private Long docenteId;
    private String nomeDocente;
}
