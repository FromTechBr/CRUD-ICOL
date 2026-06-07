package com.br.org.icol.icolbackend.dto;

import lombok.Data;

@Data
public class AlunoResponseDTO {
    private Long id;
    private String nomeCompleto;
    private int idade;
    private String cpf;
    private String telefone;
    private String declaSocie;
    private String endereco;
    private Boolean ativo;
    private Long usuarioId;
    private String emailUsuario;
}
