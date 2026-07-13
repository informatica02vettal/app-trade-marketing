-- ============================================================
-- Copia local sincronizada de clientes y sucursales del ERP
-- (consultados vía HTTP a vettal-backend, GET /api/v1/clientes/listado
-- y GET /api/v1/clientes/sucursales). Permite manipular esta información
-- dentro de app-trade-marketing sin depender de una llamada en vivo a la
-- API externa en cada consulta. Se sincroniza bajo demanda vía
-- POST /api/v1/clientes/sincronizar (upsert por codigo_cliente / erp_id).
-- ============================================================
CREATE TABLE clientes_erp (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_cliente       VARCHAR(10)  NOT NULL,
    rif                  VARCHAR(20),
    nombre_fiscal        VARCHAR(200),
    nombre_comercial     VARCHAR(200),
    direccion_fiscal     VARCHAR(500),
    telefono_principal   VARCHAR(50),
    celular              VARCHAR(50),
    email                VARCHAR(150),
    estado               VARCHAR(100),
    ciudad               VARCHAR(100),
    municipio            VARCHAR(100),
    fecha_creacion_erp   DATETIME,
    sincronizado_en       DATETIME     NOT NULL,
    CONSTRAINT uq_clientes_erp_codigo UNIQUE (codigo_cliente)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE TABLE sucursales_cliente_erp (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    erp_id               INT          NOT NULL,
    codigo_cliente       VARCHAR(10)  NOT NULL,
    id_vendedor          VARCHAR(38),
    nombre_sucursal      VARCHAR(255),
    direccion_sucursal   TEXT,
    estado               VARCHAR(38),
    ciudad               VARCHAR(38),
    sincronizado_en       DATETIME     NOT NULL,
    CONSTRAINT uq_sucursales_cliente_erp_id UNIQUE (erp_id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_sucursales_cliente_erp_codigo ON sucursales_cliente_erp (codigo_cliente);
