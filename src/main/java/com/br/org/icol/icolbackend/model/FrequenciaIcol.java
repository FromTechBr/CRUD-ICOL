package com.br.org.icol.icolbackend.model;

import java.time.*;

import com.br.org.icol.icolbackend.enums.StatusPresenca;

import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="matricula_id", nullable=false)
    private MatriculaIcol matriculaId;
}
