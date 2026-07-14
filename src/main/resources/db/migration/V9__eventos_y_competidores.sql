-- ============================================================
-- Flujo de "Evento" de la app móvil: motivo del evento, y para
-- ferias/exposiciones/congresos/ruedas de negocios un registro
-- extendido (datos del evento, leads, entrevistas en video) más
-- inteligencia de competencia por visita (reutilizada también
-- desde el paso "Competencia" de la auditoría de marca normal).
-- ============================================================

-- Nuevos subtipos de objetivo "Evento" (además de Ruta Vettal / Charla técnica)
INSERT INTO objetivo_visita_subtipos (tipo_id, nombre)
SELECT id, 'Feria' FROM objetivo_visita_tipos WHERE nombre = 'Evento'
UNION ALL
SELECT id, 'Exposición' FROM objetivo_visita_tipos WHERE nombre = 'Evento'
UNION ALL
SELECT id, 'Congreso' FROM objetivo_visita_tipos WHERE nombre = 'Evento'
UNION ALL
SELECT id, 'Rueda de negocios' FROM objetivo_visita_tipos WHERE nombre = 'Evento';

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
