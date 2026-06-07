package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.exception.RequisicaoInvalida;
import com.br.org.icol.icolbackend.model.DocentesIcol;
import com.br.org.icol.icolbackend.model.UsuariosIcol;
import com.br.org.icol.icolbackend.repository.DocentesIcolRepositorio;
import com.br.org.icol.icolbackend.repository.UsuariosIcolRepositorio;
import com.br.org.icol.icolbackend.repository.AlunosIcolRepositorio;
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
    private final AlunosIcolRepositorio repoAlunos;

    public DocentesIcolService(DocentesIcolRepositorio repoDocentes, UsuariosIcolRepositorio repoUsuarios, AlunosIcolRepositorio repoAlunos) {
        this.repoDocentes = repoDocentes;
        this.repoUsuarios = repoUsuarios;
        this.repoAlunos = repoAlunos;
    }

    // Método auxiliar para converter Entidade -> DTO
    private DocenteResponseDTO toDTO(DocentesIcol entidade) {
        DocenteResponseDTO dto = new DocenteResponseDTO();
        dto.setId(entidade.getId());
        dto.setNomeCompleto(entidade.getNomeCompleto());
        dto.setEspecializacao(entidade.getEspecializacao());
        dto.setAtivo(entidade.getAtivo());
        if (entidade.getUsuario() != null) {
            dto.setUsuarioId(entidade.getUsuario().getId());
            dto.setEmailUsuario(entidade.getUsuario().getEmailUsuario());
        }
        return dto;
    }

    // Método auxiliar para buscar a entidade internamente
    public DocentesIcol buscarEntidade(Long id) {
        return repoDocentes.findByIdAndAtivo(id)
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Docente não encontrado com ID: " + id));
    }

    public List<DocenteResponseDTO> listar() {
        return repoDocentes.findAllAtivos().stream()
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

        // Regra 1: Vínculo Único e Perfil
        if (usuarioExistente.getTipoUsuario() != com.br.org.icol.icolbackend.enums.TiposUsuario.PROFESSOR) {
            throw new RequisicaoInvalida("Este usuário não possui permissão de PROFESSOR.");
        }
        if (repoAlunos.existsByUsuario_Id(dto.getUsuarioId())) {
            throw new RequisicaoInvalida("Este usuário já possui um vínculo de ALUNO ativo.");
        }

        // 2. Verifica se esse usuário já está vinculado a outro docente (Regra 1:1)
        if (repoDocentes.findByUsuario(usuarioExistente.getId()).isPresent()) {
            throw new RequisicaoInvalida("Este usuário já está vinculado a um docente.");
        }

        DocentesIcol docenteCadastrar = new DocentesIcol();
        docenteCadastrar.setNomeCompleto(dto.getNomeCompleto());
        docenteCadastrar.setEspecializacao(dto.getEspecializacao());
        docenteCadastrar.setUsuario(usuarioExistente);
        docenteCadastrar.setAtivo(true);
        
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

    public void inativar(Long id) {
        DocentesIcol docenteInativar = buscarEntidade(id);
        docenteInativar.setAtivo(false);
        repoDocentes.save(docenteInativar);
    }
}