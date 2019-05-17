CREATE TABLE erabiltzailea (
erabiltzaileaID   INT NOT NULL AUTO_INCREMENT,
izena             CHAR(100),
mota              CHAR(10),
erabiltzailea     CHAR(20),
telegramID        CHAR(9),
CONSTRAINT erabiltzailea_pk PRIMARY KEY (erabiltzaileaID)
);

CREATE TABLE autobusa (
autobusaID        SMALLINT NOT NULL AUTO_INCREMENT,
ahalmena          TINYINT,
CONSTRAINT autoubsa_pk PRIMARY KEY(autobusaID)
);


CREATE TABLE linea (
 lineaID            INT NOT NULL AUTO_INCREMENT,
 izena              CHAR(100),
 CONSTRAINT linea_pk PRIMARY KEY(lineaID)
);

CREATE TABLE geltokia (
 geltokiaID         TINYINT NOT NULL AUTO_INCREMENT,
 izena              CHAR(100),
 CONSTRAINT geltokia_pk PRIMARY KEY(geltokiaID)
);

CREATE TABLE distantziak (
 geltokiaA          TINYINT,
 geltokiaB          TINYINT,
 denbora            TINYINT,
 CONSTRAINT distantziak_geltokiaA_fk FOREIGN KEY(geltokiaA) REFERENCES geltokia(geltokiaID),
 CONSTRAINT distantziak_geltokiaB_fk FOREIGN KEY(geltokiaB) REFERENCES geltokia(geltokiaID)
);

CREATE TABLE ibilbidea (
 ibilbideaID        INT NOT NULL AUTO_INCREMENT,
 lineaID            INT,
 autobusaID         SMALLINT,
 predikzioa         TINYINT,
 CONSTRAINT ibilbidea_pk PRIMARY KEY(ibilbideaID),
 CONSTRAINT ibilbidea_linea_fk FOREIGN KEY(lineaID) REFERENCES linea(lineaID)
);

CREATE TABLE linea_geltokiak (
 lineaID           INT,
 geltokiaID        TINYINT,
 posizioa          TINYINT,
 CONSTRAINT linea_geltokiak_linea_fk FOREIGN KEY(lineaID) REFERENCES linea(lineaID),
 CONSTRAINT linea_geltokiak_geltokia_fk FOREIGN KEY(geltokiaID) REFERENCES geltokia(geltokiaID)
);

CREATE TABLE eskaera (
 eskaeraID         INT NOT NULL AUTO_INCREMENT,
 bidaiariaID       INT,
 ibilbideaID       INT,
 geltokiaA         TINYINT,
 geltokiaB         TINYINT,
 data              TIMESTAMP DEFAULT now(),
 onartua           BOOLEAN DEFAULT false,
 irteeraOrdua      SMALLINT,
 helmugaOrdua      SMALLINT,
 CONSTRAINT eskaera_pk PRIMARY KEY (eskaeraID),
 CONSTRAINT eskaera_bidaiaria_fk FOREIGN KEY(bidaiariaID) REFERENCES erabiltzailea(erabiltzaileaID),
 CONSTRAINT eskaera_ibilbidea_fk FOREIGN KEY(ibilbideaID) REFERENCES ibilbidea(ibilbideaID),
 CONSTRAINT eskaera_geltokiaA_fk FOREIGN KEY(geltokiaA) REFERENCES geltokia(geltokiaID),
 CONSTRAINT eskaera_geltokiaB_fk FOREIGN KEY(geltokiaB) REFERENCES geltokia(geltokiaID)
);

CREATE TABLE ordutegia (
 ordutegiaID       INT NOT NULL AUTO_INCREMENT,
 CONSTRAINT ordutegia_pk PRIMARY KEY(ordutegiaID)
);

CREATE TABLE ibilbidea_geltokia_bidaiaria (
 ibilbideaID       INT,
 geltokiaID        TINYINT,
 bidaiariaID       INT,
 ekintza           CHAR(10),
 timestamp         TIMESTAMP,
 CONSTRAINT ibilbidea_geltokia_bidaiaria_bidaiaria_fk FOREIGN KEY(bidaiariaID) REFERENCES erabiltzailea(erabiltzaileaID),
 CONSTRAINT ibilbidea_geltokia_bidaiaria_ibilbidea_fk FOREIGN KEY(ibilbideaID) REFERENCES ibilbidea(ibilbideaID),
 CONSTRAINT ibilbidea_geltokia_bidaiaria_geltokia_fk FOREIGN KEY(geltokiaID) REFERENCES geltokia(geltokiaID)
);
