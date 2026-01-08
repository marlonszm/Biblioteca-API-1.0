package io.github.marlonszm.libraryapi.service;

import io.github.marlonszm.libraryapi.model.Autor;
import io.github.marlonszm.libraryapi.model.GeneroLivro;
import io.github.marlonszm.libraryapi.model.Livro;
import io.github.marlonszm.libraryapi.repository.AutorRepository;
import io.github.marlonszm.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service

public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualizarSemAtualizar(){
        UUID id = UUID.fromString("f0d1b6b0-5582-4527-85af-e594ea398d8b");
        var livro = livroRepository.findById(id).orElse(null);

        // Existindo uma janela de transação (@Transactional), não é obrigatória a utilização do repository.save()
        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));
    }



    @Transactional
    public void executar(){
        // Salva o autor
        Autor autor = new Autor();
        autor.setName("Teste Francisco");
        autor.setNacionalidade("brasileiro");
        autor.setDataNascimento(LocalDate.of(1989, 9, 21));

        autorRepository.save(autor);


        // Salva o livro
        Livro livro = new Livro();
        livro.setIsbn("90538-84874");
        livro.setPreco(BigDecimal.valueOf(70));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Darwin: a farsa");
        livro.setDataPublicacao(LocalDate.of(2025, 3, 29));

        livro.setAutor(autor);

        // ver a ooperação acontecendo (saveAndFlush)
        livroRepository.saveAndFlush(livro);

        if(autor.getName().equals("Teste Francisco")) {
            throw new RuntimeException("Rollback!");
        }

    }

}
