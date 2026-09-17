INSERT INTO usuarios (nombre, email) VALUES
('Manolo', 'manolo@ejemplo.com'),
('Sara', 'sara@ejemplo.com');

INSERT INTO tareas (titulo, descripcion, completada, fecha_limite, usuario_id) VALUES
('Terminar CRUD', 'Acabar el backend de tareas', FALSE, '2026-09-25', 1),
('Revisar seguridad', 'Configurar Spring Security', FALSE, '2026-09-30', 1),
('Documentar API', NULL, TRUE, '2026-09-15', 2),
('Sin asignar', 'Tarea de ejemplo sin usuario', FALSE, NULL, NULL);