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

CREATE TABLE categorias_producto_mercado (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(50) NOT NULL,
    marca_id  BIGINT      NOT NULL,
    activo    TINYINT(1)  NOT NULL DEFAULT 1,
    CONSTRAINT uk_categoria_producto_mercado UNIQUE (nombre),
    CONSTRAINT fk_categoria_producto_marca FOREIGN KEY (marca_id) REFERENCES marcas (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

ALTER TABLE solicitudes
    ADD COLUMN visita_id BIGINT NOT NULL AFTER id,
    ADD COLUMN marca_id BIGINT NOT NULL AFTER categoria,
    ADD CONSTRAINT fk_solicitud_visita FOREIGN KEY (visita_id) REFERENCES visitas (id),
    ADD CONSTRAINT fk_solicitud_marca FOREIGN KEY (marca_id) REFERENCES marcas (id),
    DROP COLUMN erp_cliente_id,
    DROP COLUMN cliente_nombre,
    DROP COLUMN marca;

ALTER TABLE solicitud_items
    ADD COLUMN material_id BIGINT NOT NULL AFTER solicitud_id,
    ADD CONSTRAINT fk_solicitud_item_material FOREIGN KEY (material_id) REFERENCES materiales (id),
    DROP COLUMN nombre,
    DROP COLUMN foto_url;

CREATE TABLE solicitud_item_fotos (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    solicitud_item_id   BIGINT       NOT NULL,
    url                 VARCHAR(500) NOT NULL,
    CONSTRAINT fk_solicitud_item_foto FOREIGN KEY (solicitud_item_id) REFERENCES solicitud_items (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

-- ---------- Instalación / Ejecución ----------
ALTER TABLE instalaciones_ejecucion
    ADD COLUMN visita_id BIGINT NOT NULL AFTER id,
    ADD COLUMN marca_id BIGINT NOT NULL AFTER visita_id,
    ADD CONSTRAINT fk_instalacion_visita FOREIGN KEY (visita_id) REFERENCES visitas (id),
    ADD CONSTRAINT fk_instalacion_marca FOREIGN KEY (marca_id) REFERENCES marcas (id),
    DROP COLUMN erp_cliente_id,
    DROP COLUMN cliente_nombre,
    DROP COLUMN marca;

ALTER TABLE instalacion_items
    ADD COLUMN material_id BIGINT NOT NULL AFTER instalacion_id,
    ADD CONSTRAINT fk_instalacion_item_material FOREIGN KEY (material_id) REFERENCES materiales (id),
    DROP COLUMN material,
    DROP COLUMN foto_url;

CREATE TABLE instalacion_item_fotos (
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    instalacion_item_id   BIGINT       NOT NULL,
    url                   VARCHAR(500) NOT NULL,
    CONSTRAINT fk_instalacion_item_foto FOREIGN KEY (instalacion_item_id) REFERENCES instalacion_items (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

ALTER TABLE auditorias_marca
    ADD COLUMN marca_id BIGINT NOT NULL AFTER visita_id,
    ADD CONSTRAINT fk_auditoria_marca FOREIGN KEY (marca_id) REFERENCES marcas (id),
    DROP COLUMN marca;

ALTER TABLE hallazgos_mercado
    ADD COLUMN visita_id BIGINT NOT NULL AFTER id,
    ADD COLUMN categoria_producto_id BIGINT NULL AFTER marca_competencia,
    ADD COLUMN observacion_texto TEXT NULL AFTER categoria_producto_id,
    ADD CONSTRAINT fk_hallazgo_visita FOREIGN KEY (visita_id) REFERENCES visitas (id),
    ADD CONSTRAINT fk_hallazgo_categoria_producto FOREIGN KEY (categoria_producto_id) REFERENCES categorias_producto_mercado (id),
    DROP COLUMN erp_cliente_id,
    DROP COLUMN cliente_nombre,
    DROP COLUMN categoria_producto,
    DROP COLUMN marca,
    DROP COLUMN oportunidad_texto;

ALTER TABLE clientes_prospecto
    ADD COLUMN visita_id BIGINT NOT NULL AFTER id,
    ADD CONSTRAINT fk_prospecto_visita FOREIGN KEY (visita_id) REFERENCES visitas (id),
    DROP FOREIGN KEY fk_prospecto_hallazgo,
    DROP COLUMN hallazgo_id,
    DROP COLUMN gps_lat,
    DROP COLUMN gps_lng;

ALTER TABLE hallazgos_mercado
    ADD COLUMN marca_id BIGINT NULL AFTER visita_id,
    ADD CONSTRAINT fk_hallazgo_marca FOREIGN KEY (marca_id) REFERENCES marcas (id);

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

CREATE TABLE objetivo_visita_tipos (
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    activo BOOLEAN      NOT NULL DEFAULT TRUE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE TABLE objetivo_visita_subtipos (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_id BIGINT       NOT NULL,
    nombre  VARCHAR(150) NOT NULL,
    activo  BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_objetivo_visita_subtipo_tipo FOREIGN KEY (tipo_id) REFERENCES objetivo_visita_tipos (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;


ALTER TABLE plan_visitas
    ADD COLUMN sucursal_id BIGINT NULL AFTER erp_cliente_id,
    ADD COLUMN objetivo_tipo_id BIGINT NULL AFTER objetivo,
    ADD COLUMN objetivo_subtipo_id BIGINT NULL AFTER objetivo_tipo_id,
    ADD CONSTRAINT fk_plan_visita_sucursal FOREIGN KEY (sucursal_id) REFERENCES sucursales_cliente_erp (id),
    ADD CONSTRAINT fk_plan_visita_objetivo_tipo FOREIGN KEY (objetivo_tipo_id) REFERENCES objetivo_visita_tipos (id),
    ADD CONSTRAINT fk_plan_visita_objetivo_subtipo FOREIGN KEY (objetivo_subtipo_id) REFERENCES objetivo_visita_subtipos (id);

ALTER TABLE plan_visitas
    ADD COLUMN comentario TEXT NULL AFTER objetivo_subtipo_id;


CREATE TABLE eventos_visita (
    id                              BIGINT AUTO_INCREMENT PRIMARY KEY,
    visita_id                       BIGINT       NOT NULL,
    motivo                          VARCHAR(30)  NOT NULL,
    motivo_otro_detalle             VARCHAR(255) NULL,
    nombre_evento                   VARCHAR(200) NULL,
    ciudad                          VARCHAR(100) NULL,
    estado                          VARCHAR(100) NULL,
    lugar_realizacion               VARCHAR(255) NULL,
    fecha_evento                    DATE         NULL,
    hora_inicio                     VARCHAR(10)  NULL,
    hora_fin                        VARCHAR(10)  NULL,
    organizador                     VARCHAR(200) NULL,
    objetivo_participacion          VARCHAR(500) NULL,
    participacion_vettal            VARCHAR(20)  NULL,
    cantidad_asistentes_estimada    INT          NULL,
    created_at                      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_eventos_visita_visita UNIQUE (visita_id),
    CONSTRAINT fk_eventos_visita_visita FOREIGN KEY (visita_id) REFERENCES visitas (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

CREATE TABLE evento_leads (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    evento_id   BIGINT       NOT NULL,
    nombre      VARCHAR(200) NOT NULL,
    empresa     VARCHAR(200) NULL,
    cargo       VARCHAR(150) NULL,
    telefono    VARCHAR(50)  NULL,
    correo      VARCHAR(150) NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_evento_lead_evento FOREIGN KEY (evento_id) REFERENCES eventos_visita (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;
CREATE INDEX idx_evento_lead_evento ON evento_leads (evento_id);

CREATE TABLE evento_entrevistas (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    evento_id   BIGINT       NOT NULL,
    video_url   VARCHAR(500) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_evento_entrevista_evento FOREIGN KEY (evento_id) REFERENCES eventos_visita (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;
CREATE INDEX idx_evento_entrevista_evento ON evento_entrevistas (evento_id);

CREATE TABLE competidores (
    id                                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    visita_id                           BIGINT       NOT NULL,
    nombre_empresa                      VARCHAR(200) NOT NULL,
    marcas_representadas                VARCHAR(500) NULL,
    tipo_productos_exhibidos            VARCHAR(500) NULL,
    tamano_stand                        VARCHAR(100) NULL,
    cantidad_promotores                 INT          NULL,
    cantidad_personal_tecnico           INT          NULL,
    posee_inflables                     BOOLEAN      NOT NULL DEFAULT FALSE,
    posee_toldos                        BOOLEAN      NOT NULL DEFAULT FALSE,
    posee_pantalla_led                  BOOLEAN      NOT NULL DEFAULT FALSE,
    posee_experiencias_interactivas     BOOLEAN      NOT NULL DEFAULT FALSE,
    realiza_demostraciones              BOOLEAN      NOT NULL DEFAULT FALSE,
    entrega_material_pop                BOOLEAN      NOT NULL DEFAULT FALSE,
    entrega_muestras                    BOOLEAN      NOT NULL DEFAULT FALSE,
    realiza_rifas_concursos             BOOLEAN      NOT NULL DEFAULT FALSE,
    realiza_promociones_especiales      BOOLEAN      NOT NULL DEFAULT FALSE,
    cuenta_activaciones                 BOOLEAN      NOT NULL DEFAULT FALSE,
    posee_exhibidores_diferenciadores   BOOLEAN      NOT NULL DEFAULT FALSE,
    utiliza_mascotas_publicitarias      BOOLEAN      NOT NULL DEFAULT FALSE,
    observaciones                       TEXT         NULL,
    created_at                          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_competidor_visita FOREIGN KEY (visita_id) REFERENCES visitas (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;
CREATE INDEX idx_competidor_visita ON competidores (visita_id);

CREATE TABLE competidor_fotos (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    competidor_id   BIGINT       NOT NULL,
    categoria       VARCHAR(20)  NOT NULL,
    url             VARCHAR(500) NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_competidor_foto_competidor FOREIGN KEY (competidor_id) REFERENCES competidores (id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;
CREATE INDEX idx_competidor_foto_competidor ON competidor_fotos (competidor_id);

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

CREATE TABLE plan_visita_productos (
    plan_visita_id BIGINT NOT NULL,
    producto_erp_id BIGINT NOT NULL,
    PRIMARY KEY (plan_visita_id, producto_erp_id),
    CONSTRAINT fk_pvp_plan_visita FOREIGN KEY (plan_visita_id) REFERENCES plan_visitas (id),
    CONSTRAINT fk_pvp_producto_erp FOREIGN KEY (producto_erp_id) REFERENCES productos_erp (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;

ALTER TABLE hallazgos_mercado
    ADD COLUMN producto_erp_id BIGINT NULL AFTER marca_id,
    ADD CONSTRAINT fk_hallazgo_producto_erp FOREIGN KEY (producto_erp_id) REFERENCES productos_erp (id);

ALTER TABLE auditorias_marca
    MODIFY COLUMN estado_exhibidores VARCHAR(20) NULL,
    MODIFY COLUMN estado_pop VARCHAR(20) NULL,
    ADD COLUMN completa TINYINT(1) NOT NULL DEFAULT 1 AFTER oportunidad;

ALTER TABLE evidencia_fotos
    ADD COLUMN marca_id BIGINT NULL AFTER visita_id,
    ADD CONSTRAINT fk_evidencia_marca FOREIGN KEY (marca_id) REFERENCES marcas (id);

CREATE INDEX idx_evidencia_marca ON evidencia_fotos (marca_id);

ALTER TABLE eventos_visita
    ADD COLUMN gps_lat DOUBLE NULL AFTER cantidad_asistentes_estimada,
    ADD COLUMN gps_lng DOUBLE NULL AFTER gps_lat;

CREATE TABLE regiones (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(60) NOT NULL,
    detalles varchar(255) NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT uk_regiones_nombre UNIQUE (nombre)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;


