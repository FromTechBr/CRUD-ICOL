package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.model.AvisosMuralIcol;
import com.br.org.icol.icolbackend.service.AvisosMuralIcolService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/avisos")
public class AvisosMuralIcolController {

    private final AvisosMuralIcolService service;

    public AvisosMuralIcolController(AvisosMuralIcolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AvisosMuralIcol>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvisosMuralIcol> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<AvisosMuralIcol> criar(@Valid @RequestBody AvisosMuralIcol aviso) {
        AvisosMuralIcol novoAviso = service.criar(aviso);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoAviso.getId())
                .toUri();
                
        return ResponseEntity.created(uri).body(novoAviso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvisosMuralIcol> atualizar(@PathVariable Long id, @Valid @RequestBody AvisosMuralIcol aviso) {
        return ResponseEntity.ok(service.atualizar(id, aviso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
