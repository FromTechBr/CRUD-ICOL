package com.br.org.icol.icolbackend;

import com.br.org.icol.icolbackend.enums.TiposUsuario;
import com.br.org.icol.icolbackend.model.AlunosIcol;
import com.br.org.icol.icolbackend.model.UsuariosIcol;
import com.br.org.icol.icolbackend.repository.AlunosIcolRepositorio;
import com.br.org.icol.icolbackend.repository.UsuariosIcolRepositorio;
import com.br.org.icol.icolbackend.service.AlunosIcolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AlunosIcolIntegrationTests {

    @Autowired
    private AlunosIcolService alunosService;

    @Autowired
    private UsuariosIcolRepositorio usuariosRepo;

    @Autowired
    private AlunosIcolRepositorio alunosRepo;

    private UsuariosIcol usuarioAluno;

    @BeforeEach
    void setUp() {
        UsuariosIcol usuario = new UsuariosIcol();
        usuario.setEmailUsuario("aluno.teste." + System.nanoTime() + "@icol.org.br");
        usuario.setSenha("senhaForte123");
        usuario.setTipoUsuario(TiposUsuario.ALUNO);
        usuario.setAtivo(true);
        usuarioAluno = usuariosRepo.save(usuario);
    }

    @Test
    void deveSalvarAlunoComUsuarioExistente() {
        AlunosIcol aluno = new AlunosIcol();
        aluno.setUsuario(usuarioAluno);
        aluno.setNomeCompleto("Teste Integração");
        aluno.setIdade(22);
        aluno.setCpf("999.888.777-66");
        aluno.setTelefone("11999998877");
        aluno.setDeclaSocie("Renda familiar até 2 salários mínimos");
        aluno.setEndereco("Rua de Teste, 10");

        AlunosIcol salvo = alunosService.criar(aluno);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getCpf()).isEqualTo("999.888.777-66");
        assertThat(alunosRepo.existsById(salvo.getId())).isTrue();
    }

    @Test
    void deveBuscarAlunoPorIdAposSalvar() {
        AlunosIcol aluno = new AlunosIcol();
        aluno.setUsuario(usuarioAluno);
        aluno.setNomeCompleto("Consulta Aluno");
        aluno.setIdade(24);
        aluno.setCpf("888.777.666-55");
        aluno.setTelefone("11888887766");
        aluno.setDeclaSocie("Renda familiar até 2 salários mínimos");
        aluno.setEndereco("Av Consulta, 100");

        AlunosIcol salvo = alunosService.criar(aluno);
        AlunosIcol encontrado = alunosService.buscar(salvo.getId());

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNomeCompleto()).isEqualTo("Consulta Aluno");
        assertThat(encontrado.getUsuario().getId()).isEqualTo(usuarioAluno.getId());
    }

    @Test
    void deveDeletarAlunoAposCriar() {
        AlunosIcol aluno = new AlunosIcol();
        aluno.setUsuario(usuarioAluno);
        aluno.setNomeCompleto("Aluno Deletar");
        aluno.setIdade(21);
        aluno.setCpf("777.666.555-44");
        aluno.setTelefone("11777776655");
        aluno.setDeclaSocie("Renda familiar até 2 salários mínimos");
        aluno.setEndereco("Rua Delete, 50");

        AlunosIcol salvo = alunosService.criar(aluno);
        alunosService.deletar(salvo.getId());

        assertThat(alunosRepo.existsById(salvo.getId())).isFalse();
    }
}
