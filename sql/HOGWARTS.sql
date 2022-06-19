BEGIN TRANSACTION;
DROP TABLE IF EXISTS "HOGWARTS";
CREATE TABLE IF NOT EXISTS "HOGWARTS" (
                                          "id"	INTEGER NOT NULL,
                                          "nombre"	TEXT NOT NULL,
                                          "apodo"	TEXT NOT NULL,
                                          "fNacimiento"	TEXT NOT NULL,
                                          "casa"	TEXT,
                                          "altura"	INTEGER,
                                          "hechizo"	TEXT,
                                          PRIMARY KEY("id" AUTOINCREMENT)
    );
INSERT INTO "HOGWARTS" VALUES (1,'Harry James Potter','Harry','1980-07-31','Gryffindor',180,'Patronus'),
                              (2,'Hermione Jean Granger','Hermione','1979-09-19','Gryffindor',164,'Patronus'),
                              (3,'Ron Weasley','Ron','1980-03-01','Gryffindor',185,'Locomotor'),
                              (4,'Cedric Diggory','Cedric','1977-10-12','Hufflepuff',185,'Orchideous'),
                              (5,'Luna Lovegood','Luna','1981-02-15','Ravenclaw',157,'Descendo'),
                              (6,'Draco Lucius Malfoy Black','Draco','1980-07-05','Slytherin',175,'Incendio');
COMMIT;
