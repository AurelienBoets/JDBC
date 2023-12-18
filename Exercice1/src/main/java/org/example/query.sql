CREATE DATABASE jdbc_1;
USE jdbc_1;
CREATE TABLE student(
 id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
 lastname VARCHAR(50) NOT NULL,
 firstname VARCHAR(50) NOT NULL,
 classnumber int NOT NULL,
 diploma_date DATE
);