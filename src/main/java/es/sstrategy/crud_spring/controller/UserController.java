package es.sstrategy.crud_spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sstrategy.crud_spring.dto.UsuarioDTO;
import es.sstrategy.crud_spring.dto.UsuarioMapper;
import es.sstrategy.crud_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor 
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        return ResponseEntity.ok(
            userRepository.findAll()
            .stream()
            .map(UsuarioMapper::toDTO)
            .toList()
        );
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable String email) {
        return userRepository.findByEmail(email)
            .map(UsuarioMapper::toDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{email}")
    public ResponseEntity<Void> borrarUsuario(@PathVariable String email) {
        userRepository.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
