package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.model.MatriculaIcol;
import com.br.org.icol.icolbackend.model.AlunosIcol;
import com.br.org.icol.icolbackend.model.TurmasIcol;
import com.br.org.icol.icolbackend.repository.MatriculaIcolRepositorio;
import com.br.org.icol.icolbackend.repository.AlunosIcolRepositorio;
import com.br.org.icol.icolbackend.repository.TurmasIcolRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatriculaIcolService {
    
    private final MatriculaIcolRepositorio repoMatricula;
    private final AlunosIcolRepositorio repoAlunos;
    private final TurmasIcolRepositorio repoTurmas;

    public MatriculaIcolService(MatriculaIcolRepositorio repoMatricula, 
                               AlunosIcolRepositorio repoAlunos,
                               TurmasIcolRepositorio repoTurmas) {
        this.repoMatricula = repoMatricula;
        this.repoAlunos = repoAlunos;
        this.repoTurmas = repoTurmas;
    }

    public List<MatriculaIcol> listar() {
        return repoMatricula.findAll();
    }

    public MatriculaIcol buscar(Long id) {
        return repoMatricula.findById(id)
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Matrícula não encontrada com ID: " + id));
    }

    public MatriculaIcol criar(MatriculaIcol matriculaCadastrar) {
        if (matriculaCadastrar.getAlunoMatrId() == null || matriculaCadastrar.getAlunoMatrId().getId() == null) {
            throw new RequisicaoNaoEncontrada("É obrigatório informar o ID do aluno.");
        }

        if (matriculaCadastrar.getTurmaMatrId() == null || matriculaCadastrar.getTurmaMatrId().getId() == null) {
            throw new RequisicaoNaoEncontrada("É obrigatório informar o ID da turma.");
        }

        AlunosIcol alunoExistente = repoAlunos.findById(matriculaCadastrar.getAlunoMatrId().getId())
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Aluno não encontrado."));

        TurmasIcol turmaExistente = repoTurmas.findById(matriculaCadastrar.getTurmaMatrId().getId())
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Turma não encontrada."));

        matriculaCadastrar.setAlunoMatrId(alunoExistente);
        matriculaCadastrar.setTurmaMatrId(turmaExistente);
        
        return repoMatricula.save(matriculaCadastrar);
    }

    public MatriculaIcol atualizar(Long id, MatriculaIcol matriculaAtualizar) {
        MatriculaIcol existente = buscar(id);
        
        if (matriculaAtualizar.getStatusMatricula() != null) {
            existente.setStatusMatricula(matriculaAtualizar.getStatusMatricula());
        }
        
        return repoMatricula.save(existente);
    }

    public void deletar(Long id) {
        if (!repoMatricula.existsById(id)) {
            throw new RequisicaoNaoEncontrada("Matrícula não encontrada para exclusão.");
        }
        repoMatricula.deleteById(id);
    }
}
