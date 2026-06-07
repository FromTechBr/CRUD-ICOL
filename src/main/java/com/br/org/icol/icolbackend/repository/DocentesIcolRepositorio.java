package com.br.org.icol.icolbackend.repository;

import com.br.org.icol.icolbackend.model.DocentesIcol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocentesIcolRepositorio extends JpaRepository<DocentesIcol, Long> {
    // Busca docente pelo nome (para validação ou pesquisa) apenas se ativo
    @Query("SELECT d FROM DocentesIcol d WHERE d.nomeCompleto = :nomeCompleto AND d.ativo = true")
    Optional<DocentesIcol> findByNomeCompleto(@Param("nomeCompleto") String nomeCompleto);
    
    // Busca docente pelo ID do usuário vinculado (útil para login) apenas se ativo
    @Query("SELECT d FROM DocentesIcol d WHERE d.usuario.id = :usuarioId AND d.ativo = true")
    Optional<DocentesIcol> findByUsuario(@Param("usuarioId") Long usuarioId);

    // Lista todos os docentes ativos
    @Query("SELECT d FROM DocentesIcol d WHERE d.ativo = true ORDER BY d.nomeCompleto ASC")
    java.util.List<DocentesIcol> findAllAtivos();
    
    // Busca docente por ID apenas se ativo
    @Query("SELECT d FROM DocentesIcol d WHERE d.id = :id AND d.ativo = true")
    Optional<DocentesIcol> findByIdAndAtivo(@Param("id") Long id);

    boolean existsByUsuario_Id(Long usuarioId);
}