package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.model.DocentesIcol;
import com.br.org.icol.icolbackend.service.DocentesIcolService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/docentes")
public class DocentesIcolController {

    private final DocentesIcolService service;

    public DocentesIcolController(DocentesIcolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DocentesIcol>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocentesIcol> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<DocentesIcol> criar(@Valid @RequestBody DocentesIcol docente) {
        DocentesIcol novoDocente = service.criar(docente);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoDocente.getId())
                .toUri();
                
        return ResponseEntity.created(uri).body(novoDocente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocentesIcol> atualizar(@PathVariable Long id, @Valid @RequestBody DocentesIcol docente) {
        return ResponseEntity.ok(service.atualizar(id, docente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}