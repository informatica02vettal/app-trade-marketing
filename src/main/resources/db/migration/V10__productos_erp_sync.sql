-- ============================================================
-- Copia local sincronizada del catálogo de productos del ERP
-- (API externa, tabla lista_prod_pw, consultada vía HTTP con apikey).
-- Se sincroniza bajo demanda vía POST /api/v1/productos/sincronizar
-- (upsert por codigo). Nunca borra filas existentes.
-- ============================================================
CREATE TABLE productos_erp (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo             VARCHAR(30)  NOT NULL,
    producto           VARCHAR(500),
    linea              VARCHAR(200),
    subcategoria       VARCHAR(200),
    marca              VARCHAR(100),
    contenido          VARCHAR(50),
    peso               DECIMAL(12,4),
    precio             DECIMAL(14,4),
    foto_url           VARCHAR(500),
    nombre_comercial   VARCHAR(500),
    detalles           TEXT,
    sincronizado_en    DATETIME     NOT NULL,
    CONSTRAINT uq_productos_erp_codigo UNIQUE (codigo)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;
