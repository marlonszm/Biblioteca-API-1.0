package io.github.marlonszm.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "livro")
// @Getter e @Setter; @ToString; @EqualsAndHashcode; @RequiredArgsConstructor @AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = "autor")
public class Livro {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name= "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name= "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name= "genero", length= 30, nullable = false)
    private GeneroLivro genero;

    @Column(name= "preco", nullable = false, precision = 18, scale = 2)
    private BigDecimal preco;

    @JoinColumn(name= "id_autor")
    // Um autor pode ter um ou mais livros
    @ManyToOne(
            //(cascade = CascadeType.ALL)
            fetch = FetchType.LAZY
    )
    private Autor autor;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

}
