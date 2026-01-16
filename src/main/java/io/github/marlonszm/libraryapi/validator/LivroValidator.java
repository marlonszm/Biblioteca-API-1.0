package io.github.marlonszm.libraryapi.validator;

import io.github.marlonszm.libraryapi.exceptions.CampoInvalidoException;
import io.github.marlonszm.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.marlonszm.libraryapi.model.Livro;
import io.github.marlonszm.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;
    private static final int ANO_EXIGENCIA_PRECO = 2020;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null
                && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn (Livro livro){
        Optional<Livro> livroEncontrado = livroRepository.findLivroByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream().anyMatch(id -> !id.equals(livro.getId()));
    }

}
