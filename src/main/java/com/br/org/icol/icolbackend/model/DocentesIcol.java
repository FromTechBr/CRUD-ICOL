package com.br.org.icol.icolbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@Entity
@Table(name="docentes_icol")
@ToString(exclude="turmas")
@EqualsAndHashCode(exclude="turmas")
public class DocentesIcol {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id", nullable=false, unique=true)
    private UsuariosIcol usuario;

    @Column(nullable=false)
    private String nomeCompleto;

    @Column(nullable=true)
    private String especializacao;

    @OneToMany(mappedBy="docente", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<TurmasIcol> turmas;
}