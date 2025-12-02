package com.br.org.icol.icolbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.*;
import com.br.org.icol.icolbackend.enums.StatusMatricula;

/*● Propósito: Associar um aluno a uma turma específica, formalizando a
inscrição.
● Colunas: id (Chave Primária), aluno_id (Chave Estrangeira para
Alunos.id), turma_id (Chave Estrangeira para Turmas.id), data_matricula
(DATE), status (ENUM: 'Pendente', 'Aprovada', 'Recusada').
 */

@Data
@Entity
@Table(name="matricula_icol")
public class MatriculaIcol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private LocalDate dataMatricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private StatusMatricula statusMatricula;

    @ManyToOne
    @JoinColumn(name="aluno_id")
    private AlunosIcol alunoMatrId;

    @ManyToOne
    @JoinColumn(name="turma_id")
    private TurmasIcol turmaMatrId; 
}
