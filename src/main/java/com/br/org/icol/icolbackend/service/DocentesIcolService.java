package com.br.org.icol.icolbackend.service;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.model.DocentesIcol;
import com.br.org.icol.icolbackend.model.UsuariosIcol;
import com.br.org.icol.icolbackend.repository.DocentesIcolRepositorio;
import com.br.org.icol.icolbackend.repository.UsuariosIcolRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocentesIcolService {

    private final DocentesIcolRepositorio repoDocentes;
    private final UsuariosIcolRepositorio repoUsuarios;

    public DocentesIcolService(DocentesIcolRepositorio repoDocentes, UsuariosIcolRepositorio repoUsuarios) {
        this.repoDocentes = repoDocentes;
        this.repoUsuarios = repoUsuarios;
    }

    public List<DocentesIcol> listar() {
        return repoDocentes.findAll();
    }

    public DocentesIcol buscar(Long id) {
        return repoDocentes.findById(id)
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Docente não encontrado com ID: " + id));
    }

    public DocentesIcol criar(DocentesIcol docenteCadastrar) {
        // 1. Validação de Vínculo com Usuário
        if (docenteCadastrar.getUsuario() == null || docenteCadastrar.getUsuario().getId() == null) {
            throw new RequisicaoNaoEncontrada("É obrigatório informar o ID do usuário de acesso.");
        }

        // 2. Busca o usuário no banco para garantir que existe
        UsuariosIcol usuarioExistente = repoUsuarios.findById(docenteCadastrar.getUsuario().getId())
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Usuário de acesso não encontrado."));

        // 3. Verifica se esse usuário já está vinculado a outro docente (Regra 1:1)
        if (repoDocentes.findByUsuarioId(usuarioExistente.getId()).isPresent()) {
            throw new RequisicaoNaoEncontrada("Este usuário já está vinculado a um docente.");
        }

        // 4. Validação de Campos
        if (docenteCadastrar.getNomeCompleto() == null || docenteCadastrar.getNomeCompleto().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("O nome completo é obrigatório.");
        }

        docenteCadastrar.setUsuario(usuarioExistente);
        return repoDocentes.save(docenteCadastrar);
    }

    public DocentesIcol atualizar(Long id, DocentesIcol docenteAtualizar) {
        DocentesIcol existente = buscar(id);

        if (docenteAtualizar.getNomeCompleto() == null || docenteAtualizar.getNomeCompleto().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("O nome completo é obrigatório.");
        }

        existente.setNomeCompleto(docenteAtualizar.getNomeCompleto());
        existente.setEspecializacao(docenteAtualizar.getEspecializacao());

        return repoDocentes.save(existente);
    }

    public void deletar(Long id) {
        if (!repoDocentes.existsById(id)) {
            throw new RequisicaoNaoEncontrada("Docente não encontrado para exclusão.");
        }
        repoDocentes.deleteById(id);
    }
}