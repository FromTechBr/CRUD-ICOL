package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.dto.AlunoRequestDTO;
import com.br.org.icol.icolbackend.dto.AlunoResponseDTO;
import com.br.org.icol.icolbackend.service.AlunosIcolService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunosIcolController {

    private final AlunosIcolService servicoAlunos;

    public AlunosIcolController(AlunosIcolService service) {
        this.servicoAlunos = service;
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> listar() {
        return ResponseEntity.ok(servicoAlunos.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicoAlunos.buscar(id));
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> criar(@Valid @RequestBody AlunoRequestDTO dto) {
        AlunoResponseDTO novo = servicoAlunos.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AlunoRequestDTO dto) {
        AlunoResponseDTO atualizado = servicoAlunos.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoAlunos.inativar(id);
        return ResponseEntity.noContent().build();
    }
}