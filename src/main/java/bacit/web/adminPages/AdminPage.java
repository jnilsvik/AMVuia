package bacit.web.adminPages;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminPage", value = "/admin")
public class AdminPage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>" +
                        "<head>" +
                "  <title>Admin Functions</title>" +
        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
        "  <meta charset=\"utf-8\" />" +
        "<style>" +
        ".bigbutton {" +
        "display: block;" +
        "width: 50%;" +
        "border: none;" +
        "background-color: #ffb300;" +
        "color: black;" +
        "padding: 14px 28px;" +
        "font-size: 16px;" +
        "cursor: pointer;" +
        "margin: auto;" +
        "text-align: center; }" +
        ".bigbutton:hover {" +
        "background-color: #11165a;" +
        "color: white; }" +
        "</style>" +
        "</head>" +
        "<body>" +
        "<a href=\"toolregister\"> <span class=\"bigbutton\"> Register Tool </span></a>" +
                "<br>" +
                "<a href=\"toolmaintenance\"> <span class=\"bigbutton\"> Tool Maintenance </span></a>" +
                "<br>" +
                "<a href=\"el\"> <span class=\"bigbutton\"> List of Users </span></a>" +
                "<br>" +
                "<a href=\"payment\"> <span class=\"bigbutton\"> Payment Report </span></a>" +
                "<br>" +
                "<a href=\"givecertificate\"> <span class=\"bigbutton\"> Give Certificate </span></a>" +
                "</body>"

    );
    }
}
