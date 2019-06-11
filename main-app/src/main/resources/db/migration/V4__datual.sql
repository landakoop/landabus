UPDATE linea_geltokiak SET posizioa=1 WHERE lineaID=1 and geltokiaID=3;
UPDATE linea_geltokiak SET posizioa=3 WHERE lineaID=1 and geltokiaID=4;
UPDATE linea_geltokiak SET posizioa=4 WHERE lineaID=1 and geltokiaID=5;
UPDATE linea_geltokiak SET posizioa=5 WHERE lineaID=1 and geltokiaID=6;
UPDATE linea_geltokiak SET posizioa=6 WHERE lineaID=1 and geltokiaID=8;
UPDATE linea_geltokiak SET posizioa=7 WHERE lineaID=1 and geltokiaID=7;
UPDATE linea_geltokiak SET posizioa=8 WHERE lineaID=1 and geltokiaID=1;

UPDATE linea_geltokiak SET posizioa=1 WHERE lineaID=2 and geltokiaID=8;
UPDATE linea_geltokiak SET posizioa=2 WHERE lineaID=2 and geltokiaID=7;
UPDATE linea_geltokiak SET posizioa=3 WHERE lineaID=2 and geltokiaID=2;

UPDATE linea_geltokiak SET posizioa=2 WHERE lineaID=3 and geltokiaID=5;
UPDATE linea_geltokiak SET posizioa=3 WHERE lineaID=3 and geltokiaID=3;
UPDATE linea_geltokiak SET posizioa=4 WHERE lineaID=3 and geltokiaID=4;
UPDATE linea_geltokiak SET posizioa=5 WHERE lineaID=3 and geltokiaID=1;

UPDATE linea_geltokiak SET posizioa=1 WHERE lineaID=4 and geltokiaID=1;
UPDATE linea_geltokiak SET posizioa=3 WHERE lineaID=4 and geltokiaID=8;
UPDATE linea_geltokiak SET posizioa=4 WHERE lineaID=4 and geltokiaID=6;
UPDATE linea_geltokiak SET posizioa=5 WHERE lineaID=4 and geltokiaID=5;
UPDATE linea_geltokiak SET posizioa=6 WHERE lineaID=4 and geltokiaID=4;
UPDATE linea_geltokiak SET posizioa=8 WHERE lineaID=4 and geltokiaID=3;

DELETE FROM eskaera;

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (1,3,8,0,'2019-05-01');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (1,2,4,2,'2019-05-01');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (1,4,2,4,'2019-05-01');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (1,5,5,2,'2019-05-01');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (1,6,1,7,'2019-05-01');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (1,8,2,3,'2019-05-01');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (1,7,3,5,'2019-05-01');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (1,1,0,2,'2019-05-01');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (2,8,5,0,'2019-05-02');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (2,7,8,3,'2019-05-02');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (2,2,2,7,'2019-05-02');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (2,4,12,4,'2019-05-02');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (2,5,0,13,'2019-05-02');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (3,6,3,0,'2019-05-03');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (3,5,5,5,'2019-05-03');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (3,3,8,3,'2019-05-03');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (3,4,2,10,'2019-05-03');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (3,1,10,1,'2019-05-03');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (3,7,0,9,'2019-05-03');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (4,1,3,0,'2019-05-04');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (4,7,11,3,'2019-05-04');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (4,8,5,4,'2019-05-04');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (4,6,4,5,'2019-05-04');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (4,5,3,2,'2019-05-04');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (4,4,2,1,'2019-05-04');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (4,2,1,11,'2019-05-04');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (4,3,0,3,'2019-05-04');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (5,3,8,0,'2019-05-05');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (5,2,4,2,'2019-05-05');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (5,4,2,4,'2019-05-05');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (5,5,5,2,'2019-05-05');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (5,6,1,7,'2019-05-05');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (5,8,2,3,'2019-05-05');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (5,7,3,5,'2019-05-05');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (5,1,0,2,'2019-05-05');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (6,8,2,0,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (6,7,4,3,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (6,2,2,5,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (6,4,8,4,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (6,5,0,4,'2019-05-06');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (7,6,3,0,'2019-05-07');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (7,5,5,5,'2019-05-07');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (7,3,8,3,'2019-05-07');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (7,4,2,10,'2019-05-07');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (7,1,10,1,'2019-05-07');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (7,7,0,9,'2019-05-07');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (8,1,1,0,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (8,7,6,1,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (8,8,3,2,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (8,6,2,3,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (8,5,1,2,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (8,5,2,1,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (8,2,1,6,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (8,3,0,1,'2019-05-08');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (9,3,8,0,'2019-05-09');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (9,2,4,2,'2019-05-09');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (9,4,2,4,'2019-05-09');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (9,5,5,2,'2019-05-09');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (9,6,1,7,'2019-05-09');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (9,8,2,3,'2019-05-09');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (9,7,3,5,'2019-05-09');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (9,1,0,2,'2019-05-09');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (10,8,5,0,'2019-05-10');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (10,7,8,3,'2019-05-10');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (10,2,2,7,'2019-05-10');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (10,4,12,4,'2019-05-10');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (10,5,0,13,'2019-05-10');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (11,6,2,0,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (11,5,5,3,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (11,3,2,4,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (11,4,3,1,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (11,1,4,2,'2019-05-08');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (11,7,0,6,'2019-05-08');

INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (12,1,5,0,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (12,7,2,2,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (12,8,3,3,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (12,6,2,2,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (12,5,1,1,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (12,4,0,3,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (12,2,3,2,'2019-05-06');
INSERT INTO ibilbidea_geltokia_predikzioa (ibilbideaID, geltokiaID, igo, jaitsi, data) VALUES (12,3,0,3,'2019-05-06');
