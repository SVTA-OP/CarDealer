import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


public class CheckServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
            String result = "";
            String ageString = req.getParameter("age");
            int age = Integer.parseInt(ageString);

            if (age >= 18){result = "eligible to vote";}
            else {result = "not elegible to vote";}


            PrintWriter out = res.getWriter();
            out.print("<h1> You are "+result+"</h1>");
            out.close();
    }

}