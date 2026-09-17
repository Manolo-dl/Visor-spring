package es.sstrategy.crud_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sstrategy.crud_spring.entity.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
