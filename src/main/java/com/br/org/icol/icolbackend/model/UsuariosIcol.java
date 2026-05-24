package com.br.org.icol.icolbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import com.br.org.icol.icolbackend.enums.TiposUsuario;
import java.util.List;

@Data
@Entity
@Table(name="usuarios_icol")
@ToString(exclude={"alunos", "docentes", "avisos"})
@EqualsAndHashCode(exclude={"alunos", "docentes", "avisos"})
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

    @OneToMany(mappedBy="usuario", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<AlunosIcol> alunos;

    @OneToOne(mappedBy="usuario", cascade=CascadeType.ALL, orphanRemoval=true)
    private DocentesIcol docentes;

    @OneToMany(mappedBy="autorId", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<AvisosMuralIcol> avisos;
}
