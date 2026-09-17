package es.sstrategy.crud_spring.dto;

import java.time.LocalDate;

public record TareaDTO(
    Long id,
    String titulo,
    String descripcion,
    boolean completada,
    LocalDate fechaLimite,
    Long usuarioId
) {
}
