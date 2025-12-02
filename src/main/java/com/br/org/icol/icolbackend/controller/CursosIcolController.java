package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.model.CursosIcol;
import com.br.org.icol.icolbackend.service.CursosIcolService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursosIcolController {

    private final CursosIcolService service;

    public CursosIcolController(CursosIcolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CursosIcol>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursosIcol> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<CursosIcol> criar(@Valid @RequestBody CursosIcol curso) {
        CursosIcol novoCurso = service.criar(curso);
        
        // Cria o header Location com a URL do novo recurso
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCurso.getId())
                .toUri();
                
        return ResponseEntity.created(uri).body(novoCurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursosIcol> atualizar(@PathVariable Long id, @Valid @RequestBody CursosIcol curso) {
        return ResponseEntity.ok(service.atualizar(id, curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        // Chamando o método de exclusão lógica que criamos no Service
        service.inativar(id); 
        return ResponseEntity.noContent().build();
    }
}