-- ============================================================
-- Catálogos de negocio (marcas, competencia, materiales) que hoy están
-- hardcodeados en el frontend (vettal-mobile.html) y pasan a vivir en BD.
-- ============================================================

CREATE TABLE marcas (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo  VARCHAR(20)  NOT NULL,
    nombre  VARCHAR(60)  NOT NULL,
    activo  TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT uk_marcas_codigo UNIQUE (codigo)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE TABLE marcas_competencia (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca_id  BIGINT       NOT NULL,
    nombre    VARCHAR(100) NOT NULL,
    activo    TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT fk_marcomp_marca FOREIGN KEY (marca_id) REFERENCES marcas (id),
    CONSTRAINT uk_marcomp_marca_nombre UNIQUE (marca_id, nombre)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_marcomp_marca ON marcas_competencia (marca_id);

-- familia: PUBLICIDAD | TRADE_MARKETING
CREATE TABLE categorias_material (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    familia  VARCHAR(20)  NOT NULL,
    nombre   VARCHAR(60)  NOT NULL,
    activo   TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT uk_categoria_material UNIQUE (familia, nombre)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

-- marca_id NULL = el material aplica a todas las marcas.
CREATE TABLE materiales (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca_id            BIGINT       NULL,
    categoria_id        BIGINT       NOT NULL,
    nombre              VARCHAR(150) NOT NULL,
    requiere_medidas    TINYINT(1)   NOT NULL DEFAULT 0,
    requiere_ubicacion  TINYINT(1)   NOT NULL DEFAULT 0,
    minimo_fotos        INT          NOT NULL DEFAULT 1,
    activo              TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT fk_material_marca FOREIGN KEY (marca_id) REFERENCES marcas (id),
    CONSTRAINT fk_material_categoria FOREIGN KEY (categoria_id) REFERENCES categorias_material (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_material_marca ON materiales (marca_id);
CREATE INDEX idx_material_categoria ON materiales (categoria_id);

-- Categorías de producto usadas en el módulo Mercado (antes MERCADO_CATEGORIAS
-- en el frontend). marca_id: de qué marca propia se reutiliza el catálogo de
-- competencia (ej. BOMBAS reutiliza la competencia de LEO).
CREATE TABLE categorias_producto_mercado (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(50) NOT NULL,
    marca_id  BIGINT      NOT NULL,
    activo    TINYINT(1)  NOT NULL DEFAULT 1,
    CONSTRAINT uk_categoria_producto_mercado UNIQUE (nombre),
    CONSTRAINT fk_categoria_producto_marca FOREIGN KEY (marca_id) REFERENCES marcas (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;
