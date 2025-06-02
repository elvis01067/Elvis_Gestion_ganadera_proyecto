-- Crear la tabla de facturas
CREATE TABLE IF NOT EXISTS facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(20) UNIQUE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_documento ENUM('FACTURA_VENTA', 'FACTURA_COMPRA'),
    cliente_id INT,
    proveedor_id INT,
    subtotal DECIMAL(10,2),
    iva_porcentaje DECIMAL(5,2),
    iva_valor DECIMAL(10,2),
    descuento_porcentaje DECIMAL(5,2),
    descuento_valor DECIMAL(10,2),
    total DECIMAL(10,2),
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id),
    CHECK (
        (tipo_documento = 'FACTURA_VENTA' AND cliente_id IS NOT NULL AND proveedor_id IS NULL) OR
        (tipo_documento = 'FACTURA_COMPRA' AND proveedor_id IS NOT NULL AND cliente_id IS NULL)
    )
);

-- Crear la tabla de detalles de factura
CREATE TABLE IF NOT EXISTS detalles_factura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    factura_id INT,
    concepto VARCHAR(200),
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    FOREIGN KEY (factura_id) REFERENCES facturas(id)
);
