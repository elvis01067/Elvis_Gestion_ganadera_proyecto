-- Crear la tabla de clientes si no existe
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    cedula_ruc VARCHAR(20),
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    email VARCHAR(100),
    tipo VARCHAR(20),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado BOOLEAN DEFAULT TRUE
);
