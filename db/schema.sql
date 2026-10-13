-- Esquema de la base de datos de RepartidorYa (MariaDB / MySQL)
-- Ejecutar en phpMyAdmin (pestana SQL) o con el cliente mysql.

CREATE DATABASE IF NOT EXISTS repartidorya
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE repartidorya;

CREATE TABLE tipo_producto (
    nombre VARCHAR(100) PRIMARY KEY,
    precio DECIMAL(10,2) NOT NULL
);

CREATE TABLE cliente (
    telefono VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    domicilio VARCHAR(200) NOT NULL
);

CREATE TABLE pedido (
    fecha DATE NOT NULL,
    numero_pedido INT NOT NULL,
    cliente_telefono VARCHAR(20) NOT NULL,
    distancia INT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    PRIMARY KEY (fecha, numero_pedido),
    FOREIGN KEY (cliente_telefono) REFERENCES cliente(telefono)
);

CREATE TABLE item_pedido (
    fecha DATE NOT NULL,
    numero_pedido INT NOT NULL,
    producto VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (fecha, numero_pedido, producto),
    FOREIGN KEY (fecha, numero_pedido) REFERENCES pedido(fecha, numero_pedido),
    FOREIGN KEY (producto) REFERENCES tipo_producto(nombre)
);
