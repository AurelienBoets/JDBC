CREATE DATABASE bdd_voiture;
USE bdd_voiture;
CREATE TABLE voiture(
                        id INT AUTO_INCREMENT NOT NULL,
                        nom VARCHAR(50) NOT NULL,
                        annee INT(4) NOT NULL,
                        puissance INT NOT NULL,
                        prix FLOAT(10,2) NOT NULL,
CONSTRAINT pk_voiture PRIMARY KEY (id)
);

CREATE TABLE personne(
                         id INT AUTO_INCREMENT NOT NULL,
                         nom VARCHAR(50) NOT NULL,
                         prenom VARCHAR(50) NOT NULL,
                         age INT(2) NOT NULL,
                         CONSTRAINT pk_personne PRIMARY KEY (id)
);

CREATE TABLE vente(
                      personne_id INT NOT NULL,
                      voiture_id INT NOT NULL,
                      date DATE NOT NULL,
                      CONSTRAINT fk_personne FOREIGN KEY (personne_id) REFERENCES personne(id),
                      CONSTRAINT fk_voiture FOREIGN KEY (voiture_id) REFERENCES voiture(id)
);