package io.github.marlonszm.libraryapi.repository;

import io.github.marlonszm.libraryapi.model.Autor;
import io.github.marlonszm.libraryapi.model.GeneroLivro;
import io.github.marlonszm.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    @Autowired
    LivroRepository livroRepository;


    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setName("Marlon");
        autor.setNacionalidade("brasileiro");
        autor.setDataNascimento(LocalDate.of(2005, 4, 2));
        repository.save(autor);
    }

    //d878e330-9e36-4fbc-af8c-2f3e29a846a8
    @Test
    public void atualizarTest() {
        var id = UUID.fromString("d878e330-9e36-4fbc-af8c-2f3e29a846a8");
        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Autor: " + autorEncontrado);
            autorEncontrado.setDataNascimento(LocalDate.of(1961, 3, 30));
            repository.save(autorEncontrado);
        }

    }

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id  = UUID.fromString("11033534-3753-4097-97ed-6a58afdd60d3");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("d878e330-9e36-4fbc-af8c-2f3e29a846a8");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setName("Paul Mccartney");
        autor.setNacionalidade("Inglês");
        autor.setDataNascimento(LocalDate.of(1942, 10, 24));

        Livro livro = new Livro();
        livro.setIsbn("91888-84874");
        livro.setPreco(BigDecimal.valueOf(70));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Paul Mccartney: de Liverpool a mundo");
        livro.setDataPublicacao(LocalDate.of(2010, 5, 21));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("91888-84875");
        livro2.setPreco(BigDecimal.valueOf(70));
        livro2.setGenero(GeneroLivro.BIOGRAFIA);
        livro2.setTitulo("Como aprendi a ser um baixista de elite!");
        livro2.setDataPublicacao(LocalDate.of(2013, 9, 11));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        //livroRepository.saveAll(autor.getLivros());

    }

    @Test
    @Transactional
    public void listarLivrosAutor(){
        var id = UUID.fromString("5e53470d-7014-43e0-91bc-dcd5a8a6dd35");
        var autor = repository.findById(id).get();

        //buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }



}