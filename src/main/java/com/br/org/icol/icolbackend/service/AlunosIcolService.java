package com.br.org.icol.icolbackend.service;

import org.springframework.stereotype.Service;
import com.br.org.icol.icolbackend.model.AlunosIcol;
import com.br.org.icol.icolbackend.model.UsuariosIcol;
import com.br.org.icol.icolbackend.repository.AlunosIcolRepositorio;
import com.br.org.icol.icolbackend.repository.UsuariosIcolRepositorio;
import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;

import java.util.List;
import java.util.Optional;

@Service
public class AlunosIcolService {

    private final AlunosIcolRepositorio repoAlunos;
    private final UsuariosIcolRepositorio repoUsuarios;

    // Injeção de dependência dos dois repositórios via construtor
    public AlunosIcolService(AlunosIcolRepositorio repoAlunos, UsuariosIcolRepositorio repoUsuarios) {
        this.repoAlunos = repoAlunos;
        this.repoUsuarios = repoUsuarios;
    }

    // 1. Listar todos os alunos
    public List<AlunosIcol> listar() {
        return repoAlunos.findAll();
    }

    // 2. Buscar aluno por ID
    public AlunosIcol buscar(Long id) {
        return repoAlunos.findById(id)
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Aluno não encontrado com ID: " + id));
    }

    // 3. Criar Aluno (Com regras de negócio)
    public AlunosIcol criar(AlunosIcol alunoCadastrar) {
        // RN: CPF Único - Verifica se já existe
        if (repoAlunos.findByCpf(alunoCadastrar.getCpf()).isPresent()) {
            throw new RequisicaoNaoEncontrada("Já existe um aluno cadastrado com este CPF.");
        }

        // RN: Campos Obrigatórios (Validação Manual)
        if (alunoCadastrar.getNomeCompleto() == null || alunoCadastrar.getNomeCompleto().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("O nome completo é obrigatório.");
        }
        if (alunoCadastrar.getDeclaSocie() == null || alunoCadastrar.getDeclaSocie().trim().isEmpty()) {
            throw new RequisicaoNaoEncontrada("A declaração socioeconômica é obrigatória.");
        }

        // RN: Vínculo com Usuário (Login)
        // Verifica se o objeto usuario ou o ID dele vieram nulos
        if (alunoCadastrar.getUsuario() == null || alunoCadastrar.getUsuario().getId() == null) {
            throw new RequisicaoNaoEncontrada("É obrigatório informar o ID do usuário de acesso (Login).");
        }

        // Busca o usuário real no banco para garantir que ele existe antes de vincular
        UsuariosIcol usuarioExistente = repoUsuarios.findById(alunoCadastrar.getUsuario().getId())
                .orElseThrow(() -> new RequisicaoNaoEncontrada("Usuário de acesso não encontrado."));

        // "Encaixa" o usuário completo dentro do aluno para salvar o relacionamento correto
        alunoCadastrar.setUsuario(usuarioExistente);

        return repoAlunos.save(alunoCadastrar);
    }

    // 4. Atualizar Aluno
    public AlunosIcol atualizar(Long id, AlunosIcol alunoAtualizar) {
        // Busca o aluno existente (se não existir, o método buscar já lança erro)
        AlunosIcol existente = buscar(id);

        // RN: Validação de CPF na atualização (Não pode usar CPF de outro aluno)
        Optional<AlunosIcol> alunoComEsseCpf = repoAlunos.findByCpf(alunoAtualizar.getCpf());
        if (alunoComEsseCpf.isPresent() && !alunoComEsseCpf.get().getId().equals(id)) {
            throw new RequisicaoNaoEncontrada("Este CPF já está sendo usado por outro aluno.");
        }

        // Atualiza os dados cadastrais
        existente.setNomeCompleto(alunoAtualizar.getNomeCompleto());
        existente.setCpf(alunoAtualizar.getCpf());
        existente.setTelefone(alunoAtualizar.getTelefone());
        existente.setEndereco(alunoAtualizar.getEndereco());
        existente.setIdade(alunoAtualizar.getIdade());
        existente.setDeclaSocie(alunoAtualizar.getDeclaSocie());

        // Nota: Não atualizamos o 'Usuario' (Login) aqui para manter a segurança do vínculo.
        
        return repoAlunos.save(existente);
    }

    // 5. Deletar Aluno (Exclusão Física)
    // Como o modelo AlunosIcol não tem o campo "ativo", usamos o deleteById
    public void deletar(Long id) {
        if (!repoAlunos.existsById(id)) {
            throw new RequisicaoNaoEncontrada("Aluno não encontrado para exclusão.");
        }
        repoAlunos.deleteById(id);
    }
}