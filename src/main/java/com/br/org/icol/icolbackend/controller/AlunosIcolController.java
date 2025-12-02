package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.model.AlunosIcol;
import com.br.org.icol.icolbackend.service.AlunosIcolService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunosIcolController {

    private final AlunosIcolService service;

    public AlunosIcolController(AlunosIcolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlunosIcol>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunosIcol> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<AlunosIcol> criar(@Valid @RequestBody AlunosIcol aluno) {
        AlunosIcol novoAluno = service.criar(aluno);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoAluno.getId())
                .toUri();
                
        return ResponseEntity.created(uri).body(novoAluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunosIcol> atualizar(@PathVariable Long id, @Valid @RequestBody AlunosIcol aluno) {
        return ResponseEntity.ok(service.atualizar(id, aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // Chamando o método de exclusão física (deleteById) que está no Service
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}