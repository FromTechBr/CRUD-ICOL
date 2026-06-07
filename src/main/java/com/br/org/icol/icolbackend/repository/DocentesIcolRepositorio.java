package com.br.org.icol.icolbackend.repository;

import com.br.org.icol.icolbackend.model.DocentesIcol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocentesIcolRepositorio extends JpaRepository<DocentesIcol, Long> {
    // Busca docente pelo nome (para validação ou pesquisa)
    Optional<DocentesIcol> findByNomeCompleto(String nomeCompleto);
    
    // Busca docente pelo ID do usuário vinculado (útil para login)
    @Query("SELECT d FROM DocentesIcol d WHERE d.usuario.id = :usuarioId")
    Optional<DocentesIcol> findByUsuario(@Param("usuarioId") Long usuarioId);
}