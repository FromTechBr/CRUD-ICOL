package com.br.org.icol.icolbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.org.icol.icolbackend.model.MatriculaIcol;

@Repository
public interface MatriculaIcolRepositorio extends JpaRepository<MatriculaIcol, Long>{
    boolean existsByAlunoMatrIdAndTurmaMatrId(com.br.org.icol.icolbackend.model.AlunosIcol aluno, com.br.org.icol.icolbackend.model.TurmasIcol turma);
    
    long countByTurmaMatrIdAndStatusMatricula(com.br.org.icol.icolbackend.model.TurmasIcol turma, com.br.org.icol.icolbackend.enums.StatusMatricula status);
}
