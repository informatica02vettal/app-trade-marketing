-- ============================================================
-- Productos propios (ERP) seleccionados para auditar en un plan de visita
-- (Auditoría de marca), elegidos desde el modal "Nueva visita" del dashboard.
-- Se listan en la app de mercaderistas dentro de Inteligencia de Mercado,
-- donde el mercaderista elige uno y registra la competencia detectada
-- (marca/modelo/precio) para ese producto específico.
-- ============================================================
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
