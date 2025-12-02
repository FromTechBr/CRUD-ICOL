package com.br.org.icol.icolbackend.model;

import java.time.*;
import jakarta.persistence.*;
import lombok.Data;

/*● Propósito: Armazenar os posts e comunicados do mural de
informações.
● Colunas: id (Chave Primária), autor_id (Chave Estrangeira para
Usuarios.id), titulo (TEXT), conteudo (TEXT), data_postagem
(TIMESTAMP). */

@Data
@Entity
@Table(name="avisos_mural")
public class AvisosMuralIcol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private UsuariosIcol autorId;

    @Column(nullable=false)
    private String titulo;
    @Column(nullable=false)
    private String conteudo;
    @Column(nullable=false)
    private LocalDateTime dataPostagem;
}
