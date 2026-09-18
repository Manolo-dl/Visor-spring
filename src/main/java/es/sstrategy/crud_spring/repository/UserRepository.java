package es.sstrategy.crud_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.sstrategy.crud_spring.entity.Usuario;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    //validación cuando se cree un nuevo user
    boolean existsByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
