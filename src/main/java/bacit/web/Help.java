package bacit.web;

import bacit.web.*;
import bacit.web.a_models.UserModel;
import bacit.web.utils.DBUtils;
import bacit.web.utils.hashPassword;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "Help", value = "/help")
public class Help extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.print("<html>" +
                "<head>" +
                "<title>Help</title>" +
                "</head>" +
                "<body>" +
                "<h2>How can we help?</h2>" +
                "<form onclick='http://localhost:8081/bacit-web-1.0-SNAPSHOT/' action='https://script.google.com/a/hsu.edu.hk/macros/s/AKfycbxfqBQo0UkCet0kkVLK8CDMtPBpann19xu0mI10/exec' method='POST' data-email='cheuklong20010212@gmail.com'>" +
                "<h3>Name:</h3>" +
                "<textarea id='name' name='name' rows='2' cols='50'></textarea><br><br>" +
                "<h3>Message:</h3>" +
                "<textarea id='message' name='message' rows='10' cols='50'></textarea><br><br>" +
                "<h3>Email:</h3>" +
                "<textarea id='email' name='email' rows='2' cols='50'></textarea><br><br>" +
                "<input type = 'submit' value = 'Send Help!'>" +
                "</form>" +
                "</body>" +
                "</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String a = request.getParameter("name");
        String b = request.getParameter("message");
        String c = request.getParameter("email");
        out.print("<html>" +
                "<body>" +
                "<h1>" + a + "</h1>" +
                "<h1>" + b + "</h1>" +
                "<h1>" + c + "</h1>" +
                "</body>" +
                "</html>");


    }

}
