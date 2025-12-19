package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.repository.CursosIcolRepositorio;
import com.br.org.icol.icolbackend.repository.DocentesIcolRepositorio;
import com.br.org.icol.icolbackend.repository.TurmasIcolRepositorio;
import com.br.org.icol.icolbackend.model.TurmasIcol;
import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.enums.StatusTurma;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        
}
