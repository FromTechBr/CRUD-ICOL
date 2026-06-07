package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.StatusPresenca;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class FrequenciaRequestDTO {
    @NotNull(message = "O ID da matrícula é obrigatório.")
    private Long matriculaId;

    @NotNull(message = "A data da aula é obrigatória.")
    private LocalDate dataAula;

    @NotNull(message = "O status de presença é obrigatório.")
    private StatusPresenca statusPresenca;

    private String justificativa;
}
