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

    public static void printFooter(PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }

    public static void printSidebar(PrintWriter out, String email){
        out.println(
                "<script src=\"https://kit.fontawesome.com/f65d62ad61.js\" crossorigin=\"anonymous\"></script>\n" +
                "<style> ul {" +
                "    list-style-type: none;" +
                "}" +
                "header, footer{" +
                "    font-size: 25px;" +
                "}" +
                "header, footer, body{ /* gives space for the sidebar */" +
                "    margin-left: 280px;" +
                "    padding-left: 20px;" +
                "}" +
                "" +
                "/* Sidebar segment*/" +
                ".sidebar{ /*Keeps content to the top left, and makes sure its above anything else*/" +
                "    height: 100%;" +
                "    width: 280px;" +
                "    position: fixed;" +
                "    top: 0;" +
                "    left: 0;" +
                "    z-index: 1;" +
                "    overflow-x: hidden;" +
                "    background-color: #ffb300;" +
                "}" +
                ".sidebar a{" +
                "    padding: 5px 20px 5px 20px; /*top rigth bottom left */" +
                "    text-decoration: none;" +
                "    font-family: Arial, Helvetica, sans-serif;" +
                "    font-size: 20px;" +
                "    font-weight: bold;" +
                "    color: #11165a;" +
                "    display: block;" +
                "}" +
                ".sidebar i{ /*Adjusts icons size */" +
                "    font-size: 24px;" +
                "}" +
                ".sidebar a:hover { /* Makes items half opacity when hovering */" +
                "    opacity: 0.5;" +
                "}" +
                ".sidebar img{ /*Keeps the logo image centered in the sidebar*/" +
                "    display: block;" +
                "    margin: auto;" +
                "    width: 60%;" +
                "    padding-top: 30px;" +
                "    padding-bottom: 20px;" +
                "}" +
                "/*New segment - Main*/" +
                ".main{" +
                "    font-size: 20px;" +
                "}" +
                "/*part of main - image container (flexbox)*/" +
                ".imgcontainer{" +
                "    width: 100%;" +
                "    color: gray;" +
                "    display: flex;" +
                "    flex-wrap: wrap;" +
                "    justify-content: space-between;" +
                "} " +
                "" +
                ".imgcontainer figure{" +
                "    padding-bottom: 5px;" +
                "    margin: auto;" +
                "    margin-bottom: 5px;" +
                "    text-align: center;" +
                "    border-radius: 10px;" +
                "    border: 1px solid grey;" +
                "}" +
                ".imgcontainer img{" +
                "    border-radius: 10px 10px 0px 0px;" +
                "}" +
                "/* im just playing around with search bar designs here*/" +
                ".search input{" +
                "    margin-bottom: 20px;" +
                "    padding: 10px 20px 10px 20px;" +
                "    border: 1px solid grey;" +
                "    background-color: rgb(235, 235, 235);" +
                "    border-radius: 4px;    " +
                "    font-size: 40px;" +
                "}" +
                "" +
                ".search input:hover{" +
                "background-color: rgb(216, 216, 216);}" +
                "" +
                ".search2 input, button{" +
                "    margin-bottom: 20px;" +
                "    padding: 10px 20px 10px 20px;" +
                "    border: 1px solid grey;" +
                "    background-color: rgb(235, 235, 235);" +
                "    border-radius: 4px;    " +
                "    font-size: 40px;" +
                "}" +
                "" +
                ".search2 input:hover,button:hover{" +
                "background-color: rgb(216, 216, 216);}" +
                "</style>" +
                        // TODO: 04.11.2021 make if statement to print diffrent if were admins
                "<nav class=\"sidebar\">\n" +
                "    <img src=\"img/amv.png\" alt=\"AMV logo\"></li>\n" +
                "    <a hfref='/profile'><i class=\"fa fa-user\"></i>Ola Nordman</a></li>\n" +
                "    <a hfref='/toolcategories'><i class=\"fa fa-tools\"></i>Tools and equipment</a></li>\n" +
                "    <a hfref='/profile'><i class=\"fa fa-receipt\"></i>My rentals</a></li>\n" +
                "    <a hfref='/tl'><i class=\"fa fa-clipboard\"></i>Reports</a></li>\n" +
                "    <a hfref='#'><i class=\"fa fa-question-circle\"></i>Help</a></li>\n" +
                "</nav>");
        if(AdminAccess.accessRights(email)){
            out.println(
                    ""
            );
        }
    }


    /* ADMIN SIDEBAR (commented out so you can use it when your functions are done)
                out.println("<nav class=\"sidebar\">\n" +(
                "    <img src=\"img/amv.png\" alt=\"AMV logo\"></li>\n" +
                "    <a hfref='"+BASE_URL+"/profile'><i class=\"fa fa-user\"></i>Ola Nordman</a></li>\n" +
                "    <a hfref='"+BASE_URL+"/toolcategories'><i class=\"fa fa-tools\"></i>Tools Categories</a></li>\n" +
                "    <a hfref='"+BASE_URL+"/tlb'><i class=\"fa fa-receipt\"></i>List of All Tools</a></li>\n" +
                "    <a hfref='#'><i class=\"fa fa-question-circle\"></i>Help(TO DO)</a></li>\n" +
                "    <a hfref='"+BASE_URL+"/toolregister'><i class=\"fa fa-question-circle\"></i>Register New Tool</a></li>\n" +
                "    <a hfref='"+BASE_URL+"/toolmaintenance'><i class=\"fa fa-receipt\"></i>Put Tool in Maintenance</a></li>\n" +
                "    <a hfref='"+BASE_URL+"/payment'><i class=\"fa fa-receipt\"></i>Payment Report</a></li>\n" +
                "    <a hfref='"+BASE_URL+"/givecertificate'><i class=\"fa fa-receipt\"></i>Grant User Certificates</a></li>\n" +

                "</nav>");

            //MAYBE we should have the sidebar for admins have one link for a page called "admin functions" or similar?
            //BECAUSE right now, there will be a lot of similar thing i the sidebar for admins to click.
            //I THINK that they could be split into around two or three servlets named "Reports" and "Modify Tools".
            //THESE servlets would have zero other functionalities than link to the other servlets.
            //THIS might be redundant, but I feel something needs to move around for the sidebar links.
     */

}

