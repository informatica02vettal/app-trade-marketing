ALTER TABLE eventos_visita
    ADD COLUMN gps_lat DOUBLE NULL AFTER cantidad_asistentes_estimada,
    ADD COLUMN gps_lng DOUBLE NULL AFTER gps_lat;
