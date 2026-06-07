package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.exception.RequisicaoInvalida;
import com.br.org.icol.icolbackend.model.DocentesIcol;
import com.br.org.icol.icolbackend.model.UsuariosIcol;
import com.br.org.icol.icolbackend.repository.DocentesIcolRepositorio;
import com.br.org.icol.icolbackend.repository.UsuariosIcolRepositorio;
import com.br.org.icol.icolbackend.dto.DocenteRequestDTO;
import com.br.org.icol.icolbackend.dto.DocenteResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DocentesIcolService {

    private final DocentesIcolRepositorio repoDocentes;
    private final UsuariosIcolRepositorio repoUsuarios;

    public DocentesIcolService(DocentesIcolRepositorio repoDocentes, UsuariosIcolRepositorio repoUsuarios) {
        this.repoDocentes = repoDocentes;
        this.repoUsuarios = repoUsuarios;
    }

    // Método auxiliar para converter Entidade -> DTO
    private DocenteResponseDTO toDTO(DocentesIcol entidade) {
        DocenteResponseDTO dto = new DocenteResponseDTO();
        dto.setId(entidade.getId());
        dto.setNomeCompleto(entidade.getNomeCompleto());
        dto.setEspecializacao(entidade.getEspecializacao());
        if (entidade.getUsuario() != null) {
            dto.setUsuarioId(entidade.getUsuario().getId());
            dto.setEmailUsuario(entidade.getUsuario().getEmailUsuario());
        }
        return dto;
    }

    // Método auxiliar para buscar a entidade internamente
    public DocentesIcol buscarEntidade(Long id) {
        return repoDocentes.findById(id)
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Docente não encontrado com ID: " + id));
    }

    public List<DocenteResponseDTO> listar() {
        return repoDocentes.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DocenteResponseDTO buscar(Long id) {
        return toDTO(buscarEntidade(id));
    }

    public DocenteResponseDTO criar(DocenteRequestDTO dto) {
        // 1. Busca o usuário no banco para garantir que existe
        UsuariosIcol usuarioExistente = repoUsuarios.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Usuário de acesso não encontrado."));

        // 2. Verifica se esse usuário já está vinculado a outro docente (Regra 1:1)
        if (repoDocentes.findByUsuario(usuarioExistente.getId()).isPresent()) {
            throw new RequisicaoInvalida("Este usuário já está vinculado a um docente.");
        }

        DocentesIcol docenteCadastrar = new DocentesIcol();
        docenteCadastrar.setNomeCompleto(dto.getNomeCompleto());
        docenteCadastrar.setEspecializacao(dto.getEspecializacao());
        docenteCadastrar.setUsuario(usuarioExistente);
        
        DocentesIcol salvo = repoDocentes.save(docenteCadastrar);
        return toDTO(salvo);
    }

    public DocenteResponseDTO atualizar(Long id, DocenteRequestDTO dto) {
        DocentesIcol existente = buscarEntidade(id);

        existente.setNomeCompleto(dto.getNomeCompleto());
        existente.setEspecializacao(dto.getEspecializacao());

        DocentesIcol salvo = repoDocentes.save(existente);
        return toDTO(salvo);
    }

    public void deletar(Long id) {
        if (!repoDocentes.existsById(id)) {
            throw new RequisicaoNaoEncontrada("Docente não encontrado para exclusão.");
        }
        repoDocentes.deleteById(id);
    }
}