package com.br.org.icol.icolbackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AvisoMuralResponseDTO {
    private Long id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataPostagem;
    private Long autorId;
    private String emailAutor;
}
