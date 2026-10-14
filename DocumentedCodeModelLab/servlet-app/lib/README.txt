REQUIRED JAR FILES (only needed if you do NOT use Maven, e.g. plain Eclipse/NetBeans project)
==========================================================================================

1) mysql-connector-j-8.0.33.jar   (the MySQL JDBC driver)
   Download: https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar
   Put it in:  src/main/webapp/WEB-INF/lib/     (so it ends up inside the war)

2) servlet-api.jar                (Servlet 4.0 classes, needed only to COMPILE)
   You already have it! It is in:  <TOMCAT_9_FOLDER>/lib/servlet-api.jar
   Add it to your project's build path, but do NOT copy it into WEB-INF/lib
   (Tomcat provides it at runtime; a second copy can cause errors).
   Or download: https://repo1.maven.org/maven2/javax/servlet/javax.servlet-api/4.0.1/javax.servlet-api-4.0.1.jar

IMPORTANT: Tomcat 9 uses the "javax.servlet" package. Tomcat 10+ switched to
"jakarta.servlet" and would NOT run this code. Use Apache Tomcat 9.
