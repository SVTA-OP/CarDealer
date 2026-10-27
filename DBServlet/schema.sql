CREATE DATABASE IF NOT EXISTS testdb;
USE testdb;
CREATE TABLE IF NOT EXISTS students (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), course VARCHAR(50));
INSERT INTO students (name, course) VALUES ('Asha', 'Java'), ('Ravi', 'Vue');
