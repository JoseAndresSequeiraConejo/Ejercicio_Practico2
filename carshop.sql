-- ================================
-- CREACIÓN DE BASE Y USUARIO
-- ================================
DROP SCHEMA IF EXISTS carshop;
DROP USER IF EXISTS 'usuario_prueba'@'%';

CREATE SCHEMA carshop;
CREATE USER 'usuario_prueba'@'%' IDENTIFIED BY 'Usuario_Clave.';
GRANT ALL PRIVILEGES ON carshop.* TO 'usuario_prueba'@'%';
FLUSH PRIVILEGES;
USE carshop;

-- ================================
-- TABLA: CATEGORÍA
-- ================================
-- Necesitas 'nombre' porque lo usas en los INSERT
CREATE TABLE categoria (
  id_categoria INT NOT NULL AUTO_INCREMENT,
  nombre       VARCHAR(50) NOT NULL,
  descripcion  VARCHAR(255),
  ruta_imagen  VARCHAR(1024),
  activo       TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar categorías de carros
INSERT INTO categoria (nombre, descripcion) VALUES
  ('Sedán',     'Vehículos sedán de 4 puertas'),
  ('SUV',       'Vehículos utilitarios deportivos'),
  ('Pickup',    'Camionetas de carga'),
  ('Deportivo', 'Carros deportivos y alto rendimiento'),
  ('Compacto',  'Vehículos compactos urbanos');

-- ================================
-- TABLA: CARRO
-- ================================
-- Arreglos:
--  - PK correcta: id_carro
--  - precio DECIMAL (no INT)
--  - activo TINYINT(1)
--  - FK bien nombrada y con CONSTRAINT
CREATE TABLE carro (
  id_carro     INT NOT NULL AUTO_INCREMENT,
  id_categoria INT NOT NULL,
  descripcion  TEXT NOT NULL,
  cilindros    INT NOT NULL,
  modelo       VARCHAR(100) NOT NULL,
  precio       DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  activo       TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id_carro),
  CONSTRAINT fk_carro_categoria
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
      ON UPDATE CASCADE
      ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar carros de ejemplo
-- OJO: corrige el typo (modelo, precio), agrega id_categoria obligatorio
-- Usamos: 1=Sedán, 2=SUV, 3=Pickup, 4=Deportivo, 5=Compacto
INSERT INTO carro (id_categoria, descripcion, cilindros, modelo, precio) VALUES
  (1,'Sedán compacto económico con excelente rendimiento de combustible', 4, 'Toyota Corolla 2023', 23000.00),
  (1,'Vehículo familiar espacioso con características de seguridad avanzadas', 4, 'Honda Civic 2022', 23000.00),
  (1,'Automóvil confiable con diseño aerodinámico y tecnología inteligente', 4, 'Nissan Sentra 2023', 23000.00),
  (1,'Sedán de lujo con garantía extendida y acabados premium', 4, 'Hyundai Elantra 2024', 23000.00),

  (2,'SUV compacto híbrido perfecto para aventuras familiares', 6, 'Toyota RAV4 Híbrido 2023', 35000.00),
  (2,'Vehículo utilitario deportivo con amplio espacio de carga', 4, 'Honda CR-V 2022', 32000.00),
  (2,'SUV elegante con manejo deportivo y tecnología SKYACTIV', 4, 'Mazda CX-5 2023', 33500.00),
  (2,'Vehículo todo terreno con tracción integral estándar', 4, 'Subaru Forester 2024', 34000.00),

  (3,'Pickup de trabajo pesado con motor V8 y gran remolque', 8, 'Ford F-150 2023', 55000.00),
  (3,'Camioneta resistente con cabina amplia ideal para trabajo', 8, 'Chevrolet Silverado 2022', 52000.00),
  (3,'Pickup diésel confiable con excelente reputación', 4, 'Toyota Hilux Diesel 2023', 48000.00),
  (3,'Vehículo de carga premium con interior de lujo', 8, 'Ram 1500 HEMI 2024', 60000.00),

  (4,'Deportivo icónico americano con motor V8 de alto rendimiento', 8, 'Ford Mustang GT 2023', 65000.00),
  (4,'Muscle car con diseño agresivo y potencia excepcional', 8, 'Chevrolet Camaro SS 2022', 62000.00),
  (4,'Superdeportivo alemán con ingeniería de precisión', 6, 'Porsche 911 Carrera 2024', 120000.00),
  (4,'Sedán deportivo de alta gama con motor turbo', 6, 'BMW M3 Competition 2023', 90000.00),

  (5,'Compacto urbano versátil con gran aprovechamiento del espacio', 4, 'Honda Fit 2022', 18000.00),
  (5,'Vehículo económico perfecto para uso diario en ciudad', 4, 'Nissan Versa 2023', 17000.00),
  (5,'Compacto confiable con bajo costo de mantenimiento', 4, 'Hyundai Accent 2022', 16500.00),
  (5,'Automóvil moderno con excelente relación calidad-precio', 4, 'Kia Rio 2023', 17500.00);

-- ================================
-- TABLA: USUARIO
-- ================================
CREATE TABLE usuario (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  username varchar(20) NOT NULL,
  password varchar(512) NOT NULL,
  nombre VARCHAR(20) NOT NULL,
  apellidos VARCHAR(30) NOT NULL,
  correo VARCHAR(75) NULL,
  telefono VARCHAR(15) NULL,
  ruta_imagen varchar(1024),
  activo boolean,
  PRIMARY KEY (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- ================================
-- TABLA: ROL
-- ================================
create table rol (
  id_rol INT NOT NULL AUTO_INCREMENT,
  nombre varchar(20),
  id_usuario int,
  PRIMARY KEY (id_rol),
  foreign key fk_rol_usuario (id_usuario) references usuario(id_usuario)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

insert into rol (id_rol, nombre, id_usuario) values
 (1,'ROLE_ADMIN',1), (2,'ROLE_VENDEDOR',1), (3,'ROLE_USER',1),
 (4,'ROLE_VENDEDOR',2), (5,'ROLE_USER',2),
 (6,'ROLE_USER',3);

-- Insertar usuarios de ejemplo
INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo) VALUES 
  ('jose',   '$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.', 'Jose',   'Castro Mora',    'jose@gmail.com',   '4556-8978', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Juan_Diego_Madrigal.jpg/250px-Juan_Diego_Madrigal.jpg', 1),
  ('felipe', '$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi', 'Felipe', 'Contreras Mora', 'felipe@gmail.com', '5456-8789', 'https://upload.wikimedia.org/wikipedia/commons/0/06/Photo_of_Rebeca_Arthur.jpg', 1),
  ('ian',    '$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO', 'Ian',    'Mena Loria',     'ian@gmail.com',    '7898-8936', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Eduardo_de_Pedro_2019.jpg/480px-Eduardo_de_Pedro_2019.jpg?20200109230854', 1);


-- ================================
-- TABLA: CONTACTO
-- ================================
CREATE TABLE contacto (
  id_contacto INT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  email       VARCHAR(100) NOT NULL,
  message     TEXT         NOT NULL,
  fecha_envio DATETIME     DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ================================
-- TABLAS: FACTURA Y VENTA
-- ================================
CREATE TABLE factura (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  fecha      DATE,
  total      DECIMAL(14,2),
  estado     INT,
  CONSTRAINT fk_factura_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE venta (
  id_venta   INT AUTO_INCREMENT PRIMARY KEY,
  id_factura INT NOT NULL,
  id_carro   INT NOT NULL,
  precio     DECIMAL(12,2),
  cantidad   INT,
  CONSTRAINT fk_ventas_factura FOREIGN KEY (id_factura) REFERENCES factura(id_factura),
  CONSTRAINT fk_ventas_carro   FOREIGN KEY (id_carro)   REFERENCES carro(id_carro)
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
-- CONSULTA DE VERIFICACIÓN
-- ================================
SELECT COUNT(*) AS total_carros FROM carro;
SELECT * FROM carro LIMIT 5;
