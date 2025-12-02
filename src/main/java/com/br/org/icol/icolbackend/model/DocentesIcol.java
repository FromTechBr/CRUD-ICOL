package com.br.org.icol.icolbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="docentes_icol")
public class DocentesIcol {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // Relacionamento 1:1 com Usuário (Login)
    // Um docente tem um usuário de acesso exclusivo
    @OneToOne
    @JoinColumn(name="usuario_id", nullable = false, unique = true)
    private UsuariosIcol usuario;

    @Column(nullable=false)
    private String nomeCompleto;

    // Campo opcional sugerido para diferenciar (Ex: Matemática, TI, Artes)
    @Column(nullable=true)
    private String especializacao; 
}