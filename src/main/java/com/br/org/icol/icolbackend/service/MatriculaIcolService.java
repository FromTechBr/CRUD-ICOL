package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.model.AlunosIcol;
import com.br.org.icol.icolbackend.model.MatriculaIcol;
import com.br.org.icol.icolbackend.model.TurmasIcol;
import com.br.org.icol.icolbackend.repository.AlunosIcolRepositorio;
import com.br.org.icol.icolbackend.repository.MatriculaIcolRepositorio;
import com.br.org.icol.icolbackend.repository.TurmasIcolRepositorio;
import com.br.org.icol.icolbackend.dto.MatriculaRequestDTO;
import com.br.org.icol.icolbackend.dto.MatriculaResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

    // Método auxiliar para converter Entidade -> DTO
    private MatriculaResponseDTO toDTO(MatriculaIcol entidade) {
        MatriculaResponseDTO dto = new MatriculaResponseDTO();
        dto.setId(entidade.getId());
        dto.setDataMatricula(entidade.getDataMatricula());
        dto.setStatusMatricula(entidade.getStatusMatricula());
        
        if (entidade.getAlunoMatrId() != null) {
            dto.setAlunoId(entidade.getAlunoMatrId().getId());
            dto.setNomeAluno(entidade.getAlunoMatrId().getNomeCompleto());
        }
        
        if (entidade.getTurmaMatrId() != null) {
            dto.setTurmaId(entidade.getTurmaMatrId().getId());
        }
        
        return dto;
    }

    // Método auxiliar para buscar a entidade internamente
    public MatriculaIcol buscarEntidade(Long id){
        return repoMatricula.findById(id).orElseThrow(()-> new RequisicaoNaoEncontrada("Não foi possível encontrar a matrícula com ID: "+id));
    }

    public List<MatriculaResponseDTO> listar(){
        return repoMatricula.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MatriculaResponseDTO buscar(Long id){
        return toDTO(buscarEntidade(id));
    }
    
    public MatriculaResponseDTO criar(MatriculaRequestDTO dto){
        AlunosIcol alunoExistente = repoAlunos.findById(dto.getAlunoId())
                .orElseThrow(()-> new RequisicaoNaoEncontrada("Aluno não encontrado."));
        
        TurmasIcol turmaExistente = repoTurmas.findById(dto.getTurmaId())
                .orElseThrow(()-> new RequisicaoNaoEncontrada("Turma não encontrada."));
        
        MatriculaIcol matriculaCadastrar = new MatriculaIcol();
        matriculaCadastrar.setStatusMatricula(dto.getStatusMatricula());
        matriculaCadastrar.setDataMatricula(LocalDate.now());
        matriculaCadastrar.setAlunoMatrId(alunoExistente);
        matriculaCadastrar.setTurmaMatrId(turmaExistente);
        
        MatriculaIcol salvo = repoMatricula.save(matriculaCadastrar);
        return toDTO(salvo);
    }

    public MatriculaResponseDTO atualizar(Long id, MatriculaRequestDTO dto){
        MatriculaIcol existente = buscarEntidade(id);
        existente.setStatusMatricula(dto.getStatusMatricula());
        
        MatriculaIcol salvo = repoMatricula.save(existente);
        return toDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repoMatricula.existsById(id)) {
            throw new RequisicaoNaoEncontrada("Matrícula não encontrada para exclusão.");
        }
        repoMatricula.deleteById(id);
    }
}
