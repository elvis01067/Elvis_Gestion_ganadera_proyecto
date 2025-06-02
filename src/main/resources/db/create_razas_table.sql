CREATE TABLE IF NOT EXISTS razas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    imagen_url VARCHAR(255) NOT NULL,
    descripcion TEXT,
    estado BOOLEAN DEFAULT true,
    UNIQUE (nombre)
);
