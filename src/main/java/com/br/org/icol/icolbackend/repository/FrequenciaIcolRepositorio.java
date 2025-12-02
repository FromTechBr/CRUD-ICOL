package com.br.org.icol.icolbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.org.icol.icolbackend.model.FrequenciaIcol;

@Repository
public interface FrequenciaIcolRepositorio extends JpaRepository<FrequenciaIcol, Long>{
    
}
