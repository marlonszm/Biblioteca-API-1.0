package io.github.marlonszm.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
// lombok!
// criação de getters e setters em tempo de compilação
@Getter
@Setter
@ToString(exclude = "livros")
@Table(name = "autor", schema = "public")
@EntityListeners(AuditingEntityListener.class) //observação de operações na entidade
public class Autor {

    // Uso do 'Column' para fins didáticos e realização de mapeamento.
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name= "name", nullable = false, length = 100)
    private String name;

    @Column(name= "data_nascimento", nullable = false)
    private LocalDate data_nascimento;

    @Column(name = "nacionalidade", nullable = false, length = 50)
    private String nacionalidade;
    // Um autor pode ter um ou vários livros
    @OneToMany(
            mappedBy = "autor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Livro> livros;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;
}
