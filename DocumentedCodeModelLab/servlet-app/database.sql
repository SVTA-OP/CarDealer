-- =====================================================================
-- Run this ONCE in MySQL (Workbench or command line: mysql -u root -p < database.sql)
-- =====================================================================

-- 1. create the database
CREATE DATABASE IF NOT EXISTS servletdb;

-- 2. use it
USE servletdb;

-- 3. create the table that StudentServlet reads and writes
CREATE TABLE IF NOT EXISTS students (
    id     INT AUTO_INCREMENT PRIMARY KEY,   -- unique number, MySQL fills it in
    name   VARCHAR(100) NOT NULL,
    email  VARCHAR(100),
    course VARCHAR(100)
);

-- 4. a sample row so the page is not empty
INSERT INTO students (name, email, course) VALUES ('Asha', 'asha@example.com', 'Java');
