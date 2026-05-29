package com.br.org.icol.icolbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.model.CursosIcol;
import com.br.org.icol.icolbackend.model.DocentesIcol;
import com.br.org.icol.icolbackend.model.TurmasIcol;
import com.br.org.icol.icolbackend.repository.CursosIcolRepositorio;
import com.br.org.icol.icolbackend.repository.DocentesIcolRepositorio;
import com.br.org.icol.icolbackend.repository.TurmasIcolRepositorio;

@Service
public class TurmasIcolService {
    private final TurmasIcolRepositorio repoTurmas;
    private final DocentesIcolRepositorio repoDocentes;
    private final CursosIcolRepositorio repoCursos;

    public TurmasIcolService(TurmasIcolRepositorio repoTurmas, DocentesIcolRepositorio repoDocentes, CursosIcolRepositorio repoCursos){
        this.repoDocentes=repoDocentes;
        this.repoCursos=repoCursos;
        this.repoTurmas=repoTurmas;
    }

    public List<TurmasIcol> listar(){
        return repoTurmas.findAll();
    }

    public TurmasIcol buscar(Long id){
        return repoTurmas.findById(id).orElseThrow(()-> new RequisicaoNaoEncontrada("Não foi possivel encontrar turma com ID: "+id));
    }
    
    public TurmasIcol criar(TurmasIcol turmasCriar){
        if(turmasCriar.getCurso() == null || turmasCriar.getCurso().getId() == null){
            throw new RequisicaoNaoEncontrada("É obrigatório informar o ID do curso.");
        }
        
        if(turmasCriar.getDocente() == null || turmasCriar.getDocente().getId() == null){
            throw new RequisicaoNaoEncontrada("É obrigatório informar o ID do docente.");
        }
        
        CursosIcol cursoExistente = repoCursos.findById(turmasCriar.getCurso().getId())
                .orElseThrow(()-> new RequisicaoNaoEncontrada("Curso não encontrado."));
        
        DocentesIcol docenteExistente = repoDocentes.findById(turmasCriar.getDocente().getId())
                .orElseThrow(()-> new RequisicaoNaoEncontrada("Docente não encontrado."));
        
        if(turmasCriar.getNumMaxAlunos() <= 0){
            throw new RequisicaoNaoEncontrada("Número máximo de alunos deve ser maior que 0.");
        }
        
        turmasCriar.setCurso(cursoExistente);
        turmasCriar.setDocente(docenteExistente);
        return repoTurmas.save(turmasCriar);
    }

    public TurmasIcol atualizar(Long id, TurmasIcol turmasAtualizar){
        TurmasIcol existente = buscar(id);
        
        if(turmasAtualizar.getStatusTurma() != null){
            existente.setStatusTurma(turmasAtualizar.getStatusTurma());
        }
        if(turmasAtualizar.getNumMaxAlunos() > 0){
            existente.setNumMaxAlunos(turmasAtualizar.getNumMaxAlunos());
        }
        if(turmasAtualizar.getCronogramaHorarios() != null){
            existente.setCronogramaHorarios(turmasAtualizar.getCronogramaHorarios());
        }
        return repoTurmas.save(existente);
    }

    public void deletar(Long id){
        if(!repoTurmas.existsById(id)){
            throw new RequisicaoNaoEncontrada("Turma não encontrada para exclusão.");
        }
        repoTurmas.deleteById(id);
    }
}

