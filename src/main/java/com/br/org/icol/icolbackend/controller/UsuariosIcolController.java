package com.br.org.icol.icolbackend.controller;

import java.util.List;
import java.net.URI;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.org.icol.icolbackend.model.UsuariosIcol;
import com.br.org.icol.icolbackend.service.UsuariosIcolService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosIcolController {
    private final UsuariosIcolService service;
    
    public UsuariosIcolController(UsuariosIcolService service){
        this.service=service;
    }

    @GetMapping
    public List<UsuariosIcol>listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuariosIcol> buscar(@PathVariable Long id){
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<UsuariosIcol> criar(@Valid @RequestBody UsuariosIcol usuarioCadastrar){
        UsuariosIcol criado = service.criar(usuarioCadastrar);
        URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuariosIcol> atualizar(@PathVariable Long id, @Valid @RequestBody UsuariosIcol pessoaAtualizar){
        return ResponseEntity.ok(service.atualizar(id, pessoaAtualizar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
}   

