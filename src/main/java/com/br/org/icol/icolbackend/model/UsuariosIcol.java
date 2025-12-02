package com.br.org.icol.icolbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import com.br.org.icol.icolbackend.enums.TiposUsuario;

/*● Propósito: Armazenar os dados de login para todos os tipos de
usuários (Alunos, Professores, Coordenadoras).
PRD
PRODUCT REQUIREMENTS DOCUMENT
● Colunas: id (Chave Primária), email (TEXT, único), senha (TEXT),
tipo_usuario (ENUM: 'Aluno', 'Professor', 'Coordenadora').
 */

@Data
@Entity
@Table(name="usuarios_icol")
public class UsuariosIcol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String emailUsuario;
    @Column(nullable=false)
    private String senha;
    @Column(nullable=false)
    private Boolean ativo=true;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TiposUsuario tipoUsuario;
}
