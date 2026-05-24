package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.model.FrequenciaIcol;
import com.br.org.icol.icolbackend.service.FrequenciaIcolService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/frequencias")
public class FrequenciaIcolController {

    private final FrequenciaIcolService service;

    public FrequenciaIcolController(FrequenciaIcolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FrequenciaIcol>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FrequenciaIcol> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<FrequenciaIcol> criar(@Valid @RequestBody FrequenciaIcol frequencia) {
        FrequenciaIcol novaFrequencia = service.criar(frequencia);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaFrequencia.getId())
                .toUri();
                
        return ResponseEntity.created(uri).body(novaFrequencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FrequenciaIcol> atualizar(@PathVariable Long id, @Valid @RequestBody FrequenciaIcol frequencia) {
        return ResponseEntity.ok(service.atualizar(id, frequencia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
