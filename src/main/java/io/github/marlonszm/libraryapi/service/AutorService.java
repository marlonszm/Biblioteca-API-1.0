package io.github.marlonszm.libraryapi.service;

import io.github.marlonszm.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.marlonszm.libraryapi.model.Autor;
import io.github.marlonszm.libraryapi.repository.AutorRepository;
import io.github.marlonszm.libraryapi.repository.LivroRepository;
import io.github.marlonszm.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Construtor gerado pelo lombok
@RequiredArgsConstructor
@Service
public class AutorService {

    @Autowired
    private final AutorRepository autorRepository;

    @Autowired
    private final AutorValidator autorValidator;

    @Autowired
    private final LivroRepository livroRepository;


    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional <Autor> visualizarPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um autor que possui livros cadastrados");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return autorRepository.findByNameAndNacionalidade(nome, nacionalidade);
        }
        if(nome != null){
            return autorRepository.findByName(nome);
        }
        if(nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base");
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

}
