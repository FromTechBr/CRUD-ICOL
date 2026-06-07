package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.TiposCursos;
import lombok.Data;

@Data
public class CursoResponseDTO {
    private Long id;
    private String nomeCurso;
    private String descricao;
    private String duracao;
    private TiposCursos categoria;
    private Boolean ativo;
}
