package com.br.org.icol.icolbackend.model;
import com.br.org.icol.icolbackend.enums.TiposCursos;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="cursos_icol")
public class CursosIcol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nomeCurso;
    @Column(nullable=false)
    private String descricao;
    @Column(nullable=false)
    private String duracao;
    @Column(nullable=false)
    private Boolean ativo=true;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TiposCursos categoria;
    
}
