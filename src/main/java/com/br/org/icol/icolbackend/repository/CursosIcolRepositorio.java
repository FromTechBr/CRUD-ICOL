package com.br.org.icol.icolbackend.repository;

import com.br.org.icol.icolbackend.model.CursosIcol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursosIcolRepositorio extends JpaRepository<CursosIcol, Long>{
    Optional<CursosIcol>findByNomeCurso(String nomeCurso);
}
