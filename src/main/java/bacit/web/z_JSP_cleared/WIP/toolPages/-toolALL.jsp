<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style> table, th, td {border: 1px solid black;}</style>
</head>
<body>
<jsp:include page="../../../../../../webapp/__head_nav.html"/>
    <form action = 'toollistservlet' method = 'GET'>
        <table>
            <%
                try {
                    // TODO: 10.11.2021 -joachim: could move a lot of back to the servlet but meh
                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");

                    PreparedStatement ps = con.prepareStatement("SELECT toolCategory FROM Tool GROUP BY toolCategory");
                    ResultSet rs = ps.executeQuery();

                    String categoryName;
                    while (rs.next()) {
                        categoryName = rs.getString("toolCategory");
                        out.print("<tr>");
                        out.print("<td><label for = " + categoryName + "> " + categoryName.replaceAll("_", " ") + ":</label></td>");
                        out.print("<td><img src = 'img/amv.png' width = '156' heigth = '151'></td>");
                        out.print("<td><input type = 'radio' id = " + categoryName + " name = 'category' value = " + categoryName + "></td>");
                        out.print("</tr>");
                    }
                } catch (Exception e) {
                    out.print("error");
                }
            %>
        </table>
        <input type = 'submit' value = 'Submit'>");
    </form>



</body>
</html>
