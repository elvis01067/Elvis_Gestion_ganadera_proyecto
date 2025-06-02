-- Crear la tabla de usuarios si no existe
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(32) NOT NULL,  -- MD5 hash length is 32 characters
    rol VARCHAR(20) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Limpiar datos existentes
DELETE FROM usuarios;

-- Insertar usuarios de prueba
INSERT INTO usuarios (nombre_usuario, contrasena, rol, estado) VALUES
('admin', MD5('admin'), 'ADMIN', TRUE),
('veterinario', MD5('1234'), 'VETERINARIO', TRUE),
('operario', MD5('1234'), 'OPERARIO', TRUE);
