package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.model.FrequenciaIcol;
import com.br.org.icol.icolbackend.model.MatriculaIcol;
import com.br.org.icol.icolbackend.repository.FrequenciaIcolRepositorio;
import com.br.org.icol.icolbackend.repository.MatriculaIcolRepositorio;
import com.br.org.icol.icolbackend.dto.FrequenciaRequestDTO;
import com.br.org.icol.icolbackend.dto.FrequenciaResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FrequenciaIcolService {
    
    private final FrequenciaIcolRepositorio repoFrequencia;
    private final MatriculaIcolRepositorio repoMatricula;

    public FrequenciaIcolService(FrequenciaIcolRepositorio repoFrequencia, 
                                MatriculaIcolRepositorio repoMatricula) {
        this.repoFrequencia = repoFrequencia;
        this.repoMatricula = repoMatricula;
    }

    // Método auxiliar para converter Entidade -> DTO
    private FrequenciaResponseDTO toDTO(FrequenciaIcol entidade) {
        FrequenciaResponseDTO dto = new FrequenciaResponseDTO();
        dto.setId(entidade.getId());
        dto.setDataAula(entidade.getDataAula());
        dto.setStatusPresenca(entidade.getStatusPresenca());
        dto.setJustificativa(entidade.getJustificativa());
        if (entidade.getMatriculaId() != null) {
            dto.setMatriculaId(entidade.getMatriculaId().getId());
        }
        return dto;
    }

    // Método auxiliar para buscar a entidade internamente
    public FrequenciaIcol buscarEntidade(Long id) {
        return repoFrequencia.findById(id)
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Frequência não encontrada com ID: " + id));
    }

    public List<FrequenciaResponseDTO> listar() {
        return repoFrequencia.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FrequenciaResponseDTO buscar(Long id) {
        return toDTO(buscarEntidade(id));
    }

    public FrequenciaResponseDTO registrar(FrequenciaRequestDTO dto) {
        MatriculaIcol matriculaExistente = repoMatricula.findById(dto.getMatriculaId())
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Matrícula não encontrada."));

        FrequenciaIcol frequenciaRegistrar = new FrequenciaIcol();
        frequenciaRegistrar.setMatriculaId(matriculaExistente);
        frequenciaRegistrar.setDataAula(dto.getDataAula());
        frequenciaRegistrar.setStatusPresenca(dto.getStatusPresenca());
        frequenciaRegistrar.setJustificativa(dto.getJustificativa());

        FrequenciaIcol salvo = repoFrequencia.save(frequenciaRegistrar);
        return toDTO(salvo);
    }

    public FrequenciaResponseDTO atualizar(Long id, FrequenciaRequestDTO dto) {
        FrequenciaIcol existente = buscarEntidade(id);

        existente.setDataAula(dto.getDataAula());
        existente.setStatusPresenca(dto.getStatusPresenca());
        existente.setJustificativa(dto.getJustificativa());

        FrequenciaIcol salvo = repoFrequencia.save(existente);
        return toDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repoFrequencia.existsById(id)) {
            throw new RequisicaoNaoEncontrada("Frequência não encontrada para exclusão.");
        }
        repoFrequencia.deleteById(id);
    }
}
