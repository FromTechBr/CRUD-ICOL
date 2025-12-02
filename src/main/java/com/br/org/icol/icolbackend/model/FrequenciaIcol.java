package com.br.org.icol.icolbackend.model;

import java.time.*;

import com.br.org.icol.icolbackend.enums.StatusPresenca;

import jakarta.persistence.*;
import lombok.Data;

/*● Propósito: Registrar a presença e as faltas dos alunos em cada aula.
● Colunas: id (Chave Primária), matricula_id (Chave Estrangeira para
Matriculas.id), data_aula (DATE), status_presenca (ENUM: 'Presente',
'Ausente'), justificativa (TEXT, opcional).
 */

@Data
@Entity
@Table(name="frequencia_icol")
public class FrequenciaIcol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private LocalDate dataAula;

    private String justificativa;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private StatusPresenca statusPresenca;

    @ManyToOne
    @JoinColumn(name="matricula_id")
    private MatriculaIcol matriculaId;
}
