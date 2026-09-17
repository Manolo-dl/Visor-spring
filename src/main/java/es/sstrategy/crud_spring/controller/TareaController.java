package es.sstrategy.crud_spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sstrategy.crud_spring.dto.TareaDTO;
import es.sstrategy.crud_spring.dto.TareaMapper;
import es.sstrategy.crud_spring.repository.TareaRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaRepository tareaRepository;

    @GetMapping
    public ResponseEntity<List<TareaDTO>> obtenerTodasLasTareas() {
        return ResponseEntity.ok(
            tareaRepository.findAll()
                .stream()
                .map(TareaMapper::toDTO)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtenerTarea(@PathVariable Long id) {
        return tareaRepository.findById(id)
            .map(TareaMapper::toDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTarea(@PathVariable Long id) {
        if (!tareaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tareaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
