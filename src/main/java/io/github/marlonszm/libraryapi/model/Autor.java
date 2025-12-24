package io.github.marlonszm.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
// lombok!
// criação de getters e setters em tempo de compilação
@Getter
@Setter
@ToString
@Table(name = "autor", schema = "public")
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

    //@OneToMany(mappedBy = "autor")
    @Transient
    private List<Livro> livros;
}
