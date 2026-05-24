package com.br.org.icol.icolbackend.model;

import com.br.org.icol.icolbackend.enums.StatusTurma;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@Entity
@Table(name="turmas_icol")
@ToString(exclude="matriculas")
@EqualsAndHashCode(exclude="matriculas")
public class TurmasIcol {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private StatusTurma statusTurma;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="curso_id", nullable=false)
    private CursosIcol curso;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="docente_id", nullable=false)
    private DocentesIcol docente;

    @Column(nullable=false)
    private int numMaxAlunos;

    @Column(nullable=false)
    private String cronogramaHorarios;

    @OneToMany(mappedBy="turmaMatrId", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<MatriculaIcol> matriculas;
}