-- Eliminar la tabla ganado si existe
DROP TABLE IF EXISTS registros_sanitarios;
DROP TABLE IF EXISTS ganado;

-- Crear la tabla de ganado
CREATE TABLE IF NOT EXISTS ganado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    arete VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(100),
    raza VARCHAR(100),
    fecha_nacimiento DATE,
    genero ENUM('macho', 'hembra'),
    peso DECIMAL(10,2),
    estado_salud ENUM('sano', 'enfermo', 'en_tratamiento'),
    proposito ENUM('carne', 'leche', 'doble_proposito'),
    estado_productivo ENUM('produccion', 'seco', 'gestacion'),
    pasto_id INT,
    madre_id INT,
    padre_id INT,
    fecha_adquisicion DATE,
    precio_compra DECIMAL(10,2),
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (pasto_id) REFERENCES pastos(id),
    FOREIGN KEY (madre_id) REFERENCES ganado(id),
    FOREIGN KEY (padre_id) REFERENCES ganado(id)
);

-- Insertar algunos datos de prueba
INSERT INTO ganado (arete, nombre, raza, genero, peso, estado_salud, proposito) VALUES
('A41', 'Vaca 1', 'Holstein', 'hembra', 450.5, 'sano', 'leche'),
('A42', 'Vaca 2', 'Jersey', 'hembra', 400.0, 'sano', 'leche');
