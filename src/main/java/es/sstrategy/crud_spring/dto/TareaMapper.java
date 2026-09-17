package es.sstrategy.crud_spring.dto;

import es.sstrategy.crud_spring.entity.Tarea;

public class TareaMapper {

    public static TareaDTO toDTO(Tarea tarea) {
        return new TareaDTO(
            tarea.getId(),
            tarea.getTitulo(),
            tarea.getDescripcion(),
            tarea.isCompletada(),
            tarea.getFechaLimite(),
            tarea.getUsuario() != null ? tarea.getUsuario().getId() : null
        );
    }
}
