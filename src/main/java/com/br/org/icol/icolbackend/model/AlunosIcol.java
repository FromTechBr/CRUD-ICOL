package com.br.org.icol.icolbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@Entity
@Table(name="alunos_icol")
@ToString(exclude="matriculas")
@EqualsAndHashCode(exclude="matriculas")
public class AlunosIcol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id", nullable=false)
    private UsuariosIcol usuario;
    
    @Column(nullable=false)
    private String nomeCompleto;
    @Column(nullable=false)
    private int idade;
    @Column(nullable=false, unique=true)
    private String cpf;
    @Column(nullable=false)
    private String telefone;
    @Column(nullable=false)
    private String declaSocie;
    @Column(nullable=false)
    private String endereco;

    @OneToMany(mappedBy="alunoMatrId", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<MatriculaIcol> matriculas;
}
