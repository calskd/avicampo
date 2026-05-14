CREATE DATABASE IF NOT EXISTS avicampo_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE avicampo_db;

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) DEFAULT 'ADMIN',
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    saldo_pendiente DECIMAL(10,2) DEFAULT 0.00,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS lotes (
    id_lote BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_ingreso DATE NOT NULL,
    cantidad_inicial INT NOT NULL,
    cantidad_actual INT NOT NULL,
    costo_unitario DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    CHECK (cantidad_inicial >= 0),
    CHECK (cantidad_actual >= 0),
    CHECK (costo_unitario >= 0)
);

CREATE TABLE IF NOT EXISTS compras_lote (
    id_compra BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_lote BIGINT NOT NULL,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    costo_unitario DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    proveedor VARCHAR(150),
    CONSTRAINT fk_compra_lote FOREIGN KEY (id_lote) REFERENCES lotes(id_lote),
    CHECK (cantidad >= 0),
    CHECK (costo_unitario >= 0),
    CHECK (total >= 0)
);

CREATE TABLE IF NOT EXISTS ventas (
    id_venta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    fecha DATETIME NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    tipo_pago VARCHAR(20) NOT NULL,
    CONSTRAINT fk_venta_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    CHECK (total >= 0)
);

CREATE TABLE IF NOT EXISTS detalle_venta (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_venta BIGINT NOT NULL,
    id_lote BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES ventas(id_venta),
    CONSTRAINT fk_detalle_lote FOREIGN KEY (id_lote) REFERENCES lotes(id_lote),
    CHECK (cantidad >= 0),
    CHECK (precio_unitario >= 0),
    CHECK (subtotal >= 0)
);

CREATE TABLE IF NOT EXISTS alimentacion (
    id_alimentacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_lote BIGINT NOT NULL,
    fecha DATE NOT NULL,
    cantidad_kg DECIMAL(10,2) NOT NULL,
    costo DECIMAL(10,2) DEFAULT 0.00,
    CONSTRAINT fk_alimentacion_lote FOREIGN KEY (id_lote) REFERENCES lotes(id_lote),
    CHECK (cantidad_kg >= 0),
    CHECK (costo >= 0)
);

CREATE TABLE IF NOT EXISTS vacunas (
    id_vacuna BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE IF NOT EXISTS vacunacion_lote (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_lote BIGINT NOT NULL,
    id_vacuna BIGINT NOT NULL,
    fecha_aplicacion DATE NOT NULL,
    observaciones TEXT,
    CONSTRAINT fk_vacunacion_lote FOREIGN KEY (id_lote) REFERENCES lotes(id_lote),
    CONSTRAINT fk_vacunacion_vacuna FOREIGN KEY (id_vacuna) REFERENCES vacunas(id_vacuna)
);

CREATE TABLE IF NOT EXISTS mortalidad (
    id_mortalidad BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_lote BIGINT NOT NULL,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    motivo VARCHAR(255),
    CONSTRAINT fk_mortalidad_lote FOREIGN KEY (id_lote) REFERENCES lotes(id_lote),
    CHECK (cantidad >= 0)
);

CREATE OR REPLACE VIEW vista_dashboard AS
SELECT
    (SELECT COALESCE(SUM(cantidad_actual),0) FROM lotes) AS pollos_disponibles,
    (SELECT COALESCE(SUM(cantidad_actual * costo_unitario),0) FROM lotes) AS valor_inventario,
    (SELECT COALESCE(SUM(total),0) FROM ventas WHERE DATE(fecha)=CURDATE()) AS ventas_hoy,
    (SELECT COALESCE(SUM(total),0) FROM ventas) AS total_vendido,
    (SELECT COUNT(*) FROM clientes) AS clientes_registrados,
    (SELECT COALESCE(SUM(saldo_pendiente),0) FROM clientes) AS saldo_pendiente_total,
    (SELECT COALESCE(SUM(cantidad_kg),0) FROM alimentacion) AS alimento_total_kg,
    (SELECT nombre FROM vacunas ORDER BY id_vacuna DESC LIMIT 1) AS ultima_vacuna;
