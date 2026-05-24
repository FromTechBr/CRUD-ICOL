package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.model.TurmasIcol;
import com.br.org.icol.icolbackend.service.TurmasIcolService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmasIcolController {

    private final TurmasIcolService service;

    public TurmasIcolController(TurmasIcolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TurmasIcol>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmasIcol> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<TurmasIcol> criar(@Valid @RequestBody TurmasIcol turma) {
        TurmasIcol novaTurma = service.criar(turma);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaTurma.getId())
                .toUri();
                
        return ResponseEntity.created(uri).body(novaTurma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmasIcol> atualizar(@PathVariable Long id, @Valid @RequestBody TurmasIcol turma) {
        return ResponseEntity.ok(service.atualizar(id, turma));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
