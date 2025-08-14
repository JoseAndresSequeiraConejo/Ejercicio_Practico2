-- ================================
-- CREACIÓN DE BASE Y USUARIO
-- ================================
DROP SCHEMA IF EXISTS cine;
DROP USER IF EXISTS 'usuario_prueba'@'%';

CREATE SCHEMA cine;
CREATE USER 'usuario_prueba'@'%' IDENTIFIED BY 'Usuario_Clave.';
GRANT ALL PRIVILEGES ON cine.* TO 'usuario_prueba'@'%';
FLUSH PRIVILEGES;
USE cine;

-- ================================
-- TABLA: TEATRO
-- ================================
CREATE TABLE teatro (
  id_teatro INT NOT NULL AUTO_INCREMENT,
  nombre    VARCHAR(50) NOT NULL,
  ubicacion VARCHAR(255),
  activo    TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id_teatro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar teatros
INSERT INTO teatro (nombre, ubicacion) VALUES
  ('Cinemark','Mall Oxigeno'),
  ('Cinepolis','Paseo de las flores');

-- ================================
-- TABLA: FUNCION
-- ================================
CREATE TABLE funcion (
  id_funcion  INT NOT NULL AUTO_INCREMENT,
  id_teatro   INT NOT NULL,
  nombre      VARCHAR(50)  NOT NULL,
  descripcion VARCHAR(255) NOT NULL,
  precio      double,
  tipo        VARCHAR(50) NULL,
  activo      TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id_funcion),
  CONSTRAINT fk_funcion_teatro
    FOREIGN KEY (id_teatro) REFERENCES teatro(id_teatro)
      ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Manteniendo los mismos datos (precio numérico)
INSERT INTO funcion (id_teatro, nombre, descripcion, precio) VALUES
  (1,'Avengers Endgame','Es una pelicula',4000),
  (1,'Avengers Endgame','Es una pelicula',4000),
  (1,'Avengers Endgame','Es una pelicula',4000),
  (1,'Avengers Endgame','Es una pelicula',4000);

-- ================================
-- TABLA: USUARIO
-- ================================
CREATE TABLE usuario (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  username   VARCHAR(20)  NOT NULL,
  password   VARCHAR(512) NOT NULL,
  nombre     VARCHAR(20)  NOT NULL,
  apellidos  VARCHAR(30)  NOT NULL,
  correo     VARCHAR(75),
  telefono   VARCHAR(15),
  ruta_imagen VARCHAR(1024),
  activo     TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar usuarios de ejemplo (antes de roles para respetar FKs)
INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo) VALUES 
  ('jose',   '$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.', 'Jose',   'Castro Mora',    'jose@gmail.com',   '4556-8978', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Juan_Diego_Madrigal.jpg/250px-Juan_Diego_Madrigal.jpg', 1),
  ('felipe', '$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi', 'Felipe', 'Contreras Mora', 'felipe@gmail.com', '5456-8789', 'https://upload.wikimedia.org/wikipedia/commons/0/06/Photo_of_Rebeca_Arthur.jpg', 1),
  ('ian',    '$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO', 'Ian',    'Mena Loria',     'ian@gmail.com',    '7898-8936', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Eduardo_de_Pedro_2019.jpg/480px-Eduardo_de_Pedro_2019.jpg?20200109230854', 1);

-- ================================
-- TABLA: ROL
-- ================================
CREATE TABLE rol (
  id_rol    INT NOT NULL AUTO_INCREMENT,
  nombre    VARCHAR(20) NOT NULL,
  id_usuario INT NOT NULL,
  PRIMARY KEY (id_rol),
  CONSTRAINT fk_rol_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
      ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar roles (manteniendo IDs provistos)
INSERT INTO rol (id_rol, nombre, id_usuario) VALUES
 (1,'ROLE_ADMIN',1), (2,'ROLE_VENDEDOR',1), (3,'ROLE_USER',1),
 (4,'ROLE_VENDEDOR',2), (5,'ROLE_USER',2),
 (6,'ROLE_USER',3);

-- ================================
-- TABLA: CONTACTO
-- ================================
CREATE TABLE contacto (
  id_contacto INT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  email       VARCHAR(100) NOT NULL,
  message     TEXT NOT NULL,
  fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ================================
-- TABLAS: FACTURA Y RESERVAS
-- ================================
CREATE TABLE factura (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  fecha      DATE,
  total      DECIMAL(14,2),
  estado     INT,
  CONSTRAINT fk_factura_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
      ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE venta (
  id_venta INT AUTO_INCREMENT PRIMARY KEY,
  id_factura  INT NOT NULL,
  id_funcion  INT NOT NULL,
  precio      DECIMAL(12,2) NOT NULL,
  cantidad    INT NOT NULL DEFAULT 1,
  CONSTRAINT fk_venta_factura
    FOREIGN KEY (id_factura) REFERENCES factura(id_factura)
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_venta_funcion
    FOREIGN KEY (id_funcion) REFERENCES funcion(id_funcion)
      ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ================================
-- TABLAS: REQUEST MATCHERS (SEGURIDAD)
-- ================================
CREATE TABLE request_matcher (
  id_request_matcher INT AUTO_INCREMENT PRIMARY KEY,
  pattern            VARCHAR(255) NOT NULL,
  role_name          VARCHAR(50)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO request_matcher (pattern, role_name) VALUES
  ('/carro/nuevo',        'ADMIN'),
  ('/carro/guardar',      'ADMIN'),
  ('/carro/modificar/**', 'ADMIN'),
  ('/carro/eliminar/**',  'ADMIN'),
  ('/categoria/nuevo',    'ADMIN'),
  ('/categoria/guardar',  'ADMIN'),
  ('/categoria/modificar/**', 'ADMIN'),
  ('/categoria/eliminar/**',  'ADMIN'),
  ('/usuario/nuevo',      'ADMIN'),
  ('/usuario/guardar',    'ADMIN'),
  ('/usuario/modificar/**', 'ADMIN'),
  ('/usuario/eliminar/**',  'ADMIN'),
  ('/reportes/**',        'ADMIN'),
  ('/carro/listado',      'VENDEDOR'),
  ('/categoria/listado',  'VENDEDOR'),
  ('/usuario/listado',    'VENDEDOR'),
  ('/facturar/carrito',   'USER');

CREATE TABLE request_matcher_all (
  id_request_matcher INT AUTO_INCREMENT PRIMARY KEY,
  pattern            VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO request_matcher_all (pattern) VALUES
  ('/'),
  ('/index'),
  ('/errores/**'),
  ('/carrito/**'),
  ('/pruebas/**'),
  ('/reportes/**'),
  ('/registro/**'),
  ('/js/**'),
  ('/webjars/**');

-- ================================
-- CONSULTAS DE VERIFICACIÓN
-- ================================
-- Total de teatros
SELECT COUNT(*) AS total_teatros FROM teatro;
-- Listado rápido de funciones
SELECT id_funcion, id_teatro, nombre, precio FROM funcion LIMIT 5;
