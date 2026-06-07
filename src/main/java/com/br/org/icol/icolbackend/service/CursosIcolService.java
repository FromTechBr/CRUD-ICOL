package com.br.org.icol.icolbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.org.icol.icolbackend.model.CursosIcol;
import com.br.org.icol.icolbackend.repository.CursosIcolRepositorio;
import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.exception.RequisicaoInvalida;
import com.br.org.icol.icolbackend.dto.CursoRequestDTO;
import com.br.org.icol.icolbackend.dto.CursoResponseDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CursosIcolService{
    private final CursosIcolRepositorio repoCursos;

    public CursosIcolService(CursosIcolRepositorio repoCursos){
        this.repoCursos = repoCursos;
    }

    // Método auxiliar para converter Entidade -> DTO
    private CursoResponseDTO toDTO(CursosIcol entidade) {
        CursoResponseDTO dto = new CursoResponseDTO();
        dto.setId(entidade.getId());
        dto.setNomeCurso(entidade.getNomeCurso());
        dto.setDescricao(entidade.getDescricao());
        dto.setDuracao(entidade.getDuracao());
        dto.setCategoria(entidade.getCategoria());
        dto.setAtivo(entidade.getAtivo());
        return dto;
    }

    // Método auxiliar para buscar a entidade internamente
    public CursosIcol buscarEntidade(Long id){
        return repoCursos.findById(id).orElseThrow(()-> new RequisicaoNaoEncontrada
        ("Curso com ID: "+id+" não encontrado!"));
    }

    public List<CursoResponseDTO> listar(){
        return repoCursos.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CursoResponseDTO buscar(Long id){
        return toDTO(buscarEntidade(id));
    }

    public CursoResponseDTO criar(CursoRequestDTO dto){
        if(repoCursos.findByNomeCurso(dto.getNomeCurso()).isPresent()){
            throw new RequisicaoInvalida("Esse curso já existe!");
        }
        
        CursosIcol cursosCadastrar = new CursosIcol();
        cursosCadastrar.setNomeCurso(dto.getNomeCurso());
        cursosCadastrar.setDescricao(dto.getDescricao());
        cursosCadastrar.setDuracao(dto.getDuracao());
        cursosCadastrar.setCategoria(dto.getCategoria());
        cursosCadastrar.setAtivo(true);
        
        CursosIcol salvo = repoCursos.save(cursosCadastrar);
        return toDTO(salvo);
    }

    public CursoResponseDTO atualizar(Long id, CursoRequestDTO dto){
        CursosIcol existente = buscarEntidade(id);
        Optional<CursosIcol> cursoComEsseNome = repoCursos.findByNomeCurso(dto.getNomeCurso());

        if(cursoComEsseNome.isPresent() && !cursoComEsseNome.get().getId().equals(id)){
            throw new RequisicaoInvalida("Este curso já se encontra cadastrado.");
        }
         
        existente.setNomeCurso(dto.getNomeCurso());
        existente.setDescricao(dto.getDescricao());
        existente.setDuracao(dto.getDuracao());
        existente.setCategoria(dto.getCategoria());

        CursosIcol salvo = repoCursos.save(existente);
        return toDTO(salvo);
    }

    public void inativar(Long id){
        CursosIcol cursosAdeletar = buscarEntidade(id);
        cursosAdeletar.setAtivo(false);
        repoCursos.save(cursosAdeletar);
    }
}