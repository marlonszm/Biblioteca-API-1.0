package io.github.marlonszm.libraryapi.controller.dto;

import io.github.marlonszm.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(String nome, LocalDate data_nascimento, String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setName(this.nome);
        autor.setData_nascimento(this.data_nascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }


}
