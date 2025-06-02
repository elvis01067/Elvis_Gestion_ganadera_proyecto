-- Crear la tabla de registros sanitarios
CREATE TABLE IF NOT EXISTS registros_sanitarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ganado_id INT NOT NULL,
    tipo_registro ENUM('VITAMINA', 'TRATAMIENTO', 'DESPARACITACION') NOT NULL,
    fecha DATE NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    producto_usado VARCHAR(100) NOT NULL,
    dosis VARCHAR(50) NOT NULL,
    proximo_tratamiento DATE,
    observaciones TEXT,
    estado BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ganado_id) REFERENCES ganado(id)
);
