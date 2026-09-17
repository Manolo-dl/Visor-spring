package es.sstrategy.crud_spring.dto;

import java.util.List;

import es.sstrategy.crud_spring.entity.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        List<TareaDTO> tareasDTO = usuario.getTareas().stream()
            .map(TareaMapper::toDTO)
            .toList();

        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getEmail(),
            tareasDTO
        );
    }
}
