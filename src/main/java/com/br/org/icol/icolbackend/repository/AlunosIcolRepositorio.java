package com.br.org.icol.icolbackend.repository;

import com.br.org.icol.icolbackend.model.AlunosIcol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  AlunosIcolRepositorio extends JpaRepository<AlunosIcol, Long>{
    Optional<AlunosIcol> findByCpf(String cpf);
}
