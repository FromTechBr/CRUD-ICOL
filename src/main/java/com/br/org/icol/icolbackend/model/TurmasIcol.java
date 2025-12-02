package com.br.org.icol.icolbackend.model;

import com.br.org.icol.icolbackend.enums.StatusTurma;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="turmas_icol")
public class TurmasIcol {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private StatusTurma statusTurma;

    // Relacionamento com Curso
    @ManyToOne
    @JoinColumn(name="curso_id") // Nome da coluna no banco (FK)
    private CursosIcol curso;    // Nome da variável no Java (Objeto)

    // Relacionamento com Docente
    @ManyToOne
    @JoinColumn(name="docente_id") // Nome da coluna no banco (FK para tabela Docentes)
    private DocentesIcol docente;  // Nome da variável no Java (Objeto)

    @Column(nullable=false)
    private int numMaxAlunos;

    @Column(nullable=false)
    private String cronogramaHorarios;
}