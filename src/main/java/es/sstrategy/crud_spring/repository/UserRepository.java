package es.sstrategy.crud_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sstrategy.crud_spring.entity.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long> {
}
