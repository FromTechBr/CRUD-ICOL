package com.br.org.icol.icolbackend.model;

import java.util.List;

import com.br.org.icol.icolbackend.enums.StatusTurma;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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