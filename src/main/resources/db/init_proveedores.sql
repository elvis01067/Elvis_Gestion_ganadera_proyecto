-- Crear la tabla de proveedores si no existe
CREATE TABLE IF NOT EXISTS proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    cedula_ruc VARCHAR(20),
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    email VARCHAR(100),
    tipo_producto ENUM('alimentos', 'medicina', 'equipos'),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado BOOLEAN DEFAULT TRUE
);
