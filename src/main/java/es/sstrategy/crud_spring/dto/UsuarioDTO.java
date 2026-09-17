package es.sstrategy.crud_spring.dto;

import java.util.List;

public record UsuarioDTO(
    Long id,
    String nombre,
    String email,
    List<TareaDTO> tareas
) {

}
