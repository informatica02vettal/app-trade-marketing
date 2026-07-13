-- ============================================================
-- Campo de comentario libre para el plan de visita, para notas
-- adicionales que no encajan en el objetivo estructurado (tipo/subtipo).
-- ============================================================
ALTER TABLE plan_visitas
    ADD COLUMN comentario TEXT NULL AFTER objetivo_subtipo_id;
