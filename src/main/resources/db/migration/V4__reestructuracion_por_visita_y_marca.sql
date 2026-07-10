-- ============================================================
-- Reestructuración solicitada tras las pruebas de campo (correo de Blanca):
--  - El cliente ya no se repite: solicitudes, instalaciones y hallazgos de
--    mercado se ligan a la visita (visita_id) en vez de cargar su propia
--    copia de erp_cliente_id/cliente_nombre.
--  - Las marcas propias pasan de texto libre a FK contra el catálogo `marcas`.
--  - Los materiales solicitados/instalados pasan a referenciar el catálogo
--    `materiales` (ya filtrado por marca), en vez de texto libre.
--  - Mercado se reestructura: se quita "Cliente no registrado" (se mueve a
--    Visitas) y "Actividad promocional" (duplicaba Solicitudes); se agrega
--    categoria_producto_id (FK) y se renombra oportunidad -> observación.
-- Se asume que todavía no hay datos de producción en estas tablas.
-- ============================================================

-- ---------- Solicitudes ----------
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

-- ---------- Auditoría de marca: marca pasa a FK ----------
ALTER TABLE auditorias_marca
    ADD COLUMN marca_id BIGINT NOT NULL AFTER visita_id,
    ADD CONSTRAINT fk_auditoria_marca FOREIGN KEY (marca_id) REFERENCES marcas (id),
    DROP COLUMN marca;

-- ---------- Mercado: se liga a la visita, se quita categoría/marca en texto
-- libre a favor del catálogo, y se renombra oportunidad -> observación ----------
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

-- ---------- Cliente no registrado: se mueve de Mercado a Visitas ----------
ALTER TABLE clientes_prospecto
    ADD COLUMN visita_id BIGINT NOT NULL AFTER id,
    ADD CONSTRAINT fk_prospecto_visita FOREIGN KEY (visita_id) REFERENCES visitas (id),
    DROP FOREIGN KEY fk_prospecto_hallazgo,
    DROP COLUMN hallazgo_id,
    DROP COLUMN gps_lat,
    DROP COLUMN gps_lng;
