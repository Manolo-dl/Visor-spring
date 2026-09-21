package es.sstrategy.crud_spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sstrategy.crud_spring.dto.TareaDTO;
import es.sstrategy.crud_spring.dto.TareaMapper;
import es.sstrategy.crud_spring.entity.Tarea;
import es.sstrategy.crud_spring.entity.Usuario;
import es.sstrategy.crud_spring.repository.TareaRepository;
import es.sstrategy.crud_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaRepository tareaRepository;
    private final UserRepository userRepository;

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

    @PostMapping
    public ResponseEntity<TareaDTO> crearTarea(@RequestBody TareaDTO tareaDTO) {
        Tarea tarea = new Tarea();

        if (!aplicarDatos(tarea, tareaDTO)) {
            return ResponseEntity.badRequest().build();
        }

        Tarea guardada = tareaRepository.save(tarea);
        return ResponseEntity.ok(TareaMapper.toDTO(guardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizarTarea(@PathVariable Long id, @RequestBody TareaDTO tareaDTO) {
        return tareaRepository.findById(id)
            .map(tarea -> {
                if (!aplicarDatos(tarea, tareaDTO)) {
                    return ResponseEntity.badRequest().<TareaDTO>build();
                }
                return ResponseEntity.ok(TareaMapper.toDTO(tareaRepository.save(tarea)));
            })
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

    //copia los datos del dto a la entidad, false si el dto no es válido
    private boolean aplicarDatos(Tarea tarea, TareaDTO tareaDTO) {
        if (tareaDTO.titulo() == null || tareaDTO.titulo().isBlank()) {
            return false;
        }

        //la tarea puede quedarse sin usuario, pero si viene uno tiene que existir
        if (tareaDTO.usuarioId() == null) {
            tarea.setUsuario(null);
        } else {
            Usuario usuario = userRepository.findById(tareaDTO.usuarioId()).orElse(null);
            if (usuario == null) {
                return false;
            }
            tarea.setUsuario(usuario);
        }

        tarea.setTitulo(tareaDTO.titulo());
        tarea.setDescripcion(tareaDTO.descripcion());
        tarea.setCompletada(tareaDTO.completada());
        tarea.setFechaLimite(tareaDTO.fechaLimite());
        return true;
    }
}
