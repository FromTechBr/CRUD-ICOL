package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.model.FrequenciaIcol;
import com.br.org.icol.icolbackend.model.MatriculaIcol;
import com.br.org.icol.icolbackend.repository.FrequenciaIcolRepositorio;
import com.br.org.icol.icolbackend.repository.MatriculaIcolRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FrequenciaIcolService {
    
    private final FrequenciaIcolRepositorio repoFrequencia;
    private final MatriculaIcolRepositorio repoMatricula;

    public FrequenciaIcolService(FrequenciaIcolRepositorio repoFrequencia, 
                                MatriculaIcolRepositorio repoMatricula) {
        this.repoFrequencia = repoFrequencia;
        this.repoMatricula = repoMatricula;
    }

    public List<FrequenciaIcol> listar() {
        return repoFrequencia.findAll();
    }

    public FrequenciaIcol buscar(Long id) {
        return repoFrequencia.findById(id)
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Frequência não encontrada com ID: " + id));
    }

    public FrequenciaIcol criar(FrequenciaIcol frequenciaCadastrar) {
        if (frequenciaCadastrar.getMatriculaId() == null || frequenciaCadastrar.getMatriculaId().getId() == null) {
            throw new RequisicaoNaoEncontrada("É obrigatório informar o ID da matrícula.");
        }

        if (frequenciaCadastrar.getDataAula() == null) {
            throw new RequisicaoNaoEncontrada("A data da aula é obrigatória.");
        }

        if (frequenciaCadastrar.getStatusPresenca() == null) {
            throw new RequisicaoNaoEncontrada("O status de presença é obrigatório.");
        }

        MatriculaIcol matriculaExistente = repoMatricula.findById(frequenciaCadastrar.getMatriculaId().getId())
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Matrícula não encontrada."));

        frequenciaCadastrar.setMatriculaId(matriculaExistente);
        return repoFrequencia.save(frequenciaCadastrar);
    }

    public FrequenciaIcol atualizar(Long id, FrequenciaIcol frequenciaAtualizar) {
        FrequenciaIcol existente = buscar(id);
        
        if (frequenciaAtualizar.getStatusPresenca() != null) {
            existente.setStatusPresenca(frequenciaAtualizar.getStatusPresenca());
        }
        if (frequenciaAtualizar.getJustificativa() != null) {
            existente.setJustificativa(frequenciaAtualizar.getJustificativa());
        }
        
        return repoFrequencia.save(existente);
    }

    public void deletar(Long id) {
        if (!repoFrequencia.existsById(id)) {
            throw new RequisicaoNaoEncontrada("Frequência não encontrada para exclusão.");
        }
        repoFrequencia.deleteById(id);
    }
}
