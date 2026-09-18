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

import es.sstrategy.crud_spring.dto.UsuarioDTO;
import es.sstrategy.crud_spring.dto.UsuarioMapper;
import es.sstrategy.crud_spring.entity.Usuario;
import es.sstrategy.crud_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;

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

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
       //validar si el email ya está en la bbdd
        if (userRepository.existsByEmail(usuarioDTO.email())) {
            return ResponseEntity.badRequest().build();
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.nombre());
        usuario.setEmail(usuarioDTO.email());
        
        Usuario guardado = userRepository.save(usuario);
        return ResponseEntity.ok(UsuarioMapper.toDTO(guardado));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> borrarUsuario(@PathVariable String email) {
        return userRepository.findByEmail(email)
        .map(usuario -> {
            userRepository.deleteById(usuario.getId());
            return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}")
    public ResponseEntity<Void> actualizarUsuario(@PathVariable String email, @RequestBody UsuarioDTO usuarioDTO) {
        return userRepository.findByEmail(email)
            .map(usuario -> {
                usuario.setNombre(usuarioDTO.nombre());
                usuario.setEmail(usuarioDTO.email());
                userRepository.save(usuario);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
