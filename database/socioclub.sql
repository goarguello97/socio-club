-- =====================================================================
-- SocioClub - Club Social y Deportivo Los Algarrobos
-- Script de creación de la base y datos iniciales (sección 3.3 del TP)
-- =====================================================================

SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS socioclub
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE socioclub;

DROP TABLE IF EXISTS inscripciones;
DROP TABLE IF EXISTS socios;
DROP TABLE IF EXISTS actividades;

-- ---------------------------------------------------------------------
-- Tablas
-- ---------------------------------------------------------------------

CREATE TABLE actividades (
  id             BIGINT        NOT NULL AUTO_INCREMENT,
  nombre         VARCHAR(80)   NOT NULL,
  disciplina     VARCHAR(40)   NOT NULL,
  profesor       VARCHAR(80)   NOT NULL,
  dias           VARCHAR(40)   NOT NULL,
  horario        VARCHAR(5)    NOT NULL,
  edad_minima    INT           NOT NULL,
  edad_maxima    INT           NOT NULL,
  cupo_maximo    INT           NOT NULL,
  cuota_mensual  DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE socios (
  id                BIGINT       NOT NULL AUTO_INCREMENT,
  dni               VARCHAR(8)   NOT NULL,
  nombre            VARCHAR(60)  NOT NULL,
  apellido          VARCHAR(60)  NOT NULL,
  fecha_nacimiento  DATE         NOT NULL,
  email             VARCHAR(120),
  telefono          VARCHAR(30),
  estado_cuota      VARCHAR(10)  NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_socios_dni UNIQUE (dni)
);

-- Tabla intermedia con datos propios (fecha y estado):
-- un socio tiene muchas inscripciones y una actividad tiene muchas inscripciones.
CREATE TABLE inscripciones (
  id                 BIGINT      NOT NULL AUTO_INCREMENT,
  fecha_inscripcion  DATE        NOT NULL,
  estado             VARCHAR(10) NOT NULL,
  socio_id           BIGINT      NOT NULL,
  actividad_id       BIGINT      NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_inscripciones_socio     FOREIGN KEY (socio_id)     REFERENCES socios (id),
  CONSTRAINT fk_inscripciones_actividad FOREIGN KEY (actividad_id) REFERENCES actividades (id)
);

-- ---------------------------------------------------------------------
-- Actividades (datos obligatorios del enunciado)
-- ---------------------------------------------------------------------

INSERT INTO actividades (id, nombre, disciplina, profesor, dias, horario, edad_minima, edad_maxima, cupo_maximo, cuota_mensual) VALUES
  (1, 'Fútbol infantil',   'Fútbol',    'Diego Ferreyra', 'Lun y Mié',      '18:00',  6, 12, 40, 18000),
  (2, 'Vóley mixto',       'Vóley',     'Laura Ceballos', 'Mar y Jue',      '19:00', 13, 60, 24, 20000),
  (3, 'Básquet juvenil',   'Básquet',   'Martín Oviedo',  'Lun y Vie',      '19:30', 12, 17, 20, 19000),
  (4, 'Natación',          'Natación',  'Sofía Bustos',   'Mar y Jue',      '17:00',  5, 70, 30, 26000),
  (5, 'Patín artístico',   'Patín',     'Carla Peralta',  'Mié y Vie',      '17:30',  5, 15, 25, 21000),
  (6, 'Ajedrez',           'Ajedrez',   'Raúl Giménez',   'Sáb',            '10:00',  8, 99, 16,  9000),
  (7, 'Funcional adultos', 'Fitness',   'Paula Luna',     'Lun, Mié y Vie', '20:30', 18, 65, 30, 22000),
  (8, 'Yoga',              'Bienestar', 'Inés Arguello',  'Mar y Jue',      '09:00', 16, 99, 15, 17000);

-- ---------------------------------------------------------------------
-- Socios de prueba (20). Pensados para demostrar cada regla:
--   * MOROSOS: López (4) y Torres (10)                          -> RN4
--   * Gómez (1) ya tiene 3 actividades                          -> RN5
--   * Castro (16) tiene 14 años: no entra en Fútbol infantil    -> RN3
--   * Yoga tiene 14 de 15 lugares ocupados (cupo casi completo) -> RN1
--   * Vega (20) no tiene actividades (pantalla vacía en la app)
-- ---------------------------------------------------------------------

INSERT INTO socios (id, dni, nombre, apellido, fecha_nacimiento, email, telefono, estado_cuota) VALUES
  ( 1, '31456789', 'Lucía',     'Gómez',     '1985-03-12', 'lucia.gomez@mail.com',      '351-4561234', 'AL_DIA'),
  ( 2, '35123456', 'Juan',      'Pereyra',   '1990-07-22', 'juan.pereyra@mail.com',     '351-4562345', 'AL_DIA'),
  ( 3, '26789012', 'Ana',       'Fernández', '1978-11-05', 'ana.fernandez@mail.com',    '351-4563456', 'AL_DIA'),
  ( 4, '38901234', 'Marcos',    'López',     '1995-01-30', 'marcos.lopez@mail.com',     '351-4564567', 'MOROSO'),
  ( 5, '33567890', 'Carolina',  'Díaz',      '1988-09-14', 'carolina.diaz@mail.com',    '351-4565678', 'AL_DIA'),
  ( 6, '21345678', 'Pablo',     'Martínez',  '1970-04-02', 'pablo.martinez@mail.com',   '351-4566789', 'AL_DIA'),
  ( 7, '42678901', 'Valeria',   'Romero',    '2000-12-19', 'valeria.romero@mail.com',   '351-4567890', 'AL_DIA'),
  ( 8, '12890123', 'Héctor',    'Sosa',      '1960-06-08', 'hector.sosa@mail.com',      '351-4568901', 'AL_DIA'),
  ( 9, '36234567', 'Florencia', 'Álvarez',   '1992-02-25', 'florencia.alvarez@mail.com','351-4569012', 'AL_DIA'),
  (10, '30456123', 'Gabriel',   'Torres',    '1983-08-17', 'gabriel.torres@mail.com',   '351-4570123', 'MOROSO'),
  (11, '24567234', 'Mariana',   'Ruiz',      '1975-10-03', 'mariana.ruiz@mail.com',     '351-4571234', 'AL_DIA'),
  (12, '20678345', 'Silvia',    'Benítez',   '1968-05-21', 'silvia.benitez@mail.com',   '351-4572345', 'AL_DIA'),
  (13, '49123456', 'Julieta',   'Acosta',    '2009-03-10', 'julieta.acosta@mail.com',   '351-4573456', 'AL_DIA'),
  (14, '8456123',  'Roberto',   'Medina',    '1955-12-01', 'roberto.medina@mail.com',   '351-4574567', 'AL_DIA'),
  (15, '54789012', 'Tomás',     'Herrera',   '2014-06-15', 'flia.herrera@mail.com',     '351-4575678', 'AL_DIA'),
  (16, '52345678', 'Mateo',     'Castro',    '2012-04-20', 'flia.castro@mail.com',      '351-4576789', 'AL_DIA'),
  (17, '56890123', 'Sofía',     'Ríos',      '2017-09-09', 'flia.rios@mail.com',        '351-4577890', 'AL_DIA'),
  (18, '57234567', 'Benjamín',  'Molina',    '2018-02-11', 'flia.molina@mail.com',      '351-4578901', 'AL_DIA'),
  (19, '51678901', 'Camila',    'Ortiz',     '2011-11-30', 'flia.ortiz@mail.com',       '351-4579012', 'AL_DIA'),
  (20, '45901234', 'Nicolás',   'Vega',      '2004-07-07', 'nicolas.vega@mail.com',     '351-4580123', 'AL_DIA');

-- ---------------------------------------------------------------------
-- Inscripciones iniciales (ninguna viola las reglas RN1 a RN5)
-- ---------------------------------------------------------------------

INSERT INTO inscripciones (fecha_inscripcion, estado, socio_id, actividad_id) VALUES
  -- Yoga (actividad 8): 14 inscriptos activos sobre un cupo de 15
  ('2026-03-02', 'ACTIVA',  1, 8),
  ('2026-03-02', 'ACTIVA',  2, 8),
  ('2026-03-03', 'ACTIVA',  3, 8),
  ('2026-03-03', 'ACTIVA',  4, 8),
  ('2026-03-04', 'ACTIVA',  5, 8),
  ('2026-03-04', 'ACTIVA',  6, 8),
  ('2026-03-05', 'ACTIVA',  7, 8),
  ('2026-03-05', 'ACTIVA',  8, 8),
  ('2026-03-06', 'ACTIVA',  9, 8),
  ('2026-03-06', 'ACTIVA', 10, 8),
  ('2026-03-09', 'ACTIVA', 11, 8),
  ('2026-03-09', 'ACTIVA', 12, 8),
  ('2026-03-10', 'ACTIVA', 13, 8),
  ('2026-03-10', 'ACTIVA', 14, 8),
  -- Resto de las actividades
  ('2026-03-02', 'ACTIVA',  1, 7),
  ('2026-03-02', 'ACTIVA',  1, 4),
  ('2026-03-03', 'ACTIVA',  2, 2),
  ('2026-03-03', 'BAJA',    3, 4),
  ('2026-03-04', 'ACTIVA',  5, 7),
  ('2026-03-04', 'ACTIVA',  6, 6),
  ('2026-03-05', 'ACTIVA',  8, 6),
  ('2026-03-06', 'ACTIVA', 10, 7),
  ('2026-03-09', 'ACTIVA', 12, 4),
  ('2026-03-10', 'ACTIVA', 13, 3),
  ('2026-03-10', 'ACTIVA', 14, 4),
  ('2026-03-11', 'ACTIVA', 15, 1),
  ('2026-03-11', 'ACTIVA', 15, 4),
  ('2026-03-12', 'ACTIVA', 16, 3),
  ('2026-03-12', 'ACTIVA', 17, 5),
  ('2026-03-12', 'ACTIVA', 17, 4),
  ('2026-03-13', 'ACTIVA', 18, 1),
  ('2026-03-13', 'ACTIVA', 18, 6),
  ('2026-03-16', 'ACTIVA', 19, 5),
  ('2026-03-16', 'ACTIVA', 19, 2);
