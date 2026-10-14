# Servlet Demo: Tomcat 9 + MySQL + Web Storage

## Setup
1. Run `database.sql` in MySQL.
2. Edit USER / PASSWORD in `src/main/java/com/example/servlet/DBConnection.java`.
3. Build:  `mvn clean package`   -> creates target/servletdemo.war
   (no Maven? see lib/README.txt for the jars and use Eclipse/IntelliJ + Tomcat 9)
4. Copy the war into  <TOMCAT_9>/webapps/  and run  bin/startup.bat (Windows) or bin/startup.sh (Linux/Mac).
5. Open  http://localhost:8080/servletdemo/

## URLs
- /servletdemo/hello?name=Asha   -> HelloServlet (basic servlet, web.xml mapping)
- /servletdemo/students          -> StudentServlet (MySQL CRUD, @WebServlet mapping)
- /servletdemo/storage.html      -> localStorage / sessionStorage demo

## Notes
- Must be Tomcat 9 (javax.servlet). Tomcat 10+ uses jakarta.servlet and needs code changes.
- Java 11 or newer.
