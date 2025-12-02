package com.br.org.icol.icolbackend.service;

import org.springframework.stereotype.Service;

import com.br.org.icol.icolbackend.model.CursosIcol;
import com.br.org.icol.icolbackend.repository.CursosIcolRepositorio;
import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import java.util.List;
import java.util.Optional;

@Service
public class CursosIcolService{
    private final CursosIcolRepositorio repoCursos;

    public CursosIcolService(CursosIcolRepositorio repoCursos){
        this.repoCursos = repoCursos;
    }

    public List<CursosIcol>listar(){
        return repoCursos.findAll();
    }
    public CursosIcol buscar(Long id){
        return repoCursos.findById(id).orElseThrow(()-> new RequisicaoNaoEncontrada
        ("Curso com ID: "+id+" não encontrado!"));
    }
    public CursosIcol criar(CursosIcol cursosCadastar){
        if(repoCursos.findByNomeCurso(cursosCadastar.getNomeCurso()).isPresent()){
            throw new RequisicaoNaoEncontrada("Esse curso já exite!");
        }
        if (cursosCadastar.getDescricao() == null || cursosCadastar.getDescricao().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("A descrição do curso é obrigatória.");
        }
        if (cursosCadastar.getDuracao() == null || cursosCadastar.getDuracao().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("A duração do curso é obrigatória.");
        }
        cursosCadastar.setAtivo(true);
        return repoCursos.save(cursosCadastar);
    }
    public CursosIcol atualizar(Long id,CursosIcol cursosAtualizar){
        CursosIcol existente = buscar(id);
        Optional<CursosIcol> cursoComEsseNome = repoCursos.findByNomeCurso(cursosAtualizar.getNomeCurso());

        if(cursoComEsseNome.isPresent() && !cursoComEsseNome.get().getId().equals(id)){
            throw new RequisicaoNaoEncontrada("Este curso já se encontra cadastrado.");
        }
         if (cursosAtualizar.getDescricao() == null || cursosAtualizar.getDescricao().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("A descrição do curso é obrigatória.");
        }
        if (cursosAtualizar.getDuracao() == null || cursosAtualizar.getDuracao().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("A duração do curso é obrigatória.");
        }
        existente.setNomeCurso(cursosAtualizar.getNomeCurso());
        existente.setDescricao(cursosAtualizar.getDescricao());
        existente.setDuracao(cursosAtualizar.getDuracao());
        existente.setCategoria(cursosAtualizar.getCategoria());

        return repoCursos.save(existente);
    }
    public void inativar(Long id){
        CursosIcol cursosAdeletar = buscar(id);
        cursosAdeletar.setAtivo(false);
        repoCursos.save(cursosAdeletar);
    }
}