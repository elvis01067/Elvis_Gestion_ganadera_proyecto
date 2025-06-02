-- Crear la tabla de pastos si no existe
CREATE TABLE IF NOT EXISTS pastos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    area_hectareas DECIMAL(10,2) NOT NULL,
    estado_pasto ENUM('Disponible', 'Ocupado', 'Mantenimiento') NOT NULL DEFAULT 'Disponible',
    capacidad_animales INT NOT NULL,
    fecha_ultima_rotacion DATE,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
