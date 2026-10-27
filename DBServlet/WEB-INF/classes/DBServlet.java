import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DBServlet extends HttpServlet {

    // JDBC boilerplate: change these to match your database
    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASS = "";
    static final String TABLE = "students";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM " + TABLE)) {

                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();

                out.println("<table border='1'><tr>");
                for (int i = 1; i <= cols; i++) out.println("<th>" + md.getColumnName(i) + "</th>");
                out.println("</tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    for (int i = 1; i <= cols; i++) out.println("<td>" + rs.getString(i) + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
        } catch (Exception e) {
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
        out.close();
    }
}
