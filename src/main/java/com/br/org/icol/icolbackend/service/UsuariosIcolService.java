package com.br.org.icol.icolbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.org.icol.icolbackend.exception.RequisicaoNaoEncontrada;
import com.br.org.icol.icolbackend.model.UsuariosIcol;
import com.br.org.icol.icolbackend.repository.UsuariosIcolRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosIcolService {
    private final UsuariosIcolRepositorio repoUsuarios; //declaro que a variavel repo é do tipo repositorio e que ele quem vai receber as informações para od
    private final PasswordEncoder passwordEncoder;

    public UsuariosIcolService(UsuariosIcolRepositorio repoUsuarios, PasswordEncoder passwordEncoder){
        this.repoUsuarios = repoUsuarios;
        this.passwordEncoder=passwordEncoder;
    }
    
    public List<UsuariosIcol> listar(){
        return repoUsuarios.findAll();
        // metodo para listar os usuarios que constam no banco
    }
    
    public UsuariosIcol buscar(Long id){
        // metodo para encontrar os usuarios registrados no banco
        return repoUsuarios.findById(id).orElseThrow(()-> new RequisicaoNaoEncontrada("Não foi possivel encontrar usuario com ID: "+id));
    }

    //abaixo estarei criando um metodo para cadastar o usuario no banco
    //ele vai conter as regras de negocio para que tudo de certo
    public UsuariosIcol criar(UsuariosIcol usuarioCadastrar){
        usuarioCadastrar.setAtivo(true);
        if(repoUsuarios.findByEmailUsuario(usuarioCadastrar.getEmailUsuario()).isPresent()){
            throw new RequisicaoNaoEncontrada("Este email já está cadastrado!");
        }
        String senhaCodificada = passwordEncoder.encode(usuarioCadastrar.getSenha());
        usuarioCadastrar.setSenha(senhaCodificada);
        return repoUsuarios.save(usuarioCadastrar);
    }

    public UsuariosIcol atualizar(Long id, UsuariosIcol usuarioAtualizar){
        UsuariosIcol existente = buscar(id);
        Optional<UsuariosIcol> usuarioComEsseEmail = repoUsuarios.findByEmailUsuario(usuarioAtualizar.getEmailUsuario());

        if(usuarioComEsseEmail.isPresent() && !usuarioComEsseEmail.get().getId().equals(id)){
            throw new RequisicaoNaoEncontrada("Este email já está cadastrado!");
        }
        existente.setEmailUsuario(usuarioAtualizar.getEmailUsuario());
        String novaSenha = passwordEncoder.encode(usuarioAtualizar.getSenha());
        existente.setSenha(novaSenha);
        existente.setTipoUsuario(usuarioAtualizar.getTipoUsuario());
        return repoUsuarios.save(existente);
    }

    public void inativar(Long id){
        UsuariosIcol usuarioAdeletar = buscar(id);
        usuarioAdeletar.setAtivo(false);
        repoUsuarios.save(usuarioAdeletar);
    }

}
