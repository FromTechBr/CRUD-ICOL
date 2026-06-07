package com.br.org.icol.icolbackend.controller;

import com.br.org.icol.icolbackend.service.FrequenciaIcolService;
import com.br.org.icol.icolbackend.dto.FrequenciaRequestDTO;
import com.br.org.icol.icolbackend.dto.FrequenciaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frequencias")
public class FrequenciaIcolController {

    private final FrequenciaIcolService servicoFrequencia;

    public FrequenciaIcolController(FrequenciaIcolService service) {
        this.servicoFrequencia = service;
    }

    @GetMapping
    public ResponseEntity<List<FrequenciaResponseDTO>> listar() {
        return ResponseEntity.ok(servicoFrequencia.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FrequenciaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicoFrequencia.buscar(id));
    }

    @PostMapping
    public ResponseEntity<FrequenciaResponseDTO> registrar(@Valid @RequestBody FrequenciaRequestDTO dto) {
        FrequenciaResponseDTO novo = servicoFrequencia.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FrequenciaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FrequenciaRequestDTO dto) {
        FrequenciaResponseDTO atualizado = servicoFrequencia.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoFrequencia.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
