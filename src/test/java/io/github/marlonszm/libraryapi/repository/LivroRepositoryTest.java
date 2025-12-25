package io.github.marlonszm.libraryapi.repository;

import io.github.marlonszm.libraryapi.model.Autor;
import io.github.marlonszm.libraryapi.model.GeneroLivro;
import io.github.marlonszm.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;

    // Salvar sem ser em cascata
    @Test
    public void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84875");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Bolsonaro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
        Autor autor = autorRepository.findById(UUID.fromString("1647fe83-3463-4378-a68e-96416063dc43")).orElse(null);
        //livro.setAutor(autor);

        livroRepository.save(livro);
    }

    //Salvar Autor e Livro
    @Test
    public void salvarAutoreLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90888-84874");
        livro.setPreco(BigDecimal.valueOf(70));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Biblia KJV");
        livro.setDataPublicacao(LocalDate.of(2023, 7, 2));

        Autor autor = new Autor();
        autor.setName("Thomas Nelson");
        autor.setNacionalidade("Inglês");
        autor.setData_nascimento(LocalDate.of(2000, 5, 10));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    // Salvar em cascata
    @Test
    public void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90888-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("OFU");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setName("Bolsonaro");
        autor.setNacionalidade("brasileiro");
        autor.setData_nascimento(LocalDate.of(1958, 4, 9));
        livro.setAutor(autor);

        //Não é necessario salvar o autor utilizando o autorRepository nesse caso!
        livroRepository.save(livro);
    }

    @Test
    public void atualizarAutorTest(){
        UUID id = UUID.fromString("d54de137-5dd0-47b4-9a09-a1c7727ad34e");
        var livroParaAtualizar = livroRepository.findById(id)
                .orElse(null);

        UUID idAutor = UUID.fromString("e14927f0-f20c-462e-8211-4b3e98badce2");
        Autor nelson = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(nelson);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    public void deletarTest(){
        UUID id = UUID.fromString("2ab985b3-08e2-42cf-a3ea-44ac594ae159");
        livroRepository.deleteById(id);
    }

    @Test
    public void buscarLivroTest(){
        UUID id = UUID.fromString("d54de137-5dd0-47b4-9a09-a1c7727ad34e");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getName());
    }

    @Test
    public void pesquisaPorTituloTest(){
        List<Livro> lista = livroRepository.findByTitulo("Biblia KJV");
        lista.forEach(System.out::println);
    }

    @Test
    public void pesquisaPorIsbnTest(){
        List<Livro> lista = livroRepository.findByIsbn("91888-84874");
        lista.forEach(System.out::println);
    }

    @Test
    public void pesquisaPorTituloAndPrecoTest(){
        var preco = BigDecimal.valueOf(70.00);
        String titulo = "Biblia KJV";
        List<Livro> lista = livroRepository.findByTituloAndPreco(titulo, preco);
        lista.forEach(System.out::println);
    }

    @Test
    public void listarLivrosComqQueryJPQLTest(){
        var resultado = livroRepository.listarLivrosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarAutoresDosLivrosTest(){
        var resultado = livroRepository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarNomesDiferenteslivrosTest(){
        var resultado = livroRepository.listarNomesDiferenteslivros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarGenerosDeLivrosAutoresBrasileirosTest(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarPorGeneroQueryParamTest(){
        var resultado = livroRepository.findByGenero(GeneroLivro.BIOGRAFIA, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarPorGeneroQueryPositionalTest(){
        var resultado = livroRepository.findByGenero(GeneroLivro.BIOGRAFIA, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    public void deletePorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    public void updateDataPublicacaoTest(){
        livroRepository.updateDataPublicacao(LocalDate.of(2000, 1 , 1));
    }

}