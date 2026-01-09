package io.github.marlonszm.libraryapi.repository;

import io.github.marlonszm.libraryapi.model.Autor;
import io.github.marlonszm.libraryapi.model.GeneroLivro;
import io.github.marlonszm.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query Methods (seguir sintaxe camel case e de acordo com as palavras chave disponibilizadas)

    // select * from livro where id_autor = 'id'
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = 'titulo'
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = 'isbn'
    List<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = 'titulo' and preco = 'preco'
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    //select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate dataInicio, LocalDate dataFim);

    //JPQL -> referencia as entidades e as propriedades
    // select l.* from livro as l order by l.titulo
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarLivrosOrdenadoPorTituloAndPreco();

    @Query(" select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query(" select distinct l.titulo from Livro l")
    List<String> listarNomesDiferenteslivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'brasileiro'
        order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();

    // named parameters -> parametros nomeados (queries mais complexas)
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro,
                             @Param("paramOrdenacao")  String nomeDaPropriedade);

    // positional parameters -> parametros posicionais (queries mais simples)
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositional(GeneroLivro generoLivro, String nomeDaPropriedade);

    //Operações de escrita necessitam das anotações @Modifying e @Transactional
    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);


}
