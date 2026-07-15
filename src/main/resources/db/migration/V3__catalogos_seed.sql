INSERT INTO marcas (id, codigo, nombre) VALUES
(1, 'LEO',     'Leo'),
(2, 'WORKPRO', 'WorkPro'),
(3, 'HAPPY',   'Happy'),
(4, 'BLU',     'Blu'),
(5, 'TENGEN',  'Tengen'),
(6, 'VESTRA',  'Vestra'),
(7, 'ITAP',    'Itap'),
(8, 'ERA',     'Era');

INSERT INTO marcas_competencia (marca_id, nombre) VALUES
(1, 'SHIMGE'), (1, 'INTOP'), (1, 'PEARL'), (1, 'TAIFU'), (1, 'PANELLI'), (1, 'BAICO'),
(1, 'ESPA'), (1, 'Pedrollo'), (1, 'Griven'), (1, 'Calpeda'), (1, 'Truper'), (1, 'Stanley'),
(1, 'Lince'), (1, 'Wokin'), (1, 'Excellent'), (1, 'Exceline'), (1, 'Faboven'),
(1, 'Gladiator PRO'), (1, 'City Pumps'), (1, 'Strugger'), (1, 'Daewoo'), (1, 'Caprino');

INSERT INTO marcas_competencia (marca_id, nombre) VALUES
(2, 'TOTAL'), (2, 'INGCO'), (2, 'JADEVER'), (2, 'EMTOP'), (2, 'WADFOW'), (2, 'BOSCH'),
(2, 'PROMAKER'), (2, 'DEWALT'), (2, 'LOBSTER'), (2, 'BRUFER'), (2, 'MAKITA'), (2, 'SKILL'),
(2, 'Milwaukee'), (2, 'Truper'), (2, 'Yustools'), (2, 'Wokin'), (2, 'Xcort'), (2, 'Pretul'),
(2, 'Atouan'), (2, 'Exxel'), (2, 'Roccin'), (2, 'Ridgid'), (2, 'Bellota'), (2, 'Greenlee'),
(2, 'Cinhell'), (2, 'Daewoo'), (2, 'Black + Decker');

INSERT INTO marcas_competencia (marca_id, nombre) VALUES
(3, 'SHIMGE'), (3, 'INTOP'), (3, 'PEARL'), (3, 'TAIFU'), (3, 'PANELLI'), (3, 'BAICO'),
(3, 'ESPA'), (3, 'Pedrollo'), (3, 'Griven'), (3, 'Calpeda'), (3, 'Truper'), (3, 'Stanley'),
(3, 'Lince'), (3, 'Wokin'), (3, 'Excellent'), (3, 'Exceline'), (3, 'Faboven'),
(3, 'Gladiator PRO'), (3, 'City Pumps'), (3, 'Strugger'), (3, 'Daewoo'), (3, 'Caprino');

INSERT INTO marcas_competencia (marca_id, nombre) VALUES
(4, 'ZOWA'), (4, 'PUREPRO'), (4, 'GRIVEN'), (4, 'GRIMAX'), (4, 'SALUDVEN'), (4, 'OZONO'), (4, 'CLEMENTE'),
(4, 'Water Quality'), (4, 'Charger'), (4, 'Sediment'), (4, 'Clear Water');

INSERT INTO marcas_competencia (marca_id, nombre) VALUES
(5, 'CHINT'), (5, 'ANDELLI'), (5, 'GE'), (5, 'SCHENIDER'), (5, 'BTICINO'), (5, 'CNC'), (5, 'DELIXI'), (5, 'STECK'), (5, 'VITRON');

INSERT INTO marcas_competencia (marca_id, nombre) VALUES
(6, 'COVO'), (6, 'FP'), (6, 'TORRENTI'), (6, 'GRIVEN'), (6, 'BELT-G'), (6, 'GRINACA'),
(6, 'AQUA PLUS'), (6, 'SALUDVEN'), (6, 'GRIMAX'), (6, 'AQUAFINA'),
(6, 'Galven'), (6, 'Tezza');

INSERT INTO marcas_competencia (marca_id, nombre) VALUES
(7, 'COVO'), (7, 'FP'), (7, 'TORRENTI'), (7, 'GRIVEN'), (7, 'BELT-G'), (7, 'GRINACA'),
(7, 'AQUA PLUS'), (7, 'SALUDVEN'), (7, 'GRIMAX'), (7, 'AQUAFINA');

