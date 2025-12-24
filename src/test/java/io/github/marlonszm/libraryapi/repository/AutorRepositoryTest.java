package io.github.marlonszm.libraryapi.repository;

import io.github.marlonszm.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setName("Marlon");
        autor.setNacionalidade("brasileiro");
        autor.setData_nascimento(LocalDate.of(2005, 4, 2));
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
            autorEncontrado.setData_nascimento(LocalDate.of(1961, 3, 30));
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



}