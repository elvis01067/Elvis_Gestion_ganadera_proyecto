-- Crear la tabla de ganado si no existe
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
