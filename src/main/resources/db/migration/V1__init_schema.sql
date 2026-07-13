-- ============================================================
-- app-trade-marketing — esquema inicial de la base de datos propia
-- Es la ÚNICA base de datos de esta aplicación. Los datos de clientes
-- del ERP administrativo NO se replican aquí: se consultan en vivo vía
-- el endpoint REST de vettal-backend (ver integration.clientesapi).
-- ============================================================

CREATE TABLE usuarios (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre              VARCHAR(150)    NOT NULL,
    email               VARCHAR(150)    NOT NULL,
    password_hash       VARCHAR(255)    NOT NULL,
    region              VARCHAR(100)    NULL,
    rol                 VARCHAR(20)     NOT NULL,
    activo              TINYINT(1)      NOT NULL DEFAULT 1,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NULL,
    CONSTRAINT uk_usuarios_email UNIQUE (email)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE TABLE asignaciones_cliente (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    erp_cliente_id  VARCHAR(10)     NOT NULL,
    cliente_nombre  VARCHAR(200)    NULL,
    usuario_id      BIGINT          NOT NULL,
    region          VARCHAR(100)    NULL,
    activo          TINYINT(1)      NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_asignacion_cliente_usuario UNIQUE (erp_cliente_id, usuario_id),
    CONSTRAINT fk_asignacion_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_asignacion_erp_cliente ON asignaciones_cliente (erp_cliente_id);

CREATE TABLE plan_visitas (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    erp_cliente_id      VARCHAR(10)     NULL,
    cliente_nombre      VARCHAR(200)    NOT NULL,
    usuario_id          BIGINT          NOT NULL,
    region              VARCHAR(100)    NULL,
    fecha_programada    DATE            NOT NULL,
    hora_programada     VARCHAR(10)     NULL,
    objetivo            VARCHAR(255)    NULL,
    tipo_visita         VARCHAR(20)     NOT NULL DEFAULT 'PLANIFICADA',
    estado              VARCHAR(20)     NOT NULL DEFAULT 'PENDIENTE',
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NULL,
    CONSTRAINT fk_plan_visita_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_plan_visita_usuario_fecha ON plan_visitas (usuario_id, fecha_programada);
CREATE INDEX idx_plan_visita_erp_cliente ON plan_visitas (erp_cliente_id);

CREATE TABLE visitas (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_id             BIGINT          NULL,
    erp_cliente_id      VARCHAR(10)     NULL,
    cliente_nombre      VARCHAR(200)    NOT NULL,
    region              VARCHAR(100)    NULL,
    usuario_id          BIGINT          NOT NULL,
    ejecutivo_ventas    VARCHAR(150)    NULL,
    checkin_at          TIMESTAMP       NOT NULL,
    checkin_gps_lat     DECIMAL(10,7)   NULL,
    checkin_gps_lng     DECIMAL(10,7)   NULL,
    checkout_at         TIMESTAMP       NULL,
    permanencia_min     INT             NULL,
    observaciones       TEXT            NULL,
    estado              VARCHAR(20)     NOT NULL DEFAULT 'EN_CURSO',
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_visita_plan FOREIGN KEY (plan_id) REFERENCES plan_visitas (id),
    CONSTRAINT fk_visita_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_visita_usuario ON visitas (usuario_id);
CREATE INDEX idx_visita_erp_cliente ON visitas (erp_cliente_id);

CREATE TABLE evidencia_fotos (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    visita_id   BIGINT          NOT NULL,
    categoria   VARCHAR(20)     NOT NULL,
    url         VARCHAR(500)    NOT NULL,
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_evidencia_visita FOREIGN KEY (visita_id) REFERENCES visitas (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_evidencia_visita ON evidencia_fotos (visita_id);

CREATE TABLE auditorias_marca (
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    visita_id               BIGINT          NOT NULL,
    marca                   VARCHAR(100)    NOT NULL,
    presencia_pct           TINYINT         NOT NULL DEFAULT 0,
    anaquel_pct             TINYINT         NOT NULL DEFAULT 0,
    frentes_vettal          INT             NOT NULL DEFAULT 0,
    frentes_totales         INT             NOT NULL DEFAULT 0,
    exhibidor_marca         TINYINT(1)      NOT NULL DEFAULT 0,
    producto_exhibidor      TINYINT(1)      NOT NULL DEFAULT 0,
    producto_anaquel        TINYINT(1)      NOT NULL DEFAULT 0,
    aviso_fachada           TINYINT(1)      NOT NULL DEFAULT 0,
    aviso_pared             TINYINT(1)      NOT NULL DEFAULT 0,
    banderines              TINYINT(1)      NOT NULL DEFAULT 0,
    rotulado                TINYINT(1)      NOT NULL DEFAULT 0,
    empleados_uniforme      TINYINT(1)      NOT NULL DEFAULT 0,
    estado_exhibidores      VARCHAR(20)     NOT NULL,
    estado_pop              VARCHAR(20)     NOT NULL,
    competencia_detectada   VARCHAR(255)    NULL,
    oportunidad             TEXT            NULL,
    created_at              TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_auditoria_visita FOREIGN KEY (visita_id) REFERENCES visitas (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_auditoria_visita ON auditorias_marca (visita_id);

CREATE TABLE solicitudes (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    erp_cliente_id  VARCHAR(10)     NULL,
    cliente_nombre  VARCHAR(200)    NOT NULL,
    categoria       VARCHAR(20)     NOT NULL,
    marca           VARCHAR(100)    NULL,
    observaciones   TEXT            NULL,
    solicitante_id  BIGINT          NOT NULL,
    estado          VARCHAR(30)     NOT NULL DEFAULT 'PENDIENTE_APROBACION',
    aprobado_por    BIGINT          NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NULL,
    CONSTRAINT fk_solicitud_solicitante FOREIGN KEY (solicitante_id) REFERENCES usuarios (id),
    CONSTRAINT fk_solicitud_aprobador FOREIGN KEY (aprobado_por) REFERENCES usuarios (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_solicitud_erp_cliente ON solicitudes (erp_cliente_id);
CREATE INDEX idx_solicitud_estado ON solicitudes (estado);

CREATE TABLE solicitud_items (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    solicitud_id    BIGINT          NOT NULL,
    nombre          VARCHAR(150)    NOT NULL,
    medidas         VARCHAR(50)     NULL,
    ubicacion       VARCHAR(100)    NULL,
    foto_url        VARCHAR(500)    NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_solicitud_item_solicitud FOREIGN KEY (solicitud_id) REFERENCES solicitudes (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_solicitud_item_solicitud ON solicitud_items (solicitud_id);

CREATE TABLE instalaciones_ejecucion (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    erp_cliente_id      VARCHAR(10)     NULL,
    cliente_nombre      VARCHAR(200)    NOT NULL,
    marca               VARCHAR(100)    NOT NULL,
    categoria           VARCHAR(30)     NOT NULL,
    usuario_id          BIGINT          NOT NULL,
    observaciones       TEXT            NULL,
    fecha_instalacion   DATE            NOT NULL,
    estado              VARCHAR(20)     NOT NULL DEFAULT 'INSTALADO',
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NULL,
    CONSTRAINT fk_instalacion_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_instalacion_erp_cliente ON instalaciones_ejecucion (erp_cliente_id);

CREATE TABLE instalacion_items (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    instalacion_id  BIGINT          NOT NULL,
    material        VARCHAR(150)    NOT NULL,
    foto_url        VARCHAR(500)    NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_instalacion_item_instalacion FOREIGN KEY (instalacion_id) REFERENCES instalaciones_ejecucion (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_instalacion_item_instalacion ON instalacion_items (instalacion_id);

CREATE TABLE hallazgos_mercado (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo                VARCHAR(40)     NOT NULL,
    erp_cliente_id      VARCHAR(10)     NULL,
    cliente_nombre      VARCHAR(200)    NULL,
    categoria_producto  VARCHAR(50)     NULL,
    marca               VARCHAR(100)    NULL,
    marca_competencia   VARCHAR(100)    NULL,
    oportunidad_texto   TEXT            NULL,
    detalle             TEXT            NULL,
    usuario_id          BIGINT          NOT NULL,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hallazgo_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_hallazgo_tipo ON hallazgos_mercado (tipo);
CREATE INDEX idx_hallazgo_erp_cliente ON hallazgos_mercado (erp_cliente_id);

CREATE TABLE hallazgo_productos (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    hallazgo_id BIGINT          NOT NULL,
    marca       VARCHAR(100)    NULL,
    modelo      VARCHAR(100)    NULL,
    precio      DECIMAL(12,2)   NULL,
    foto_url    VARCHAR(500)    NULL,
    CONSTRAINT fk_hallazgo_producto_hallazgo FOREIGN KEY (hallazgo_id) REFERENCES hallazgos_mercado (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_hallazgo_producto_hallazgo ON hallazgo_productos (hallazgo_id);

CREATE TABLE hallazgo_materiales (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    hallazgo_id BIGINT          NOT NULL,
    material    VARCHAR(150)    NULL,
    marca       VARCHAR(100)    NULL,
    foto_url    VARCHAR(500)    NULL,
    CONSTRAINT fk_hallazgo_material_hallazgo FOREIGN KEY (hallazgo_id) REFERENCES hallazgos_mercado (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE INDEX idx_hallazgo_material_hallazgo ON hallazgo_materiales (hallazgo_id);

CREATE TABLE clientes_prospecto (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    hallazgo_id         BIGINT          NULL,
    nombre              VARCHAR(200)    NOT NULL,
    rif                 VARCHAR(20)     NOT NULL,
    whatsapp            VARCHAR(30)     NOT NULL,
    telefono            VARCHAR(30)     NULL,
    gps_lat             DECIMAL(10,7)   NOT NULL,
    gps_lng             DECIMAL(10,7)   NOT NULL,
    foto_fachada_url    VARCHAR(500)    NOT NULL,
    foto_interior_url   VARCHAR(500)    NOT NULL,
    marcas_competencia  VARCHAR(255)    NULL,
    usuario_id          BIGINT          NOT NULL,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prospecto_hallazgo FOREIGN KEY (hallazgo_id) REFERENCES hallazgos_mercado (id) ON DELETE SET NULL,
    CONSTRAINT fk_prospecto_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE TABLE planogramas (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_exhibidor  VARCHAR(100)    NOT NULL,
    nombre          VARCHAR(150)    NOT NULL,
    imagen_url      VARCHAR(500)    NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE TABLE artes_marca (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca           VARCHAR(100)    NOT NULL,
    nombre          VARCHAR(150)    NOT NULL,
    archivo_url     VARCHAR(500)    NOT NULL,
    tipo_archivo    VARCHAR(20)     NOT NULL DEFAULT 'IMAGEN',
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;
