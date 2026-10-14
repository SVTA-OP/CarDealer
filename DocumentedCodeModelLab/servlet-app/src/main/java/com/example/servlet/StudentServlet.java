package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * STUDENT SERVLET with MySQL (registered with an annotation -> URL: /students)
 *
 * Shows how a servlet uses JDBC (Java Database Connectivity):
 *    1. get a Connection              (DBConnection.getConnection)
 *    2. create a PreparedStatement    (the SQL, with ? placeholders)
 *    3. fill the ? values             (setString, setInt ...)
 *    4. run it                        (executeQuery for SELECT, executeUpdate for INSERT/DELETE)
 *    5. close everything              (try-with-resources does this automatically)
 *
 *   GET  /students                  -> show the table of students + the add form
 *   POST /students  action=add      -> INSERT a new student
 *   POST /students  action=delete   -> DELETE a student by id
 */
@WebServlet("/students")      // <- this single line replaces the web.xml mapping
public class StudentServlet extends HttpServlet {

    // ======================= GET : READ and SHOW =======================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body style='font-family:Arial; margin:30px'>");
        out.println("<h1>Students (from MySQL)</h1>");

        // ----- the add form: POST sends the data to this same servlet -----
        out.println("<form method='post' action='students'>");
        out.println("<input type='hidden' name='action' value='add'>");
        out.println("Name: <input type='text' name='name' required> ");
        out.println("Email: <input type='text' name='email'> ");
        out.println("Course: <input type='text' name='course'> ");
        out.println("<button type='submit'>Add</button>");
        out.println("</form><br>");

        // ----- the table -----
        out.println("<table border='1' cellpadding='8' cellspacing='0'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Course</th><th>Delete</th></tr>");

        String sql = "SELECT id, name, email, course FROM students";

        // try-with-resources: connection, statement and result are CLOSED automatically
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // rs.next() moves to the next row; returns false when there are no more rows
            while (rs.next()) {
                int id = rs.getInt("id");
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + HelloServlet.escape(rs.getString("name")) + "</td>");
                out.println("<td>" + HelloServlet.escape(rs.getString("email")) + "</td>");
                out.println("<td>" + HelloServlet.escape(rs.getString("course")) + "</td>");
                // a tiny form per row that POSTs a delete request
                out.println("<td><form method='post' action='students'>"
                          + "<input type='hidden' name='action' value='delete'>"
                          + "<input type='hidden' name='id' value='" + id + "'>"
                          + "<button type='submit'>Delete</button></form></td>");
                out.println("</tr>");
            }

        } catch (SQLException e) {
            // show a friendly error instead of a crash page
            out.println("</table><p style='color:red'>Database error: " + HelloServlet.escape(e.getMessage()) + "</p>");
        }

        out.println("</table><br><a href='index.html'>Back to home</a>");
        out.println("</body></html>");
    }

    // ======================= POST : ADD and DELETE =======================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Which button was pressed? (hidden field "action" in the form)
        String action = request.getParameter("action");

        try (Connection con = DBConnection.getConnection()) {

            if ("add".equals(action)) {
                // ? are placeholders. Using them (instead of joining strings) prevents SQL injection.
                String sql = "INSERT INTO students (name, email, course) VALUES (?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, request.getParameter("name"));    // 1st ?
                    ps.setString(2, request.getParameter("email"));   // 2nd ?
                    ps.setString(3, request.getParameter("course"));  // 3rd ?
                    ps.executeUpdate();                               // run INSERT
                }

            } else if ("delete".equals(action)) {
                String sql = "DELETE FROM students WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, Integer.parseInt(request.getParameter("id")));
                    ps.executeUpdate();                               // run DELETE
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }

        // POST-REDIRECT-GET: send the browser to the GET page, so pressing refresh
        // does not submit the form a second time.
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
