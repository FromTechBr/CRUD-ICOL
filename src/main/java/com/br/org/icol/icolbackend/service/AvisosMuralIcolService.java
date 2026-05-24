package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.model.AvisosMuralIcol;
import com.br.org.icol.icolbackend.model.UsuariosIcol;
import com.br.org.icol.icolbackend.repository.AvisosMuralIcolRepositorio;
import com.br.org.icol.icolbackend.repository.UsuariosIcolRepositorio;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvisosMuralIcolService {
    
    private final AvisosMuralIcolRepositorio repoAvisos;
    private final UsuariosIcolRepositorio repoUsuarios;

    public AvisosMuralIcolService(AvisosMuralIcolRepositorio repoAvisos, 
                                 UsuariosIcolRepositorio repoUsuarios) {
        this.repoAvisos = repoAvisos;
        this.repoUsuarios = repoUsuarios;
    }

    public List<AvisosMuralIcol> listar() {
        return repoAvisos.findAll();
    }

    public AvisosMuralIcol buscar(Long id) {
        return repoAvisos.findById(id)
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Aviso não encontrado com ID: " + id));
    }

    public AvisosMuralIcol criar(AvisosMuralIcol avisoCadastrar) {
        if (avisoCadastrar.getAutorId() == null || avisoCadastrar.getAutorId().getId() == null) {
            throw new RequisicaoNaoEncontrada("É obrigatório informar o ID do autor.");
        }

        if (avisoCadastrar.getTitulo() == null || avisoCadastrar.getTitulo().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("O título do aviso é obrigatório.");
        }

        if (avisoCadastrar.getConteudo() == null || avisoCadastrar.getConteudo().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("O conteúdo do aviso é obrigatório.");
        }

        UsuariosIcol autorExistente = repoUsuarios.findById(avisoCadastrar.getAutorId().getId())
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Autor não encontrado."));

        avisoCadastrar.setAutorId(autorExistente);
        avisoCadastrar.setDataPostagem(LocalDateTime.now());
        
        return repoAvisos.save(avisoCadastrar);
    }

    public AvisosMuralIcol atualizar(Long id, AvisosMuralIcol avisoAtualizar) {
        AvisosMuralIcol existente = buscar(id);
        
        if (avisoAtualizar.getTitulo() != null && !avisoAtualizar.getTitulo().trim().isEmpty()) {
            existente.setTitulo(avisoAtualizar.getTitulo());
        }
        if (avisoAtualizar.getConteudo() != null && !avisoAtualizar.getConteudo().trim().isEmpty()) {
            existente.setConteudo(avisoAtualizar.getConteudo());
        }
        
        return repoAvisos.save(existente);
    }

    public void deletar(Long id) {
        if (!repoAvisos.existsById(id)) {
            throw new RequisicaoNaoEncontrada("Aviso não encontrado para exclusão.");
        }
        repoAvisos.deleteById(id);
    }
}
