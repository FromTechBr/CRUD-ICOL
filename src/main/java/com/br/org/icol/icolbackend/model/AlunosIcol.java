package com.br.org.icol.icolbackend.model;

import jakarta.persistence.*;
import lombok.Data;

//id (Chave Primária), usuario_id (Chave Estrangeira para
//Usuarios.id), nome_completo (TEXT), idade (INTEGER), cpf (TEXT,
//único), telefone (TEXT), declaracao_socioeconomica (TEXT), endereco
//(TEXT).

/*  @ManyToOne
    @JoinColumn(name="usuario_id")
    private UsuariosIcol autorId; */
@Data
@Entity
@Table(name="alunos_icol")
public class AlunosIcol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="usuario")
    private UsuariosIcol usuario;
    
    @Column(nullable=false)
    private String nomeCompleto;
    @Column(nullable=false)
    private int idade;
    @Column(nullable=false, unique=true)
    private String cpf;
    @Column(nullable=false)
    private String telefone;
    @Column(nullable=false)
    private String declaSocie;
    @Column(nullable=false)
    private String endereco;
}
