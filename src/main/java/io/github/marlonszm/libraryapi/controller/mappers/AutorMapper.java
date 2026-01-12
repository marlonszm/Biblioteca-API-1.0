package io.github.marlonszm.libraryapi.controller.mappers;

import ch.qos.logback.core.model.ComponentModel;
import io.github.marlonszm.libraryapi.controller.dto.AutorDTO;
import io.github.marlonszm.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);
    AutorDTO toDTO(Autor autor);

}
