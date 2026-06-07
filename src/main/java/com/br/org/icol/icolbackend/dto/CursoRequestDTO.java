package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.TiposCursos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CursoRequestDTO {
    @NotBlank(message = "O nome do curso é obrigatório.")
    private String nomeCurso;

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @NotBlank(message = "A duração é obrigatória.")
    private String duracao;

    @NotNull(message = "A categoria é obrigatória.")
    private TiposCursos categoria;
}
