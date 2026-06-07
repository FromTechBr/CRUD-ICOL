package com.br.org.icol.icolbackend.dto;

import com.br.org.icol.icolbackend.enums.TiposUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequestDTO {
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    @NotNull(message = "O tipo de usuário é obrigatório.")
    private TiposUsuario tipoUsuario;
}
