package com.br.org.icol.icolbackend.repository;

import com.br.org.icol.icolbackend.model.AlunosIcol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  AlunosIcolRepositorio extends JpaRepository<AlunosIcol, Long>{
    Optional<AlunosIcol> findByCpf(String cpf);
    
    // Busca por CPF apenas entre alunos ativos
    @Query("SELECT a FROM AlunosIcol a WHERE a.cpf = :cpf AND a.ativo = true")
    Optional<AlunosIcol> findByCpfAndAtivo(@Param("cpf") String cpf);
    
    // Lista todos os alunos ativos
    @Query("SELECT a FROM AlunosIcol a WHERE a.ativo = true ORDER BY a.nomeCompleto ASC")
    List<AlunosIcol> findAllAtivos();
    
    // Busca aluno por ID apenas se ativo
    @Query("SELECT a FROM AlunosIcol a WHERE a.id = :id AND a.ativo = true")
    Optional<AlunosIcol> findByIdAndAtivo(@Param("id") Long id);
}