INSERT INTO marcas_competencia (marca_id, nombre) VALUES
(8, 'TUBRICA'), (8, 'PACVO'), (8, 'PCP'), (8, 'TIGRE'), (8, 'DONSEN'), (8, 'TERMOFUZION'), (8, 'ZHOU');

INSERT INTO categorias_material (id, familia, nombre) VALUES
(1, 'TRADE_MARKETING', 'Exhibición'),
(2, 'TRADE_MARKETING', 'Señalización'),
(3, 'TRADE_MARKETING', 'Activaciones'),
(4, 'TRADE_MARKETING', 'Merchandising'),
(5, 'PUBLICIDAD', 'Online'),
(6, 'PUBLICIDAD', 'Offline');

INSERT INTO materiales (marca_id, categoria_id, nombre, requiere_medidas, requiere_ubicacion, minimo_fotos) VALUES
(1, 1, 'Exhibidor grande', 0, 0, 1),
(1, 1, 'Exhibidor pequeño', 0, 0, 1),
(1, 1, 'Exhibidor para bombas sumergibles', 0, 0, 1),
(1, 1, 'Exhibidor personalizado', 1, 1, 2),
(NULL, 1, 'Exhibidor estándar', 0, 0, 1),
(NULL, 1, 'Exhibidor personalizado', 1, 1, 2);

INSERT INTO materiales (marca_id, categoria_id, nombre, requiere_medidas, requiere_ubicacion, minimo_fotos) VALUES
(NULL, 2, 'Aviso de fachada', 1, 1, 2),
(NULL, 2, 'Aviso de pared', 1, 1, 2),
(NULL, 2, 'Rompetráfico', 0, 0, 1),
(NULL, 2, 'Stickers', 0, 0, 1),
(NULL, 2, 'Rotulado', 1, 1, 2),
(NULL, 2, 'Banderines', 0, 0, 1);

INSERT INTO materiales (marca_id, categoria_id, nombre, requiere_medidas, requiere_ubicacion, minimo_fotos) VALUES
(NULL, 3, 'Activaciones PDV', 0, 0, 0),
(NULL, 3, 'Charla técnica', 0, 0, 0);

INSERT INTO materiales (marca_id, categoria_id, nombre, requiere_medidas, requiere_ubicacion, minimo_fotos) VALUES
(NULL, 4, 'Uniformes', 0, 0, 1),
(NULL, 4, 'POP', 0, 0, 1);

INSERT INTO materiales (marca_id, categoria_id, nombre, requiere_medidas, requiere_ubicacion, minimo_fotos) VALUES
(NULL, 5, 'Patrocinio ADS', 0, 0, 1),
(NULL, 5, 'Colab', 0, 0, 1),
(NULL, 6, 'Audiovisual e Impreso', 0, 0, 1),
(NULL, 6, 'Patrocinio Evento', 0, 0, 1);

INSERT INTO categorias_producto_mercado (nombre, marca_id) VALUES
('BOMBAS', 1),
('HERRAMIENTAS', 2),
('PURIFICADORES', 4),
('BREAKERS', 5),
('GRIFERIA', 6),
('VALVULERIA', 7),
('PVC/PPR', 8);

INSERT INTO objetivo_visita_tipos (nombre) VALUES ('Auditoría de marca'), ('Evento');

INSERT INTO objetivo_visita_subtipos (tipo_id, nombre)
SELECT id, 'Ruta Vettal' FROM objetivo_visita_tipos WHERE nombre = 'Evento'
UNION ALL
SELECT id, 'Charla técnica' FROM objetivo_visita_tipos WHERE nombre = 'Evento';

INSERT INTO objetivo_visita_subtipos (tipo_id, nombre)
SELECT id, 'Feria' FROM objetivo_visita_tipos WHERE nombre = 'Evento'
UNION ALL
SELECT id, 'Exposición' FROM objetivo_visita_tipos WHERE nombre = 'Evento'
UNION ALL
SELECT id, 'Congreso' FROM objetivo_visita_tipos WHERE nombre = 'Evento'
UNION ALL
SELECT id, 'Rueda de negocios' FROM objetivo_visita_tipos WHERE nombre = 'Evento';
