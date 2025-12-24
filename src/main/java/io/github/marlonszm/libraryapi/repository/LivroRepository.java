package io.github.marlonszm.libraryapi.repository;

import io.github.marlonszm.libraryapi.model.Autor;
import io.github.marlonszm.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query Method
    // sekect * from livro where id_autor = 'id'
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
    List<Livro> findByDataPublicacaoBetween(LocalDate dataPublicacao, LocalDate dataInicio, LocalDate dataFim);



}
