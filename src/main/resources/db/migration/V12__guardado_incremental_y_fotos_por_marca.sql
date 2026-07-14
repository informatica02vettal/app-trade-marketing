-- ============================================================
-- Permite guardar la auditoría de marca de forma incremental (no solo al
-- final), para que el progreso quede en la base de datos y se pueda
-- retomar en cualquier dispositivo si el mercaderista cierra la app a
-- mitad de una visita. `completa` distingue un borrador en progreso de una
-- auditoría ya finalizada (las filas existentes son todas finalizadas).
-- También se permite asociar una foto de evidencia a una marca específica
-- (antes solo se sabía a qué visita pertenecía, no a qué marca).
-- ============================================================
ALTER TABLE auditorias_marca
    MODIFY COLUMN estado_exhibidores VARCHAR(20) NULL,
    MODIFY COLUMN estado_pop VARCHAR(20) NULL,
    ADD COLUMN completa TINYINT(1) NOT NULL DEFAULT 1 AFTER oportunidad;

ALTER TABLE evidencia_fotos
    ADD COLUMN marca_id BIGINT NULL AFTER visita_id,
    ADD CONSTRAINT fk_evidencia_marca FOREIGN KEY (marca_id) REFERENCES marcas (id);

CREATE INDEX idx_evidencia_marca ON evidencia_fotos (marca_id);
