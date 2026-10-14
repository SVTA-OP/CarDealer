package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BASIC HELLO SERVLET (registered in web.xml -> URL: /hello)
 *
 * A servlet is a Java class that runs inside Tomcat and answers browser requests.
 * It extends HttpServlet and overrides doGet() / doPost().
 *
 * LIFE CYCLE (Tomcat calls these for you):
 *   init()    -> once, when the servlet is first created
 *   doGet()   -> every time a browser sends a GET request  (typing a URL, clicking a link)
 *   doPost()  -> every time a browser sends a POST request (submitting a form with method="post")
 *   destroy() -> once, when the servlet is removed
 *
 * Try:  http://localhost:8080/servletdemo/hello
 *       http://localhost:8080/servletdemo/hello?name=Asha
 */
public class HelloServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("HelloServlet: init() called");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. READ input: ?name=Asha in the URL. Returns null if missing.
        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            name = "World";
        }

        // 2. SET the type of data we send back (HTML text)
        response.setContentType("text/html;charset=UTF-8");

        // 3. WRITE the HTML. PrintWriter sends text to the browser.
        PrintWriter out = response.getWriter();
        out.println("<html><body style='font-family:Arial'>");
        out.println("<h1>Hello, " + escape(name) + "!</h1>");
        out.println("<p>This page was made by HelloServlet.</p>");
        out.println("<p>Method used: " + request.getMethod() + "</p>");
        out.println("<a href='index.html'>Back to home</a>");
        out.println("</body></html>");
    }

    // A POST request does the same thing as GET, so we just call doGet()
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("HelloServlet: destroy() called");
    }

    /**
     * Replaces < > & so user text cannot inject HTML/JavaScript into the page.
     * (Always do this when printing text typed by users.)
     */
    static String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
