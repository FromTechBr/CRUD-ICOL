package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.TiposUsuario;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String email;
    private TiposUsuario tipoUsuario;
    private Boolean ativo;
}
