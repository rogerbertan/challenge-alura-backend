-- NOT NULL constraints on usuarios
ALTER TABLE usuarios ALTER COLUMN nome SET NOT NULL;
ALTER TABLE usuarios ALTER COLUMN email SET NOT NULL;

-- UNIQUE constraint on email
ALTER TABLE usuarios ADD CONSTRAINT uq_usuarios_email UNIQUE (email);

-- NOT NULL constraints on salas
ALTER TABLE salas ALTER COLUMN nome SET NOT NULL;
ALTER TABLE salas ALTER COLUMN capacidade SET NOT NULL;

-- NOT NULL constraints on reservas
ALTER TABLE reservas ALTER COLUMN usuario_id SET NOT NULL;
ALTER TABLE reservas ALTER COLUMN sala_id SET NOT NULL;
ALTER TABLE reservas ALTER COLUMN data_hora_inicio SET NOT NULL;
ALTER TABLE reservas ALTER COLUMN data_hora_fim SET NOT NULL;
ALTER TABLE reservas ALTER COLUMN numero_pessoas SET NOT NULL;
ALTER TABLE reservas ALTER COLUMN status_reserva SET NOT NULL;

-- Indexes on fk in reservas
CREATE INDEX idx_reservas_usuario_id ON reservas (usuario_id);
CREATE INDEX idx_reservas_sala_id ON reservas (sala_id);