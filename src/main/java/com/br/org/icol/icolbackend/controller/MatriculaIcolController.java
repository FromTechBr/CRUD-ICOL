package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.model.MatriculaIcol;
import com.br.org.icol.icolbackend.service.MatriculaIcolService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaIcolController {

    private final MatriculaIcolService service;

    public MatriculaIcolController(MatriculaIcolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MatriculaIcol>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaIcol> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<MatriculaIcol> criar(@Valid @RequestBody MatriculaIcol matricula) {
        MatriculaIcol novaMatricula = service.criar(matricula);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaMatricula.getId())
                .toUri();
                
        return ResponseEntity.created(uri).body(novaMatricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaIcol> atualizar(@PathVariable Long id, @Valid @RequestBody MatriculaIcol matricula) {
        return ResponseEntity.ok(service.atualizar(id, matricula));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
