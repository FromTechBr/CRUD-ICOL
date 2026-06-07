package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.StatusPresenca;
import lombok.Data;
import java.time.LocalDate;

@Data
public class FrequenciaResponseDTO {
    private Long id;
    private LocalDate dataAula;
    private StatusPresenca statusPresenca;
    private String justificativa;
    private Long matriculaId;
}
