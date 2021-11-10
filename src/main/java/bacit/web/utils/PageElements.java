package bacit.web.utils;

//by Paul

import bacit.web.adminPages.AdminAccess;

import java.io.PrintWriter;

public class PageElements {

    public static final String BASE_URL = "http://localhost:8081/bacit-web-1.0-SNAPSHOT";

    public static void printHeader(String title, PrintWriter out){
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+title+"</title>");
        out.println("</head>");
        out.println("<body>");
    }
    public static void printHead(PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<head>");
        out.println("  <title>Toollist</title>"); // TODO: 09.11.2021 set the titel to a string or smth
        out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        out.println("  <meta charset=\"utf-8\" />");
        out.println("  <link rel=\"stylesheet\" href=\"CSS/list.css\">");
        out.println("  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>");
        out.println("</head>");
        out.println("<body>");
    }

    public static void printFooter(PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }

    public static void printSidebar(PrintWriter out, String email){
        out.println(
                "<script src=\"https://kit.fontawesome.com/f65d62ad61.js\" crossorigin=\"anonymous\"></script>\n" +
                        "<link rel=\"stylesheet\" href=\"CSS/style.css\">" +
                "<nav class=\"sidebar\">\n" +
                "    <img src=\"img/amv.png\" alt=\"AMV logo\"></li>\n" +
                "    <a href="+BASE_URL+"'/profile'><i class=\"fa fa-user\"></i>Ola Nordman</a></li>\n" +
                "    <a href="+BASE_URL+"'/toolcategories'><i class=\"fa fa-tools\"></i>Tools and equipment</a></li>\n" +
                "    <a href="+BASE_URL+"'/profile'><i class=\"fa fa-receipt\"></i>My rentals</a></li>\n" +
                "    <a href="+BASE_URL+"'/tl'><i class=\"fa fa-clipboard\"></i>Reports</a></li>\n" +
                "    <a href="+BASE_URL+"'#'><i class=\"fa fa-question-circle\"></i>Help</a></li>\n");
        if(AdminAccess.accessRights(email)) {
            // TODO: 04.11.2021 make if statement to print diffrent if were admins
            out.println("<a href=" + BASE_URL + "/admin><i class=\"fa fa-users-cog\"></i>Admin Page</a></li>\n");
        }
        out.println("</nav><div class='main'");
    }

    public static void printHeadNav(PrintWriter out){
        out.print(
                "    <style>\n" +
                "        .amv-tx-c-b {\n" +
                "            color: #11165a;\n" +
                "        }\n" +
                "        .amv-bg-c-y {\n" +
                "            background-color: #ffb300;\n" +
                "        }\n" +
                "        .amv-btn {\n" +
                "            color: #11165a;\n" +
                "            border: 2px solid #11165a;\n" +
                "            border-radius: unset;\n" +
                "        }\n" +
                "    </style>"+
                "<header class=\"p-3 mb-3 border-bottom amv-bg-c-y\" style=\"font-family: Arial,Helvetica,sans-serif\">\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start\">\n" +
                "            <a href=\"index.jsp\" class=\"d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none\">\n" +
                "                <img src=\"img/amv.png\" class=\"bi me-2\" height=\"40\" role=\"img\" aria-label=\"Bootstrap\" alt=\"AMV logo\">\n" +
                "            </a>\n" +
                "\n" +
                "            <ul class=\"nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0\">\n" +
                "                <li><a href=\"/toolcategories\" class=\"nav-link px-2 amv-tx-c-b\">TOOL OVERVIEW</a></li>\n" +
                "                <li><a href=\"/profile\" class=\"nav-link px-2 amv-tx-c-b\">RENTALS</a></li>\n" +
                "                <li><a href=\"/help\" class=\"nav-link px-2 amv-tx-c-b\">FAQ & HELP</a></li>\n" +
                "            </ul>\n" +
                "\n" +
                "            <div class=\"text-end\">\n" +
                "                <button type=\"button\" formaction=\"login\" class=\"btn amv-btn\">LOGIN</button>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</header>");
    }
}

