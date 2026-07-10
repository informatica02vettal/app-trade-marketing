-- ============================================================
-- Integra la inteligencia de mercado al flujo por marca (correo de
-- Blanca, cambio de mayor impacto: completar toda la evaluación de una
-- marca -incluyendo precios de competencia, nuevos productos y material
-- publicitario de competencia- antes de pasar a la siguiente). Se agrega
-- marca_id como FK directa, igual que ya se hizo con auditorias_marca,
-- solicitudes e instalaciones_ejecucion en V4. categoria_producto_id se
-- mantiene como campo opcional/complementario, sin romper nada existente.
-- ============================================================
ALTER TABLE hallazgos_mercado
    ADD COLUMN marca_id BIGINT NULL AFTER visita_id,
    ADD CONSTRAINT fk_hallazgo_marca FOREIGN KEY (marca_id) REFERENCES marcas (id);
