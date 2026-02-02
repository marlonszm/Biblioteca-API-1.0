package io.github.marlonszm.libraryapi.controller.mappers;

import io.github.marlonszm.libraryapi.controller.dto.UsuarioDTO;
import io.github.marlonszm.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

}
