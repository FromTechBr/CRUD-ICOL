package com.br.org.icol.icolbackend.dto;

import lombok.Data;

@Data
public class DocenteResponseDTO {
    private Long id;
    private String nomeCompleto;
    private String especializacao;
    private Long usuarioId;
    private String emailUsuario;
}
