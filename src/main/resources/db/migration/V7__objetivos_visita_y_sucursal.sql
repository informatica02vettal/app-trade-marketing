-- ============================================================
-- Catálogo gestionable de objetivos de visita (tipo → subtipo opcional,
-- ej. "Evento" tiene los subtipos "Ruta Vettal"/"Charla técnica";
-- "Auditoría de marca" no tiene subtipos). Reemplaza el campo de texto
-- libre "objetivo" de plan_visitas por selects respaldados en base de
-- datos, gestionables desde el dashboard (Administración → Objetivos
-- de visita). También se agrega selección opcional de sucursal del
-- cliente (sucursales_cliente_erp, creada en V6) al plan de visita.
-- ============================================================
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

INSERT INTO objetivo_visita_tipos (nombre) VALUES ('Auditoría de marca'), ('Evento');

INSERT INTO objetivo_visita_subtipos (tipo_id, nombre)
SELECT id, 'Ruta Vettal' FROM objetivo_visita_tipos WHERE nombre = 'Evento'
UNION ALL
SELECT id, 'Charla técnica' FROM objetivo_visita_tipos WHERE nombre = 'Evento';

ALTER TABLE plan_visitas
    ADD COLUMN sucursal_id BIGINT NULL AFTER erp_cliente_id,
    ADD COLUMN objetivo_tipo_id BIGINT NULL AFTER objetivo,
    ADD COLUMN objetivo_subtipo_id BIGINT NULL AFTER objetivo_tipo_id,
    ADD CONSTRAINT fk_plan_visita_sucursal FOREIGN KEY (sucursal_id) REFERENCES sucursales_cliente_erp (id),
    ADD CONSTRAINT fk_plan_visita_objetivo_tipo FOREIGN KEY (objetivo_tipo_id) REFERENCES objetivo_visita_tipos (id),
    ADD CONSTRAINT fk_plan_visita_objetivo_subtipo FOREIGN KEY (objetivo_subtipo_id) REFERENCES objetivo_visita_subtipos (id);
